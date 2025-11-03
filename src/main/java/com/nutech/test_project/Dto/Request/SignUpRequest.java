package com.nutech.test_project.Dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

    @NotBlank(message = "tidak boleh kosong")
    @Email(message = "tidak sesuai format email")
    private String email;

    @NotBlank(message = "tidak boleh kosong")
    @Size(min = 2, max = 50, message = "minimal 2 dan maksimal 50 karakter")
    private String first_name;

    @NotBlank(message = "tidak boleh kosong")
    @Size(min = 2, max = 50, message = "minimal 2 dan maksimal 50 karakter")
    private String last_name;

    @NotBlank(message = "tidak boleh kosong")
    @Size(min = 8, max = 100, message = "minimal 6 karakter")
    private String password;

}
