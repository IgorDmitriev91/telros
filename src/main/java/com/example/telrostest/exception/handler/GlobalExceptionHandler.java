package com.example.telrostest.exception.handler;

import com.example.telrostest.dto.ErrorMessage;
import com.example.telrostest.exception.UserNotFoundException;
import com.example.telrostest.exception.NoValidData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    private ResponseEntity<ErrorMessage> handle(Exception ex, HttpStatus httpStatus) {

        if (httpStatus.value() >= 500) {
            log.error("response code: {} exception: {}, message: {}",
                    httpStatus.value(),
                    ex.getClass().getName(),
                    ex.getMessage()
            );
        } else {
            log.warn("response code: {} exception: {}, message {}",
                    httpStatus.value(),
                    ex.getClass().getName(),
                    ex.getMessage()
            );
        }
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), httpStatus);
    }

    @ExceptionHandler(NoValidData.class)
    public ResponseEntity<ErrorMessage> handleAlreadyExistsException(NoValidData ex) {
        return handle(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(UserNotFoundException ex) {
        return handle(ex, HttpStatus.NOT_FOUND);
    }
}