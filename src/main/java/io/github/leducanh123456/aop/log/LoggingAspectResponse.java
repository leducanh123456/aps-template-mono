package io.github.leducanh123456.aop.log;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.leducanh123456.constant.LogConstant;
import io.github.leducanh123456.dto.base.LogObjectDTO;
import io.github.leducanh123456.util.LogUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

@Aspect
@Component
@Slf4j
public class LoggingAspectResponse {

    private static final Logger LOGGER = LogManager.getLogger("Console");
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    public LoggingAspectResponse(@Qualifier("logConfigObjectMapper") ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) " +
            "&& !@annotation(io.github.leducanh123456.anotation.log.ExcludeLogMethod)")
    public void controllerPointcut() {
    }

    @Around("controllerPointcut()")
    public Object logAroundControllers(ProceedingJoinPoint joinPoint) throws Throwable {
        LogObjectDTO logObjectDTO = LogUtil.createLog();
        LogUtil.setBasicInformationLogResponse(joinPoint, logObjectDTO);
        logObjectDTO.setHost(request.getServerName());
        logObjectDTO.setHostIp(LogUtil.getHostIp(request));
        Level currentLevel = LOGGER.getLevel();
        logObjectDTO.setLevelName(currentLevel.getStandardLevel().name());
        logObjectDTO.setMethod(request.getMethod());
        logObjectDTO.setPath(request.getRequestURI());
        logObjectDTO.setMessage(LogUtil.buildMessageRequest(request));
        logObjectDTO.setMethod(request.getMethod());
        logObjectDTO.setMsg(LogUtil.buildMessageRequest(request));
        String fullUrl = LogUtil.getDomain(request);
        logObjectDTO.setUrl(fullUrl);
        long startTime = System.currentTimeMillis();
        Object result;
        Boolean requestState = true;
        try {
            result = joinPoint.proceed();
            if (result instanceof ResponseEntity<?> responseEntity) {
                if (!(responseEntity.getBody() instanceof Resource)) {
                    logObjectDTO.setResponseContent(objectMapper.writeValueAsString(responseEntity.getBody()));
                    HttpStatusCode statusCode = responseEntity.getStatusCode();
                    logObjectDTO.setResponseCode("" + statusCode.value());
                }
            } else {
                if (!(result instanceof Resource)) {
                    logObjectDTO.setResponseContent(objectMapper.writeValueAsString(result));
                    logObjectDTO.setStatus("200");
                }
            }
            return result;
        } catch (RuntimeException e) {
            requestState = false;
            try (var sw = new StringWriter(); var pw = new PrintWriter(sw)) {
                e.printStackTrace(pw);
                logObjectDTO.setStackInfo(sw.toString());
            }
            logObjectDTO.setResponseMsg(e.getMessage());
            logObjectDTO.setState(LogConstant.LOG_STATE_FAIL);
            throw e;
        } finally {
            logObjectDTO.setMsg(LogUtil.buildMessageResponse(request, requestState));
            logObjectDTO.setMessage(LogUtil.buildMessageResponse(request, requestState));
            logObjectDTO.setDuration(System.currentTimeMillis() - startTime);
            LOGGER.info(objectMapper.writeValueAsString(logObjectDTO));
        }
    }

}
