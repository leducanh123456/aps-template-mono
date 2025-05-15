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
public class QuickOfferData {
    private String quickofferId;
    private String approvalDate;
    private String approvalExpirationDate;
    private String finalMaxLoan;
    private String approvalTime;
    private String quickOfferStatus;
    private String reason;
}
