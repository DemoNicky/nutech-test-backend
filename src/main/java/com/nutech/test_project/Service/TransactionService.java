package com.nutech.test_project.Service;

import com.nutech.test_project.Dto.Request.TopupRequest;
import com.nutech.test_project.Dto.Request.TransactionRequest;
import com.nutech.test_project.Dto.Response.GetTransactionHistory;
import com.nutech.test_project.Dto.Response.ResponseHandling;
import com.nutech.test_project.Dto.Response.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TransactionService {
    ResponseHandling<Map<String, BigDecimal>> balance(String emailFromToken);

    ResponseHandling<Map<String, BigDecimal>> topup(TopupRequest topupRequest, String emailFromToken);

    ResponseHandling<TransactionResponse> transaction(TransactionRequest transactionRequest, String emailFromToken);

    ResponseHandling<List<GetTransactionHistory>> transactionHistory(String emailFromToken, int offset, Integer limit);
}
