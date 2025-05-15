package io.github.leducanh123456.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author: hduong25
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuickOfferBody {
    @JsonProperty("customerId")
    private String customerId;

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("orderDate")
    private LocalDateTime orderDate;

    @JsonProperty("productId")
    private String productId;

    @JsonProperty("quickofferId")
    private String quickofferId;

    @JsonProperty("customerName")
    private String customerName;

    @JsonProperty("dateOfBirth")
    private String dateOfBirth;

    @JsonProperty("genderCode")
    private String genderCode;

    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @JsonProperty("typeOfIdCode")
    private String typeOfIdCode;

    @JsonProperty("typeOfIdName")
    private String typeOfIdName;

    @JsonProperty("personalId")
    private String personalId;

    @JsonProperty("issueDate")
    private LocalDate issueDate;

    @JsonProperty("expireDate")
    private String expireDate;

    @JsonProperty("jobCode")
    private String jobCode;

    @JsonProperty("jobName")
    private String jobName;

    @JsonProperty("averageMonthlyIncome")
    private Long averageMonthlyIncome;

    @JsonProperty("incomeProofCode")
    private String incomeProofCode;

    @JsonProperty("incomeProofName")
    private String incomeProofName;

    @JsonProperty("customerMaritalStatusCode")
    private String customerMaritalStatusCode;

    @JsonProperty("customerMaritalStatusName")
    private String customerMaritalStatusName;

    @JsonProperty("numberOfChildren")
    private Long numberOfChildren;

    @JsonProperty("residenceAddress")
    private String residenceAddress;

    @JsonProperty("residenceProvinceCode")
    private String residenceProvinceCode;

    @JsonProperty("residenceProvinceName")
    private String residenceProvinceName;

    @JsonProperty("residenceDistrictCode")
    private String residenceDistrictCode;

    @JsonProperty("residenceDistrictName")
    private String residenceDistrictName;

    @JsonProperty("residenceWardCode")
    private String residenceWardCode;

    @JsonProperty("residenceWardName")
    private String residenceWardName;

    @JsonProperty("currentAddress")
    private String currentAddress;

    @JsonProperty("currentProvinceCode")
    private String currentProvinceCode;

    @JsonProperty("currentProvinceName")
    private String currentProvinceName;

    @JsonProperty("currentDistrictCode")
    private String currentDistrictCode;

    @JsonProperty("currentDistrictName")
    private String currentDistrictName;

    @JsonProperty("currentWardCode")
    private String currentWardCode;

    @JsonProperty("currentWardName")
    private String currentWardName;

    @JsonProperty("loanMoney")
    private Long loanMoney;

    @JsonProperty("tenor")
    private Long tenor;

    @JsonProperty("paymentMethod")
    private String paymentMethod;

    @JsonProperty("assetCategoryCode")
    private String assetCategoryCode;

    @JsonProperty("assetCategoryName")
    private String assetCategoryName;

    @JsonProperty("assetCompanyCode")
    private String assetCompanyCode;

    @JsonProperty("assetCompanyName")
    private String assetCompanyName;

    @JsonProperty("assetModelCode")
    private String assetModelCode;

    @JsonProperty("assetModelName")
    private String assetModelName;

    @JsonProperty("assetYearCode")
    private String assetYearCode;

    @JsonProperty("assetYearName")
    private String assetYearName;

    @JsonProperty("assetInforCode")
    private String assetInforCode;

    @JsonProperty("assetInforName")
    private String assetInforName;

    @JsonProperty("paperIssueDate")
    private LocalDate paperIssueDate;
}
