package com.vborodin.onlineshop.productservice.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
class ApiErrorMessage{
    String status;
    String error;
    String message;

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
