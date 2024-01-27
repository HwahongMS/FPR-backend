package com.fpr.financialProduct.entity;

import com.fpr.financialProduct.dto.FinancialProduct;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "financial_product")
public class FinancialProductEntity {
    @Id
    @Column(name = "financial_product_code")
    private String financialProductCode;

    @Column(name = "korean_company_name")
    private String koreanCompanyName;

    @Column(name = "financial_product_name")
    private String financialProductName;

    @Column(name = "join_way")
    private String joinWay;

    @Column(name = "maturity_interest")
    private String maturityInterest;

    @Column(name = "special_condition")
    private String specialCondition;

    @Column(name = "join_member")
    private String joinMember;

    @Column(name = "etc_note")
    private String etcNote;

    @Column(name = "max_limit")
    private int maxLimit;

    @Column(name = "declaration_start_date")
    private String declarationStartDate;

    @Column(name = "financial_type_name")
    private String financialTypeName;

    @OneToMany(mappedBy = "financialProduct", cascade = CascadeType.ALL)
    private List<FinancialProductOptionEntity> options;

    public FinancialProductEntity() {

    }
    public FinancialProductEntity(FinancialProduct financialProduct) {
        this.financialProductCode = financialProduct.getFinancialProductCode();
        this.etcNote = financialProduct.getEtcNote();
        this.financialTypeName = financialProduct.getFinancialTypeName();
        this.financialProductName = financialProduct.getFinancialProductName();
        this.declarationStartDate = financialProduct.getDeclarationStartDate();
        this.joinMember = financialProduct.getJoinMember();
        this.joinWay = financialProduct.getJoinWay();
        this.koreanCompanyName = financialProduct.getKoreanCompanyName();
        this.maturityInterest = financialProduct.getMaturityInterest();
        this.maxLimit = financialProduct.getMaxLimit();
        this.specialCondition = financialProduct.getSpecialCondition();
    }
}
