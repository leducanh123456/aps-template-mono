package io.github.leducanh123456.constant.erorcode;

import lombok.Getter;

@Getter
public enum TestErrorCodeConstant implements ErrorCode {
    INVALID("001", "Invalid input"),
    NOT_FOUND("002", "Not found");

    private final String code;
    private final String message;

    TestErrorCodeConstant(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
