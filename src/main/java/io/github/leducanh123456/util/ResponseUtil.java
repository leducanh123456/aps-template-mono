package io.github.leducanh123456.util;

import io.github.leducanh123456.dto.base.ResponseDTO;
import io.github.leducanh123456.dto.base.ResponseDataDTO;

import java.util.Objects;
import java.util.UUID;

public class ResponseUtil {
    private ResponseUtil() {}
    public static<T> ResponseDataDTO<T> ok(T t){
        ResponseDataDTO<T> dto = new ResponseDataDTO<T>();
        dto.setData(t);
        dto.setErrorMessage("ok");
        dto.setRefNo(UUID.fromString(Objects.requireNonNull(SecurityUtil.getRefNo())));
        dto.setErrorCode("00");
        return dto;
    }
    public static ResponseDTO ok(){
        ResponseDTO dto = new ResponseDTO();
        dto.setMessage("ok");
        dto.setRefNo(UUID.fromString(Objects.requireNonNull(SecurityUtil.getRefNo())));
        dto.setStatusCode("00");
        return dto;
    }
}
