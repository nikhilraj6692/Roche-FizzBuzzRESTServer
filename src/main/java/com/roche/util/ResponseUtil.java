package com.roche.util;

import com.roche.dto.ResponseDTO;
import com.roche.model.Error;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil
{

    private static final MessageSource messageSource;

    static {
        messageSource = SpringContextUtil.getBean(MessageSource.class);
    }

    public static ResponseEntity generateErrorResponse(List<Error> errors, HttpStatus httpStatus) {
        List<ResponseDTO.ErrorResponseDTO> errorResponse = Optional.ofNullable(errors)
            .orElse(Collections.emptyList()).stream().map(error ->
                ResponseDTO.ErrorResponseDTO.builder()
                    .code(error.getMessage())
                    .message(
                        messageSource.getMessage(error.getMessage(), null, Locale.getDefault()))
                    .build()).collect(Collectors.toList());

        ResponseDTO response = ResponseDTO.builder()
            .errors(errorResponse)
            .build();
        return new ResponseEntity(response, httpStatus);
    }

    public static ResponseEntity generateResponse(String code, Object output, HttpStatus httpStatus, String... args) {
        ResponseDTO response = ResponseDTO.builder()
            .code(code)
            .message(messageSource.getMessage(code, args, Locale.getDefault()))
            .response(output)
            .build();
        return new ResponseEntity(response, httpStatus);
    }
}
