package com.nutech.test_project.Service.Impl;

import com.nutech.test_project.Dto.Request.LoginRequest;
import com.nutech.test_project.Dto.Request.SignUpRequest;
import com.nutech.test_project.Dto.Request.UserUpdateRequest;
import com.nutech.test_project.Dto.Response.GetProfileResponse;
import com.nutech.test_project.Dto.Response.ResponseHandling;
import com.nutech.test_project.Dto.Response.UserUpdateResponse;
import com.nutech.test_project.Entity.Users;
import com.nutech.test_project.ErrorHandler.ServiceCustomException.CustomBadRequestException;
import com.nutech.test_project.ErrorHandler.ServiceCustomException.CustomFailedUploadFile;
import com.nutech.test_project.Repository.BalancesRepository;
import com.nutech.test_project.Repository.UsersRepository;
import com.nutech.test_project.Service.UserService;
import com.nutech.test_project.Utils.ImageUtils.UploadUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImplements implements UserService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private UploadUtils uploadUtils;

    private BalancesRepository balancesRepository;

    @Transactional
    @Override
    public ResponseHandling<Void> register(SignUpRequest signUpRequest) {
        ResponseHandling<Void> responseHandling = new ResponseHandling<>();

        usersRepository.registerUser(signUpRequest.getFirst_name(), signUpRequest.getLast_name(),
                signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));
        Optional<Users> users = usersRepository.findByEmail(signUpRequest.getEmail());

        balancesRepository.createBalance(users.get().getId());

        responseHandling.setStatus("0");
        responseHandling.setMessage("Registrasi berhasil silahkan login");
        return responseHandling;
    }

    @Override
    public ResponseHandling<Map<String, String>> login(LoginRequest loginRequest) {
        ResponseHandling<Map<String, String>> responseHandling = new ResponseHandling<>();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtService.generateAccessToken(authentication);

            Map<String, String> tokenResult = new HashMap<>();
            tokenResult.put("token", token);

            responseHandling.setStatus("0");
            responseHandling.setMessage("Login Sukses");
            responseHandling.setData(tokenResult);
        } catch (Exception e) {
            responseHandling.setStatus("103");
            responseHandling.setMessage("Username atau password salah");
            responseHandling.setData(null);
        }

        return responseHandling;
    }

    @Override
    public ResponseHandling<GetProfileResponse> profile(String emailFromToken) {
        ResponseHandling<GetProfileResponse> responseHandling = new ResponseHandling<>();

        Optional<Users> user = usersRepository.findByEmail(emailFromToken);
        if (!user.isPresent()){
            throw new CustomBadRequestException("user not found");
        }

        GetProfileResponse getProfileResponse = new GetProfileResponse();
        getProfileResponse.setEmail(user.get().getEmail());
        getProfileResponse.setFirst_name(user.get().getFirstName());
        getProfileResponse.setLast_name(user.get().getLastName());
        getProfileResponse.setProfile_image(user.get().getUrlImage());

        responseHandling.setStatus("0");
        responseHandling.setMessage("Sukses");
        responseHandling.setData(getProfileResponse);

        return responseHandling;
    }

    @Override
    public ResponseHandling<UserUpdateResponse> update(UserUpdateRequest userUpdateRequest, String emailFromToken) {
        ResponseHandling<UserUpdateResponse> responseHandling = new ResponseHandling<>();
        int result = usersRepository.updateUser(userUpdateRequest.getFirst_name(), userUpdateRequest.getLast_name(), emailFromToken);
        if (result == 0) {
            log.error("error when update user data");
            throw new CustomBadRequestException("gagal update user");
        }

        Optional<Users> users = usersRepository.findByEmail(emailFromToken);
        UserUpdateResponse userUpdateResponse = new UserUpdateResponse();
        userUpdateResponse.setEmail(users.get().getEmail());
        userUpdateResponse.setFirst_name(users.get().getFirstName());
        userUpdateResponse.setLast_name(users.get().getLastName());
        userUpdateResponse.setProfile_image(users.get().getUrlImage());

        responseHandling.setStatus("0");
        responseHandling.setMessage("Update Pofile berhasil");
        responseHandling.setData(userUpdateResponse);
        return responseHandling;
    }

    @Override
    public ResponseHandling<UserUpdateResponse> updateImage(MultipartFile file, String emailFromToken) {
        ResponseHandling<UserUpdateResponse> responseHandling = new ResponseHandling<>();
        String res;
        try {
            res = uploadUtils.uploadImageResult(file);
        }catch (IOException e){
            log.error(e.toString());
            throw new CustomFailedUploadFile("file error while upload");
        }

        int result = usersRepository.updateImage(res, emailFromToken);
        if (result == 0) {
            log.error("error when update user data");
            throw new CustomBadRequestException("gagal update user");
        }

        Optional<Users> users = usersRepository.findByEmail(emailFromToken);
        UserUpdateResponse userUpdateResponse = new UserUpdateResponse();
        userUpdateResponse.setEmail(users.get().getEmail());
        userUpdateResponse.setFirst_name(users.get().getFirstName());
        userUpdateResponse.setLast_name(users.get().getLastName());
        userUpdateResponse.setProfile_image(users.get().getUrlImage());

        responseHandling.setStatus("0");
        responseHandling.setMessage("Update Pofile Image berhasil");
        responseHandling.setData(userUpdateResponse);
        return responseHandling;
    }


}
