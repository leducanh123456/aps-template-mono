package io.github.leducanh123456.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDataDTO<T> {
    private String errorCode;
    private String errorMessage;
    private UUID refNo;
    private T data;
}
