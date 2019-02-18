package com.vborodin.onlineshop.userservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
	@Getter
	private final HttpStatus status;

	public ApiException(String message, HttpStatus status) {
		super(message);	
		this.status = status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public ApiException(String message) {
		super(message);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
