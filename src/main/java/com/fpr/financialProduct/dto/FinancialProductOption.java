package com.fpr.financialProduct.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinancialProductOption {
    @JsonProperty("fin_prdt_cd")
    private String financialProductCode;

    @JsonProperty("intr_rate_type_nm")
    private String interestRateTypeName;

    @JsonProperty("save_trm")
    private String saveTerm;

    @JsonProperty("intr_rate")
    private double interestRate;

    @JsonProperty("intr_rate2")
    private double interestRate2;

}
