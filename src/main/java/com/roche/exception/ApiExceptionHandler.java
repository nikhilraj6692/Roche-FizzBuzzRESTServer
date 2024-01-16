package com.roche.exception;

import com.roche.util.ResponseUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import com.roche.model.Error;
@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity handleConstraintViolationException(MethodArgumentNotValidException ex, WebRequest request) {
        List<Error> errors = new ArrayList<>();
        ex.getAllErrors().stream().forEach(
            error -> {
                log.error("Constraint violation exception occurred for key: {}",  ((FieldError) error).getField());
                errors.add(Error.builder()
                        .message(error.getDefaultMessage())
                        .field(((FieldError) error).getField())
                        .build());
            });

        return ResponseUtil.generateErrorResponse(errors, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleGenericException(Exception ex, WebRequest request) {
        List<Error> errors = new ArrayList<>();
        errors.add(Error.builder()
            .message(ex.getLocalizedMessage())
            .build());

        return ResponseUtil.generateErrorResponse(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


