package com.fpr.financialProduct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TopFinancialProduct {
    private String koreanCompanyName;
    private String financialProductName;
    private String maturityInterest;
    private String joinMember;
    private String etcNote;
    private String financialTypeName;
    private String interestRateTypeName;
    private String saveTerm;
    private double interestRate;
    public TopFinancialProduct(Object[] object) {
        this.koreanCompanyName = (String) object[0];
        this.financialProductName = (String) object[1];
        this.maturityInterest = (String) object[2];
        this.joinMember = (String) object[3];
        this.etcNote = (String) object[4];
        this.financialTypeName = (String) object[5];
        this.interestRateTypeName = (String) object[6];
        this.saveTerm = (String) object[7];
        this.interestRate = (double) object[8];
    }
}
