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
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("은행 이름 : "+koreanCompanyName +"\n");
        sb.append("금융 상품 이름 : "+financialProductName+"\n");
        sb.append("만기후 이자율 : "+maturityInterest+"\n");
        sb.append("가입 대상 : "+joinMember+"\n");
        sb.append("비고 : "+etcNote+"\n");
        sb.append("금융 상품 형태 : "+financialTypeName+"\n");
        sb.append("이자 적립 형태 : "+interestRateTypeName+"\n");
        sb.append("저축 기간 : "+saveTerm+"\n");
        sb.append("이자율 : "+interestRate+"\n");


        return sb.toString();
    }
}
