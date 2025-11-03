package com.nutech.test_project.Dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseHandling<T> {

    private String status;

    private String message;

    private T data;
}