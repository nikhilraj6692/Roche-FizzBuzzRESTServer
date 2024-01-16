package com.roche.dto;

import static com.roche.util.Constants.LIMIT_VIOLATION_MSG;
import static com.roche.util.Constants.REPLACEMENT_STR_VIOLATION_MSG;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FizzBuzzRequestDTO
{
    private int int1;

    private int int2;

    @Min(value = 1, message = LIMIT_VIOLATION_MSG)
    private int limit;

    @NotBlank(message = REPLACEMENT_STR_VIOLATION_MSG)
    private String str1;

    @NotBlank(message = REPLACEMENT_STR_VIOLATION_MSG)
    private String str2;

}
