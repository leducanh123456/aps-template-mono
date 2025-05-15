package io.github.leducanh123456.feign;


import io.github.leducanh123456.config.feign.TestAuthorizationConfig;
import io.github.leducanh123456.feign.dto.AuthorizationApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author: hduong25
 */

@FeignClient(name = "f88AuthorizationClient",
        url = "${f88.api.authorization}",
        configuration = {TestAuthorizationConfig.class})
public interface TestAuthorizationApi {
    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    AuthorizationApiResponse authorization(@RequestBody Map<String, ?> request);
}