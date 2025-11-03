package com.nutech.test_project.Controller;

import com.nutech.test_project.Dto.Request.LoginRequest;
import com.nutech.test_project.Dto.Request.SignUpRequest;
import com.nutech.test_project.Dto.Request.UserUpdateRequest;
import com.nutech.test_project.Dto.Response.GetProfileResponse;
import com.nutech.test_project.Dto.Response.ResponseHandling;
import com.nutech.test_project.Dto.Response.UserUpdateResponse;
import com.nutech.test_project.ErrorHandler.ServiceCustomException.CustomFailedUploadFile;
import com.nutech.test_project.ErrorHandler.ServiceCustomException.CustomFileNotValid;
import com.nutech.test_project.Service.UserService;
import com.nutech.test_project.Utils.ImageUtils.FileValidator;
import com.nutech.test_project.Utils.TokenUtils.ExtractTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    private final ExtractTokenUtil extractTokenUtil;

    private static final long MIN_FILE_SIZE = 3 * 1024 * 1024;

    private FileValidator fileValidator;

    @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseHandling<Void>> register(@Valid @RequestBody SignUpRequest signUpRequest){
        ResponseHandling<Void> responseHandling = userService.register(signUpRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseHandling<Map<String, String>>> login(@Valid @RequestBody LoginRequest loginRequest){
        ResponseHandling<Map<String, String>> responseHandling = userService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

    @GetMapping(value = "/profile")
    public ResponseEntity<ResponseHandling<GetProfileResponse>> profile(){
        ResponseHandling<GetProfileResponse> responseHandling = userService.profile(extractTokenUtil.getEmailFromToken());
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

    @PutMapping(value = "/profile/update")
    public ResponseEntity<ResponseHandling<UserUpdateResponse>> update(@Valid @RequestBody UserUpdateRequest userUpdateRequest){
        ResponseHandling<UserUpdateResponse> responseHandling = userService.update(userUpdateRequest, extractTokenUtil.getEmailFromToken());
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

    @PutMapping(value = "/profile/image")
    public ResponseEntity<ResponseHandling<UserUpdateResponse>> updateImage(@RequestPart("file")MultipartFile file){

        if (file == null || file.isEmpty()) {
            throw new CustomFileNotValid("file cannot be null or empty");
        }
        if (!fileValidator.isValidFileExtension(file)){
            throw new CustomFileNotValid("file not valid, file extension not allowed");
        }
        if (file.getSize() > MIN_FILE_SIZE) {
            throw new CustomFailedUploadFile("file size cannot be more than 3 MB");
        }
        ResponseHandling<UserUpdateResponse> responseHandling = userService.updateImage(file, extractTokenUtil.getEmailFromToken());
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

}
