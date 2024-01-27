package com.fpr.financialProduct.DTO;

import lombok.Data;

@Data
public class FinancialProductResponseDTO {
    private String finPrdtCd;
    private String korCoNm;
    private String finPrdtnm;
    private String joinWay;
    private String mtrtInt;
    private String spclCnd;
    private String joinMember;
    private String etcNote;
    private int maxLimit;
    private String dclsStartDay;
    private String finTypeNm;
}
