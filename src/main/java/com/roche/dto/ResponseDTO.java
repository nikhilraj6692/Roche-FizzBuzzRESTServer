package com.roche.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@JsonInclude(Include.NON_NULL)
public class ResponseDTO {
    private String code;
    private String message;
    private Object response;
    private List<ErrorResponseDTO> errors;

    @Data
    @Builder(toBuilder = true)
    @JsonInclude(Include.NON_NULL)
    public static class ErrorResponseDTO {
        private String code;
        private String message;
        private Object[] params;
    }

}
