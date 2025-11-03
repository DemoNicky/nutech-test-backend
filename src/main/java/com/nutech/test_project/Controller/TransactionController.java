package com.nutech.test_project.Controller;

import com.nutech.test_project.Dto.Request.TopupRequest;
import com.nutech.test_project.Dto.Request.TransactionRequest;
import com.nutech.test_project.Dto.Response.GetTransactionHistory;
import com.nutech.test_project.Dto.Response.ResponseHandling;
import com.nutech.test_project.Dto.Response.TransactionResponse;
import com.nutech.test_project.Service.TransactionService;
import com.nutech.test_project.Utils.TokenUtils.ExtractTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    private final ExtractTokenUtil extractTokenUtil;

    @GetMapping(value = "/balance")
    public ResponseEntity<ResponseHandling<Map<String, BigDecimal>>> balance(){
        ResponseHandling<Map<String, BigDecimal>> responseHandling = transactionService.balance(extractTokenUtil.getEmailFromToken());
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

    @GetMapping(value = "/transaction/history")
    public ResponseEntity<ResponseHandling<List<GetTransactionHistory>>> transactionHistory(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false) Integer limit) {
        ResponseHandling<List<GetTransactionHistory>> responseHandling = transactionService.transactionHistory(extractTokenUtil.getEmailFromToken(), offset, limit);
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

    @PostMapping(value = "/topup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseHandling<Map<String, BigDecimal>>> topup(@Valid @RequestBody TopupRequest topupRequest){
        ResponseHandling<Map<String, BigDecimal>> responseHandling = transactionService.topup(topupRequest, extractTokenUtil.getEmailFromToken());
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

    @PostMapping(value = "/transaction", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseHandling<TransactionResponse>> transaction(@Valid @RequestBody TransactionRequest transactionRequest){
        ResponseHandling<TransactionResponse> responseHandling = transactionService.transaction(transactionRequest, extractTokenUtil.getEmailFromToken());
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }



}
