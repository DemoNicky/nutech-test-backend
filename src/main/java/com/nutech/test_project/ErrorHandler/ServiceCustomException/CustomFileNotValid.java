package com.nutech.test_project.ErrorHandler.ServiceCustomException;

public class CustomFileNotValid extends RuntimeException {

    public CustomFileNotValid(String message) {
        super(message);
    }

    public CustomFileNotValid(String message, Throwable cause) {
        super(message, cause);
    }

}
