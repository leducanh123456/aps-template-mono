package io.github.leducanh123456.config.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.TimeZone;

@Configuration
public class ObjectMapperCustomBenConfig {

    @Bean("defaultObjectMapper")
    @Primary
    public ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Đăng ký các module cần thiết
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new ParameterNamesModule());

        // Cấu hình SerializationFeature
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        // Cấu hình DeserializationFeature
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);

        // Cấu hình MapperFeature
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

        // Cấu hình JsonInclude
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Cấu hình thời gian mặc định
        mapper.setTimeZone(TimeZone.getDefault());
        return mapper;
    }

    @Bean(name = "logConfigObjectMapper")
    public ObjectMapper logConfigObjectMapper() {
        ObjectMapper mapperLog = new ObjectMapper();
        mapperLog.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // Đăng ký các module
        mapperLog.registerModule(new JavaTimeModule());
        mapperLog.registerModule(new Jdk8Module());
        mapperLog.registerModule(new ParameterNamesModule());

        // Cấu hình SerializationFeature
        mapperLog.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapperLog.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        // Cấu hình DeserializationFeature
        mapperLog.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapperLog.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapperLog.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);

        // Cấu hình MapperFeature
        mapperLog.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
        mapperLog.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

        // Cấu hình JsonInclude
        mapperLog.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Cấu hình thời gian mặc định
        mapperLog.setTimeZone(TimeZone.getDefault());
        mapperLog.setAnnotationIntrospector(new CustomLogAnnotationIntrospector());
        return mapperLog;
    }
}
