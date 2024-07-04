package dev.ehyeon.surl.presentation;

import dev.ehyeon.surl.common.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(
            CustomException exception
    ) {
        return new ResponseEntity<>(exception.getMessage(), exception.getHttpStatus());
    }
}
