package io.github.leducanh123456.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AuthorizationApiRequest {
    @JsonProperty("grant_type")
    private String grantType;
}