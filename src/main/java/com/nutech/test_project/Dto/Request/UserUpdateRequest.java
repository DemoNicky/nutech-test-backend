package com.nutech.test_project.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

    @NotBlank(message = "tidak boleh kosong")
    @Size(min = 2, max = 50, message = "minimal 2 dan maksimal 50 karakter")
    private String first_name;

    @Size(min = 2, max = 50, message = "minimal 2 dan maksimal 50 karakter")
    private String last_name;

}
