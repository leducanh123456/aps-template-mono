package io.github.leducanh123456.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.leducanh123456.config.mapper.ObjectMapperConfig;
import io.github.leducanh123456.constant.LogConstant;
import io.github.leducanh123456.dto.base.LogObjectDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
public final class LogUtil {
    private static final Logger LOGGER = LogManager.getLogger("Kafka");

    private LogUtil() {
    }

    public static LogObjectDTO createLog() {
        LogObjectDTO logObjectDTO = new LogObjectDTO();
        logObjectDTO.setState(LogConstant.LOG_STATE_SUCCESS);
        logObjectDTO.setStartTime(System.currentTimeMillis());
        logObjectDTO.setClientMessageId(SecurityUtil.getRequestId());
        logObjectDTO.setReqNo(SecurityUtil.getRefNo());
        if (ObjectUtils.isEmpty(logObjectDTO.getClientMessageId())) {
            logObjectDTO.setClientMessageId(UUID.randomUUID().toString());
        }
        logObjectDTO.setServiceName(LogConstant.SERVICE_NAME);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        setUserInLog(authentication, logObjectDTO);
        logObjectDTO.setSourcesApp(LogConstant.SOURCE_APP);
        logObjectDTO.setStatus(LogConstant.STATUS);
        logObjectDTO.setThread(Thread.currentThread().threadId());
        logObjectDTO.setThreadName(Thread.currentThread().getName());
        logObjectDTO.setTimestamp(System.currentTimeMillis());
        logObjectDTO.setTypeSystem(LogConstant.TYPE_SYSTEM);
        logObjectDTO.setState(LogConstant.LOG_STATE_SUCCESS);
        return logObjectDTO;
    }

