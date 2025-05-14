package io.github.leducanh123456.aop.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.leducanh123456.dto.base.LogObjectDTO;
import io.github.leducanh123456.util.LogUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@Slf4j
public class LoggingAspectRequest {

    private static final Logger LOGGER  = LogManager.getLogger("Console");
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    public LoggingAspectRequest(@Qualifier("logConfigObjectMapper") ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) " +
            "&& !@annotation(io.github.leducanh123456.anotation.log.ExcludeLogMethod)")
    public void controllerPointcut() {
    }

    @Before("controllerPointcut()")
    public void logRequest(JoinPoint joinPoint) throws Throwable {
        LogObjectDTO logObjectDTO = LogUtil.createLog();
        LogUtil.setBasicInformationLogRequest(joinPoint, logObjectDTO);
        logObjectDTO.setHost(request.getServerName());
        logObjectDTO.setHostIp(LogUtil.getHostIp(request));
        logObjectDTO.setMessage(LogUtil.buildMessageRequest(request));
        logObjectDTO.setMethod(request.getMethod());
        logObjectDTO.setMsg(LogUtil.buildMessageRequest(request));
        logObjectDTO.setPath(request.getRequestURI());
        String fullUrl = LogUtil.getDomain(request);
        logObjectDTO.setUrl(fullUrl);
        Level currentLevel = LOGGER.getLevel();
        logObjectDTO.setLevelName(currentLevel.getStandardLevel().name());
        logObjectDTO.setRequestContent(objectMapper.writeValueAsString(extractRequestBody(joinPoint)));
        LOGGER.info(objectMapper.writeValueAsString(logObjectDTO));
    }

    private Object extractRequestBody(JoinPoint joinPoint) {
        List<Object> requestBodies = new ArrayList<>();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Parameter[] parameters = signature.getMethod().getParameters();
        Object[] args = joinPoint.getArgs();
        for (var i = 0; i < parameters.length; i++) {
            if (LogUtil.isValidRequestBody(args[i])) {
                requestBodies.add(args[i]);
            }
        }
        return requestBodies.size() == 1 ? requestBodies.getFirst() : requestBodies;
    }
}
