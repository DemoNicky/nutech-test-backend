package com.nutech.test_project.ErrorHandler.ServiceCustomException;

public class CustomNotfoundException extends RuntimeException{
    public CustomNotfoundException(String message) {
        super(message);
    }

    public CustomNotfoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