    private static void setUserInLog(Authentication authentication, LogObjectDTO logObjectDTO) {
        if (!ObjectUtils.isEmpty(authentication)) {
            if (authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails userDetails) {
                    logObjectDTO.setUsername(userDetails.getUsername());
                } else if (principal instanceof String str) {
                    logObjectDTO.setUsername(str);
                } else {
                    logObjectDTO.setUsername(authentication.getName()); // fallback
                }
            } else {
                logObjectDTO.setUsername(authentication.getName());
            }
        } else {
            logObjectDTO.setUsername("Anonymous");
        }
    }

    public static String buildMessageRequest(HttpServletRequest request) {
        var message = new StringBuilder();
        message.append(LogConstant.MESSAGE_TYPE_REQUEST);
        message.append(LogConstant.MESSAGE_TYPE_SPACE);
        if (!ObjectUtils.isEmpty(request.getQueryString())) {
            message.append(request.getRequestURI()).append("?").append(request.getQueryString());
        } else {
            message.append(request.getRequestURI());
        }
        return message.toString();
    }

    public static String buildMessageResponse(HttpServletRequest request, Boolean requestState) {
        var message = new StringBuilder();
        message.append(LogConstant.MESSAGE_TYPE_REQUEST);
        message.append(LogConstant.MESSAGE_TYPE_SPACE);
        if (!ObjectUtils.isEmpty(requestState) && Boolean.TRUE.equals(requestState)) {
            message.append(LogConstant.REQUEST_STATE_SUCCESS);
            message.append(LogConstant.MESSAGE_TYPE_SPACE);
        } else {
            message.append(LogConstant.REQUEST_STATE_FAIL);
            message.append(LogConstant.MESSAGE_TYPE_SPACE);
        }
        if (!ObjectUtils.isEmpty(request.getQueryString())) {
            message.append(request.getRequestURI()).append("?").append(request.getQueryString());
        } else {
            message.append(request.getRequestURI());
        }
        return message.toString();
    }

    public static String getHostIp(HttpServletRequest request) {
        String ip = null;
        var headerIp = request.getHeader("X-Real-IP");
        if (headerIp != null && !headerIp.isEmpty()) {
            return headerIp;
        }
        headerIp = request.getHeader("X-Forwarded-For");
        if (headerIp != null && !headerIp.isEmpty()) {
            return headerIp.split(",")[0].trim();
        }
        ip = request.getRemoteAddr();
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            try {
                var inetAddress = InetAddress.getLocalHost();
                ip = inetAddress.getHostAddress();
            } catch (UnknownHostException e) {
                ip = "127.0.0.1";
                log.error("error ", e);
            }
        }
        return ip;
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");
        return dateTime.format(formatter);
    }

    public static void setBasicInformationLogRequest(JoinPoint joinPoint, LogObjectDTO logObjectDTO) {
        logObjectDTO.setAscTime(LogUtil.formatDateTime(LocalDateTime.now()));
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class<?> declaringClass = signature.getDeclaringType();
        String fileName = declaringClass.getSimpleName() + ".java";
        String packageName = declaringClass.getPackage().getName();
        String methodName = signature.getName();
        logObjectDTO.setFileName(packageName + "." + fileName);
        logObjectDTO.setFuncName(methodName);
        logObjectDTO.setModule(LogConstant.MODULE_REQUEST);
        logObjectDTO.setName(LogConstant.MODULE_NAME);
        logObjectDTO.setLogType(LogConstant.LOG_TYPE_REQUEST);
        logObjectDTO.setPathName(packageName + "." + fileName);
        logObjectDTO.setProcess(Thread.currentThread().threadId());
        logObjectDTO.setProcessName(Thread.currentThread().getName());
        logObjectDTO.setServiceName(LogConstant.SERVICE_NAME);
        logObjectDTO.setSourcesApp(LogConstant.SOURCE_APP);
        logObjectDTO.setStatus(LogConstant.STATUS);
        logObjectDTO.setThread(Thread.currentThread().threadId());
        logObjectDTO.setThreadName(Thread.currentThread().getName());
        logObjectDTO.setTimestamp(System.currentTimeMillis());
        logObjectDTO.setTypeSystem(LogConstant.TYPE_SYSTEM);
        logObjectDTO.setState(LogConstant.LOG_STATE_SUCCESS);

    }

    public static void setBasicInformationLogResponse(JoinPoint joinPoint, LogObjectDTO logObjectDTO) {

        logObjectDTO.setAscTime(LogUtil.formatDateTime(LocalDateTime.now()));
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class<?> declaringClass = signature.getDeclaringType();
        String fileName = declaringClass.getSimpleName() + ".java";
        String packageName = declaringClass.getPackage().getName();
        String methodName = signature.getName();
        logObjectDTO.setFileName(packageName + "." + fileName);
        logObjectDTO.setFuncName(methodName);
        logObjectDTO.setModule(LogConstant.MODULE_REQUEST);
        logObjectDTO.setName(LogConstant.MODULE_NAME);
        logObjectDTO.setLogType(LogConstant.LOG_TYPE_RESPONSE);
        logObjectDTO.setPathName(packageName + "." + fileName);
        logObjectDTO.setProcess(Thread.currentThread().threadId());
        logObjectDTO.setProcessName(Thread.currentThread().getName());
        logObjectDTO.setServiceName(LogConstant.SERVICE_NAME);
        logObjectDTO.setSourcesApp(LogConstant.SOURCE_APP);
        logObjectDTO.setStatus(LogConstant.STATUS);
        logObjectDTO.setThread(Thread.currentThread().threadId());
        logObjectDTO.setThreadName(Thread.currentThread().getName());
        logObjectDTO.setTimestamp(System.currentTimeMillis());
        logObjectDTO.setTypeSystem(LogConstant.TYPE_SYSTEM);
        logObjectDTO.setState(LogConstant.LOG_STATE_SUCCESS);
    }

    public static void writeLog(LogObjectDTO logObjectDTO) {
        logObjectDTO.setEndTime(System.currentTimeMillis());
        try {
            Object dataWriteLog = logObjectDTO.getDataWriteLog();
            Object dataPreWriteLog = logObjectDTO.getDataPreWriteLog();
            if (!ObjectUtils.isEmpty(dataWriteLog)) {
                logObjectDTO.setData(ObjectMapperConfig.getInstance().writeValueAsString(dataWriteLog));
            }
            if (!ObjectUtils.isEmpty(dataPreWriteLog)) {
                logObjectDTO.setPreData(ObjectMapperConfig.getInstance().writeValueAsString(dataPreWriteLog));
            }
            logObjectDTO.setDuration(logObjectDTO.getStartTime() - logObjectDTO.getEndTime());
            if (!ObjectUtils.isEmpty(logObjectDTO.getException())) {
                var sw = new StringWriter();
                var pw = new PrintWriter(sw);
                logObjectDTO.getException().printStackTrace(pw);
                logObjectDTO.setStackInfo(sw.toString());
            }
            LOGGER.info(ObjectMapperConfig.getInstance().writeValueAsString(logObjectDTO));
        } catch (JsonProcessingException e) {
            log.error("Error convert object to str ", e);
        }
    }

    public static String getDomain(HttpServletRequest request) {
        var domain = request.getHeader(LogConstant.X_FORWARDED_HOST);
        if (domain == null || domain.isEmpty()) {
            domain = request.getServerName();
        }
        var port = request.getHeader(LogConstant.X_FORWARDED_PORT);
        if (port == null || port.isEmpty()) {
            int serverPort = request.getServerPort();
            if (serverPort != LogConstant.PORT_HTTP && serverPort != LogConstant.PORT_HTTPS) {
                port = String.valueOf(serverPort);
            }
        }
        var scheme = request.getHeader(LogConstant.X_FORWARDED_PROTO);
        if (scheme == null || scheme.isEmpty()) {
            scheme = request.getScheme();
        }

        var fullUrl = appendPortAndRequestParam(request, scheme, domain, port);

        return fullUrl.toString();
    }

    private static StringBuilder appendPortAndRequestParam(HttpServletRequest request, String scheme, String domain, String port) {
        var fullUrl = new StringBuilder(scheme).append("://").append(domain);
        if (port != null && !port.isEmpty() && !(LogConstant.PORT_HTTP_STR.equals(port) && LogConstant.HTTP.equalsIgnoreCase(scheme))
                && !(LogConstant.PORT_HTTPS_STR.equals(port) && LogConstant.HTTPS.equalsIgnoreCase(scheme))) {
            fullUrl.append(":").append(port);
        }

        var queryString = request.getQueryString();
        if (queryString != null && !queryString.isEmpty()) {
            fullUrl.append("?").append(queryString);
        }
        return fullUrl;
    }

    public static boolean isValidRequestBody(Object arg) {
        return arg != null &&
                !(arg instanceof MultipartFile) &&
                !(arg instanceof MultipartFile[]) &&
                !(arg instanceof HttpServletRequest) &&
                !(arg instanceof HttpServletResponse);
    }
}
