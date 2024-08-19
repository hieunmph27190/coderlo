package com.hieucoder.coderlo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hieucoder.coderlo.dto.respone.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<Object>> handlingException(Exception exception) {
        log.info(exception.toString());
        exception.printStackTrace();
        ApiResponse<Object> apiRespone = ApiResponse.<Object>builder()
                .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiRespone);
    }

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse<Object>> handlingRuntimeException(RuntimeException exception) {
        log.info(exception.toString());
        exception.printStackTrace();
        ApiResponse<Object> apiRespone = ApiResponse.<Object>builder()
                .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiRespone);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<String> handlingAppExcaption(AppException exception) {
        ApiResponse<String> apiRespone = ApiResponse.<String>builder()
                .code(exception.getErrorCode().getCode())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handlingNotValidException(MethodArgumentNotValidException exception) {

        // Kiểm tra nếu getFieldError() trả về null
        FieldError fieldError = exception.getFieldError();
        String errorMessage = (fieldError != null) ? fieldError.getDefaultMessage() : "Invalid request";

        ApiResponse<Object> apiRespone =
                ApiResponse.<Object>builder().code(400).message(errorMessage).build();

        return new ResponseEntity<>(apiRespone, HttpStatus.BAD_REQUEST);
    }
}
