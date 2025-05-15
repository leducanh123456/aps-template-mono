package io.github.leducanh123456.config.exception;

import io.github.leducanh123456.constant.ResponseConstant;
import io.github.leducanh123456.dto.base.ErrorCodeValidate;
import io.github.leducanh123456.dto.base.ErrorResponseDTO;
import io.github.leducanh123456.dto.base.ErrorResponseDataDTO;
import io.github.leducanh123456.util.SecurityUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
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
        errorResponseDTO.setErrorCode(ResponseConstant.ResponseCode.INTERNAL_ERROR);
        errorResponseDTO.setErrorMessage(e.getMessage());
        errorResponseDTO.setErrorTime(LocalDateTime.now());
        errorResponseDTO.setApiPath(request.getDescription(false));
        errorResponseDTO.setRefNo(UUID.fromString(Objects.requireNonNull(SecurityUtil.getRefNo())));
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, ErrorCodeValidate> errors = new HashMap<>();
        List<ObjectError> validateErrors = ex.getBindingResult().getAllErrors();
        validateErrors.forEach(error -> {
            String fieldName = (error instanceof FieldError) ? ((FieldError) error).getField() : error.getObjectName();
            String errorMessage = error.getDefaultMessage();
            ErrorCodeValidate errorValidate = new ErrorCodeValidate();
            if(ObjectUtils.isEmpty(errorMessage)){
                errorValidate.setErrorCode("SYSTEM_ERROR");
                errorValidate.setErrorMessage("system error");
            } else {
                String[] codeAndMessage = errorMessage.split("\\|");
                if(codeAndMessage.length == 2){
                    errorValidate.setErrorCode(codeAndMessage[0]);
                    errorValidate.setErrorMessage(codeAndMessage[1]);
                } else {
                    errorValidate.setErrorCode("SYSTEM_ERROR");
                    errorValidate.setErrorMessage(codeAndMessage[0]);
                }
            }

            errors.put(fieldName, errorValidate);
        });
        ErrorResponseDataDTO<Map<String, ErrorCodeValidate>> errorResponseDataDTO = new ErrorResponseDataDTO<>();
        if(!errors.isEmpty()){
            Map.Entry<String, ErrorCodeValidate> entry = errors.entrySet().iterator().next();
            errorResponseDataDTO.setErrorCode(entry.getValue().getErrorCode());
            errorResponseDataDTO.setErrorMessage(entry.getValue().getErrorMessage());
        } else {
            errorResponseDataDTO.setErrorCode("SYSTEM_ERROR");
            errorResponseDataDTO.setErrorMessage(ex.getMessage());

        }
        errorResponseDataDTO.setErrorTime(LocalDateTime.now());
        errorResponseDataDTO.setApiPath(request.getDescription(false));
        errorResponseDataDTO.setRefNo(UUID.fromString(Objects.requireNonNull(SecurityUtil.getRefNo())));
        errorResponseDataDTO.setData(errors);
        return new ResponseEntity<>(errorResponseDataDTO, HttpStatus.BAD_REQUEST);
    }
}
