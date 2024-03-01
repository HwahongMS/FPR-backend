package com.fpr.financialProduct.entity;

import com.fpr.financialProduct.dto.FinancialProductOption;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "financial_product_option")
public class FinancialProductOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fin_option_key")
    private Long finOptionKey;

    @ManyToOne
    @JoinColumn(name = "financial_product_code", referencedColumnName = "financial_product_code")
    private FinancialProductEntity financialProduct;

    @Column(name = "interest_rate_type_name")
    private String interestRateTypeName;

    @Column(name = "save_term")
    private String saveTerm;

    @Column(name = "interest_rate")
    private double interestRate;

    @Column(name = "interest_rate2")
    private double interestRate2;

    public FinancialProductOptionEntity() {

    }
    public FinancialProductOptionEntity(FinancialProductEntity financialProductEntity, FinancialProductOption financialProductOption) {
        this.interestRateTypeName = financialProductOption.getInterestRateTypeName();
        this.interestRate = financialProductOption.getInterestRate();
        this.interestRate2 = financialProductOption.getInterestRate2();
        this.saveTerm = financialProductOption.getSaveTerm();
        this.financialProduct = financialProductEntity;
    }

}
