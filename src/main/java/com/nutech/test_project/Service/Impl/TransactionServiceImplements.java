package com.nutech.test_project.Service.Impl;

import com.nutech.test_project.Dto.Request.TopupRequest;
import com.nutech.test_project.Dto.Request.TransactionRequest;
import com.nutech.test_project.Dto.Response.GetTransactionHistory;
import com.nutech.test_project.Dto.Response.ResponseHandling;
import com.nutech.test_project.Dto.Response.TransactionResponse;
import com.nutech.test_project.Entity.Balances;
import com.nutech.test_project.Entity.Enum.Status;
import com.nutech.test_project.Entity.Services;
import com.nutech.test_project.Entity.Transaction;
import com.nutech.test_project.Entity.Users;
import com.nutech.test_project.ErrorHandler.ServiceCustomException.CustomBadRequestException;
import com.nutech.test_project.ErrorHandler.ServiceCustomException.CustomNotfoundException;
import com.nutech.test_project.Repository.*;
import com.nutech.test_project.Service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionServiceImplements implements TransactionService {

    private BalancesRepository balancesRepository;

    private TopupsRepository topupsRepository;

    private UsersRepository usersRepository;

    private ServicesRepository servicesRepository;

    private TransactionRepository transactionRepository;

    @Override
    public ResponseHandling<Map<String, BigDecimal>> balance(String emailFromToken) {
        ResponseHandling<Map<String, BigDecimal>> responseHandling = new ResponseHandling<>();

        Optional<Users> users = usersRepository.findByEmail(emailFromToken);
        if (!users.isPresent()){
            throw new CustomBadRequestException("user not found");
        }

        Map<String, BigDecimal> balanceResult = new HashMap<>();
        balanceResult.put("balance", users.get().getBalances().getAmount());

        responseHandling.setStatus("0");
        responseHandling.setMessage("Get Balance Berhasil");
        responseHandling.setData(balanceResult);

        return responseHandling;
    }

    @Transactional
    @Override
    public ResponseHandling<Map<String, BigDecimal>> topup(TopupRequest topupRequest, String emailFromToken) {
        ResponseHandling<Map<String, BigDecimal>> responseHandling = new ResponseHandling<>();

        Optional<Users> users = usersRepository.findByEmail(emailFromToken);
        if (!users.isPresent()){
            throw new CustomBadRequestException("user not found");
        }

        int topup = topupsRepository.saveBalance(users.get().getId(), topupRequest.getTop_up_amount());
        if (topup == 0) {
            log.error("error when topup");
            throw new CustomBadRequestException("gagal topup");
        }

        topupsRepository.saveTopup(topupRequest.getTop_up_amount(), users.get().getId());

        Optional<Balances> balances = topupsRepository.getBalanceByUserId(users.get().getId());

        Map<String, BigDecimal> balance = new HashMap<>();
        balance.put("balance", balances.get().getAmount());

        responseHandling.setStatus("0");
        responseHandling.setMessage("Top Up Balance berhasil");
        responseHandling.setData(balance);

        return responseHandling;
    }

    @Override
    public ResponseHandling<TransactionResponse> transaction(TransactionRequest transactionRequest, String emailFromToken) {
        ResponseHandling<TransactionResponse> responseHandling = new ResponseHandling<>();

        Optional<Users> users = usersRepository.findByEmail(emailFromToken);
        if (!users.isPresent()){
            throw new CustomNotfoundException("user not found");
        }

        Optional<Services> services = servicesRepository.finByServiceCode(transactionRequest.getService_code());
        if (!services.isPresent()){
            throw new CustomNotfoundException("Service ataus Layanan tidak ditemukan");
        }

        Optional<Balances> balances = balancesRepository.findBalanceByUserId(users.get().getId());
        if (!balances.isPresent()){
            throw new CustomNotfoundException("balance not found");
        }

        BigDecimal currentBalance = balances.get().getAmount();
        BigDecimal servicePrice = services.get().getPrice();

        if (currentBalance.compareTo(servicePrice) < 0) {
            throw new CustomBadRequestException("Saldo tidak mencukupi");
        }

        BigDecimal finalAmount = currentBalance.subtract(servicePrice);

        int res = balancesRepository.afterPay(users.get().getId(), finalAmount);
        if (res == 0){
            throw new CustomBadRequestException("failed to pay");
        }

        Transaction transaction = new Transaction();
        transaction.setInvoiceNumber(generateInvoiceNumber());
        transaction.setAmount(servicePrice);
        transaction.setStatus(Status.PAYMENT);
        transaction.setUsers(users.get());
        transaction.setServices(services.get());

        transactionRepository.save(transaction);

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setInvoice_number(transaction.getInvoiceNumber());
        transactionResponse.setService_code(transaction.getServices().getServiceCode());
        transactionResponse.setService_name(transaction.getServices().getServiceName());
        transactionResponse.setTransaction_type(transaction.getStatus().name());
        transactionResponse.setTotal_amount(transaction.getAmount());
        transactionResponse.setCreated_on(transaction.getCreatedAt());

        responseHandling.setStatus("0");
        responseHandling.setMessage("Transaksi berhasil");
        responseHandling.setData(transactionResponse);

        return responseHandling;
    }

    @Override
    public ResponseHandling<List<GetTransactionHistory>> transactionHistory(String emailFromToken, int offset, Integer limit) {
        ResponseHandling<List<GetTransactionHistory>> responseHandling = new ResponseHandling<>();

        Optional<Users> users = usersRepository.findByEmail(emailFromToken);
        if (!users.isPresent()){
            throw new CustomNotfoundException("user not found");
        }

        Pageable pageable = PageRequest.of(offset, limit);
        Page<Transaction> transactions = transactionRepository.findTransactionByUser(users.get().getId(), pageable);

        List<GetTransactionHistory> getTransactionHistories = transactions.stream().map(p -> {
            GetTransactionHistory getTransactionHistory = new GetTransactionHistory();
            getTransactionHistory.setInvoice_number(p.getInvoiceNumber());
            getTransactionHistory.setTransaction_type(p.getStatus().name());
            getTransactionHistory.setDescription(p.getServices().getServiceName());
            getTransactionHistory.setTotal_amount(p.getAmount());
            getTransactionHistory.setCreated_on(p.getCreatedAt());
            return getTransactionHistory;
        }).collect(Collectors.toList());

        responseHandling.setStatus("0");
        responseHandling.setMessage("Get History Berhasil");
        responseHandling.setData(getTransactionHistories);

        return responseHandling;
    }

    public static String generateInvoiceNumber() {
        return UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 20)
                .toUpperCase();
    }
}
