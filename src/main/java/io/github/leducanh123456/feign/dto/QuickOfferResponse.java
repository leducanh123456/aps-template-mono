package io.github.leducanh123456.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: hduong25
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuickOfferResponse {
    private int status;
    private String error;
    private String soaErrorCode;
    private String soaErrorDesc;
    private String clientMessageId;
    private String path;
    private QuickOfferData data;
}
