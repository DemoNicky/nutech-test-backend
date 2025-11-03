package com.nutech.test_project.Dto.Request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TopupRequest {

    @NotNull(message = "Parameter amount tidak boleh kosong")
    @DecimalMin(value = "0.0", inclusive = false, message = "Parameter amount hanya boleh angka dan tidak boleh lebih kecil dari 0")
    @Digits(integer = 12, fraction = 2, message = "Format amount tidak valid (maks 12 digit dan 2 desimal)")
    private BigDecimal top_up_amount;

}
