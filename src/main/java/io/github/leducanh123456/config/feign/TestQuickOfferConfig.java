package io.github.leducanh123456.config.feign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import io.github.leducanh123456.config.mapper.ObjectMapperConfig;
import io.github.leducanh123456.constant.LogConstant;
import io.github.leducanh123456.dto.base.LogObjectDTO;
import io.github.leducanh123456.exception.TestException;
import io.github.leducanh123456.feign.dto.AuthorizationApiResponse;
import io.github.leducanh123456.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * @author: hduong25
 */

@Slf4j
@RequiredArgsConstructor
public class TestQuickOfferConfig {
    private static final Logger LOGGER = LogManager.getLogger("Console");

    private static final ObjectMapper om = new ObjectMapper();

    @Value("${f88.api.connect.timeout:PT1M}")
    private String connectTimeout;
    @Value("${f88.api.read.timeout:PT1M}")
    private String readTimeout;

    @Bean
    public Request.Options feignOptions() {
        return new Request.Options(Duration.parse(connectTimeout), Duration.parse(readTimeout), true);
    }

    private AuthorizationApiResponse getAuthorFromF88Api() {
        return null;
    }

    @Bean
    public Decoder feignDecoder() {
        return (response, type) -> {
            byte[] bodyData = logResponseData(response);
            return new JacksonDecoder().decode(response.toBuilder().body(bodyData).build(), type);
        };
    }


    @Bean
    @SneakyThrows
    public ErrorDecoder errorDecoder() {
        return ((methodKey, response) -> {
            try {
                logResponseData(response);
            } catch (IOException e) {
                throw new TestException("Message");
            }
            return new TestException("Message");
        });
    }

    private byte[] logResponseData(Response response) throws IOException {
        log.info("[FEIGN RESPONSE] Status: {} | Headers: {}", response.status(), response.headers());
        LogObjectDTO logObjectDTO = writeLogResponseKibana(response);
        byte[] bodyData = toByteArray(response.body().asInputStream());
        var body = new String(bodyData, StandardCharsets.UTF_8);
        logObjectDTO.setResponseContent(body);
        String logMessage = ObjectMapperConfig.getInstance().writeValueAsString(logObjectDTO);
        LOGGER.info(logMessage);
        log.info(logMessage);
        return bodyData;
    }

    public LogObjectDTO writeLogResponseKibana(Response response) {
        LogObjectDTO logObjectDTO = LogUtil.createLog();
        logObjectDTO.setResponseCode("" + response.status());
        logObjectDTO.setLogType(LogConstant.LOG_TYPE_CALL_API_RESPONSE);
        logObjectDTO.setUrl(response.request().url());
        logObjectDTO.setMethod(response.request().httpMethod().name());
        return logObjectDTO;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            LogObjectDTO logObjectDTO = LogUtil.createLog();
            requestTemplate.header("Content-Type", "application/json");
            AuthorizationApiResponse authorizationApiResponse = this.getAuthorFromF88Api();
            String accessToken = authorizationApiResponse.getAccessToken();
            requestTemplate.header("Authorization", "Bearer " + accessToken);
            requestTemplate.header("clientMessageId", logObjectDTO.getClientMessageId());
            requestTemplate.headers();
            writeLogRequestKibana(requestTemplate, logObjectDTO);
        };
    }

    public void writeLogRequestKibana(RequestTemplate requestTemplate, LogObjectDTO logObjectDTO) {
        logObjectDTO.setLogType(LogConstant.LOG_TYPE_CALL_API_REQUEST);
        logObjectDTO.setUrl(requestTemplate.feignTarget().url() + requestTemplate.url());
        logObjectDTO.setMethod(requestTemplate.method());
        logObjectDTO.setRequestContent(requestTemplate.body() != null ? new String(requestTemplate.body(), StandardCharsets.UTF_8) : null);
        try {
            String logMessage = ObjectMapperConfig.getInstance().writeValueAsString(logObjectDTO);
            LOGGER.info(logMessage);
            log.info(logMessage);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException", e);
        }
    }

    private byte[] toByteArray(InputStream inputStream) throws IOException {
        var buffer = new ByteArrayOutputStream();
        int nRead;
        var data = new byte[LogConstant.LOG_STREAM_SIZE];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();
    }
}
