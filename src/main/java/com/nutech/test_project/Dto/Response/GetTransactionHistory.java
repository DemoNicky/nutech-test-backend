package com.nutech.test_project.Dto.Response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class GetTransactionHistory {

    private String invoice_number;

    private String transaction_type;

    private String description;

    private BigDecimal total_amount;

    private LocalDateTime created_on;

}
