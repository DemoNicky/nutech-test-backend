package com.nutech.test_project.ErrorHandler.ServiceCustomException;

public class CustomFailedUploadFile extends RuntimeException{
    public CustomFailedUploadFile(String message) {
        super(message);
    }

    public CustomFailedUploadFile(String message, Throwable cause) {
        super(message, cause);
    }
}
