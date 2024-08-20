package com.hieucoder.coderlo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "UNCATEGORIZED_EXCEPTION", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "INVALID_KEY", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "USER_EXISTED", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "USERNAME_INVALID", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "INVALID_PASSWORD", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "USER_NOT_EXISTED", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "UNAUTHORIZEDn", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "INVALID_DOB", HttpStatus.BAD_REQUEST),
    TOKEN_INVALID(2000, "TOKEN_INVALID", HttpStatus.UNAUTHORIZED);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
