package io.github.leducanh123456.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private String statusCode;
    private String message;
    private UUID refNo;
}