package com.vborodin.onlineshop.userservice.exception;

import lombok.Data;

@Data
class ApiErrorMessage{
    private String status;
    private String error;
    private String message;
}
