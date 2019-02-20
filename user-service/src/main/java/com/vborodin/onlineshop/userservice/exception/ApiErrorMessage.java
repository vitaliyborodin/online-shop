package com.vborodin.onlineshop.userservice.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
class ApiErrorMessage{
    private String status;
    private String error;
    private String message;

    ApiErrorMessage(Exception ex, HttpStatus status){
        this.status = String.valueOf(status);
        this.error = status.getReasonPhrase();
        this.message = ex.getMessage();
    }

    ApiErrorMessage(ApiException ex){
        this.status = String.valueOf(ex.getStatus());
        this.error = ex.getStatus().getReasonPhrase();
        this.message = ex.getMessage();
    }
}
