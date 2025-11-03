package com.nutech.test_project.Service;

import com.nutech.test_project.Dto.Request.LoginRequest;
import com.nutech.test_project.Dto.Request.SignUpRequest;
import com.nutech.test_project.Dto.Request.UserUpdateRequest;
import com.nutech.test_project.Dto.Response.GetProfileResponse;
import com.nutech.test_project.Dto.Response.ResponseHandling;
import com.nutech.test_project.Dto.Response.UserUpdateResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UserService {

    ResponseHandling<Void> register(SignUpRequest signUpRequest);

    ResponseHandling<Map<String, String>> login(LoginRequest loginRequest);

    ResponseHandling<GetProfileResponse> profile(String emailFromToken);

    ResponseHandling<UserUpdateResponse> update(UserUpdateRequest userUpdateRequest, String emailFromToken);

    ResponseHandling<UserUpdateResponse> updateImage(MultipartFile file, String emailFromToken);
}
