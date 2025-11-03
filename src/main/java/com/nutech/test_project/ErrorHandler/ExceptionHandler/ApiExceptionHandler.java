package com.nutech.test_project.ErrorHandler.ExceptionHandler;

import com.nutech.test_project.Dto.Response.ResponseHandling;
import com.nutech.test_project.ErrorHandler.ServiceCustomException.CustomBadRequestException;
import com.nutech.test_project.ErrorHandler.ServiceCustomException.CustomFailedUploadFile;
import com.nutech.test_project.ErrorHandler.ServiceCustomException.CustomFileNotValid;
import com.nutech.test_project.ErrorHandler.ServiceCustomException.CustomNotfoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseHandling<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        FieldError firstError = ex.getBindingResult().getFieldErrors().get(0);

        String errorMessage = "Paramter " + firstError.getField() + " " + firstError.getDefaultMessage();

        ResponseHandling<Void> responseHandling = new ResponseHandling<>();
        responseHandling.setStatus("102");
        responseHandling.setMessage(errorMessage);
        responseHandling.setData(null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseHandling);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseHandling<Void>> handleBadCredentialsException(BadCredentialsException ex) {
        ResponseHandling<Void> responseHandling = new ResponseHandling<>();
        responseHandling.setStatus("103");
        responseHandling.setMessage("Username atau password salah");
        responseHandling.setData(null);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseHandling);
    }

    @ExceptionHandler(CustomBadRequestException.class)
    public ResponseEntity<ResponseHandling<Void>> customBadRequestException(CustomBadRequestException ex) {
        ResponseHandling<Void> responseHandling = new ResponseHandling<>();
        responseHandling.setStatus("104");
        responseHandling.setMessage(ex.getMessage());
        responseHandling.setData(null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseHandling);
    }

    @ExceptionHandler(value = {CustomFileNotValid.class})
    public ResponseEntity<Object> customFileNotValid(CustomFileNotValid customFileNotValid){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseHandling responseHandling = new ResponseHandling();
        responseHandling.setStatus("400");
        responseHandling.setMessage(customFileNotValid.getMessage());
        return new ResponseEntity<>(responseHandling, httpStatus);
    }

    @ExceptionHandler(value = {CustomFailedUploadFile.class})
    public ResponseEntity<Object> customFailUploadException(CustomFailedUploadFile customFailUploadException){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseHandling responseHandling = new ResponseHandling();
        responseHandling.setStatus("error");
        responseHandling.setMessage(customFailUploadException.getMessage());
        return new ResponseEntity<>(responseHandling, httpStatus);
    }

    @ExceptionHandler(value = {CustomNotfoundException.class})
    public ResponseEntity<Object> customNotFoundException(CustomNotfoundException customNotfoundException){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ResponseHandling responseHandling = new ResponseHandling();
        responseHandling.setStatus("404");
        responseHandling.setMessage(customNotfoundException.getMessage());
        return new ResponseEntity<>(responseHandling, httpStatus);
    }
}
