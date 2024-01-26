package com.fpr.financialProduct.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "financial_product_option")
public class FinancialProductOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "financial_product_code", referencedColumnName = "financial_product_code")
    private FinancialProductEntity financialProduct;

    @Column(name = "interest_rate_type_name")
    private String interestRateTypeName;

    @Column(name = "save_term")
    private String saveTerm;

    @Column(name = "interest_rate")
    private int interestRate;

    @Column(name = "interest_rate2")
    private int interestRate2;
}
