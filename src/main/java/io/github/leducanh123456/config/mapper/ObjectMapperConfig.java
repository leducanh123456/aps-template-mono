package io.github.leducanh123456.config.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.util.TimeZone;

public class ObjectMapperConfig {
    private static volatile ObjectMapper instance;

    private ObjectMapperConfig() {
    }

    public static ObjectMapper getInstance() {
        if (instance == null) {
            synchronized (ObjectMapperConfig.class) {
                if (instance == null) {
                    ObjectMapper instanceTmp = new ObjectMapper();
                    instanceTmp.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                    // Đăng ký các module
                    instanceTmp.registerModule(new JavaTimeModule());
                    instanceTmp.registerModule(new Jdk8Module());
                    instanceTmp.registerModule(new ParameterNamesModule());

                    // Cấu hình SerializationFeature
                    instanceTmp.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    instanceTmp.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

                    // Cấu hình DeserializationFeature
                    instanceTmp.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    instanceTmp.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
                    instanceTmp.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);

                    // Cấu hình MapperFeature
                    instanceTmp.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
                    instanceTmp.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

                    // Cấu hình JsonInclude
                    instanceTmp.setSerializationInclusion(JsonInclude.Include.NON_NULL);

                    // Cấu hình thời gian mặc định
                    instanceTmp.setTimeZone(TimeZone.getDefault());
                    instanceTmp.setAnnotationIntrospector(new CustomLogAnnotationIntrospector());
                    instance = instanceTmp;
                }
            }
        }
        return instance;
    }
}
