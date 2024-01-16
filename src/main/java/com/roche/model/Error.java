package com.roche.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {
    private String field;
    private String message;
}