package com.nutech.test_project.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {

    @NotBlank(message = "Service code tidak boleh kosong")
    @Pattern(regexp = "^[A-Z_]+$", message = "Service code hanya boleh huruf kapital dan underscore")
    private String service_code;

}
