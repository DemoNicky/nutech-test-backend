package com.nutech.test_project.Dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateResponse {

    private String email;

    private String first_name;

    private String last_name;

    private String profile_image;

}
