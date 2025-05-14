package io.github.leducanh123456.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {
    private String statusCode;
    private String apiPath;
    private String message;
    private UUID refNo;
    private LocalDateTime errorTime;
}
