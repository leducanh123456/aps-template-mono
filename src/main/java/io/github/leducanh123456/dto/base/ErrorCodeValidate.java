package io.github.leducanh123456.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorCodeValidate {
    private String errorCode;
    private String errorMessage;
}
