package io.github.leducanh123456.config.exception;

import io.github.leducanh123456.constant.ResponseConstant;
import io.github.leducanh123456.dto.base.ErrorResponseDTO;
import io.github.leducanh123456.dto.base.ErrorResponseDataDTO;
import io.github.leducanh123456.util.SecurityUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handeException(Exception e, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setStatusCode(ResponseConstant.ResponseCode.INTERNAL_ERROR);
        errorResponseDTO.setMessage(e.getMessage());
        errorResponseDTO.setErrorTime(LocalDateTime.now());
        errorResponseDTO.setApiPath(request.getDescription(false));
        errorResponseDTO.setRefNo(UUID.fromString(Objects.requireNonNull(SecurityUtil.getRefNo())));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        List<ObjectError> validateErrors = ex.getBindingResult().getAllErrors();
        validateErrors.forEach(error -> {
            String fileName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fileName, errorMessage);
        });
        ErrorResponseDataDTO<Map<String, String>> errorResponseDataDTO = new ErrorResponseDataDTO<>();
        errorResponseDataDTO.setStatusCode(ResponseConstant.ResponseCode.INTERNAL_ERROR);
        errorResponseDataDTO.setMessage(ex.getMessage());
        errorResponseDataDTO.setErrorTime(LocalDateTime.now());
        errorResponseDataDTO.setApiPath(request.getDescription(false));
        errorResponseDataDTO.setRefNo(UUID.fromString(Objects.requireNonNull(SecurityUtil.getRefNo())));
        errorResponseDataDTO.setData(errors);
        return new ResponseEntity<>(errorResponseDataDTO, HttpStatus.BAD_REQUEST);
    }
}
