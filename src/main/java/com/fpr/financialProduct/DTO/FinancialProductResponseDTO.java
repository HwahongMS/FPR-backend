package com.fpr.financialProduct.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class FinancialProductResponseDTO {
    private String finPrdtCd;
    private String korCoNm;
    private String finPrdtMm;
    private String joinWay;
    private String mtrtInt;
    private String spclCnd;
    private String joinMember;
    private String etxNote;
    private int maxLimit;
    private Date dxlsStartDay;
    private String finTypeNm;
}
