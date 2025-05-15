package io.github.leducanh123456.config.feign;

import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;


/**
 * @author: hduong25
 */

public class TestAuthorizationConfig {
    @Value("${cntt.api.client-id}")
    private String clientId;

    @Value("${cntt.api.client-secret}")
    private String clientSecret;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(clientId, clientSecret);
    }

    @Bean
    @Primary
    public Encoder formEncoder() {
        return new FormEncoder();
    }
}
