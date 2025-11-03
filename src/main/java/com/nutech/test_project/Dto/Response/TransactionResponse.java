package com.nutech.test_project.Dto.Response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponse {

    private String invoice_number;

    private String service_code;

    private String service_name;

    private String transaction_type;

    private BigDecimal total_amount;

    private LocalDateTime created_on;

}
