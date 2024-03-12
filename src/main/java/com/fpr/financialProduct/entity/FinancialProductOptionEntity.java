package com.fpr.financialProduct.entity;

import com.fpr.financialProduct.dto.FinancialProductOption;
import jakarta.persistence.*;
import lombok.Data;

import static java.lang.Math.pow;

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
    private int saveTerm;

    @Column(name = "interest_rate")
    private double interestRate;

    @Column(name = "interest_rate2")
    private double interestRate2;

    @Column(name = "rsrv_type_nm")
    private String reserveTypeName;

    @Column(name = "total_interest_rate")
    private double totalInterestRate;

    public FinancialProductOptionEntity() {

    }
    public FinancialProductOptionEntity(FinancialProductEntity financialProductEntity, FinancialProductOption financialProductOption) {
        this.interestRateTypeName = financialProductOption.getInterestRateTypeName();
        this.interestRate = financialProductOption.getInterestRate();
        this.interestRate2 = financialProductOption.getInterestRate2();
        this.saveTerm = financialProductOption.getSaveTerm();
        this.financialProduct = financialProductEntity;
        this.reserveTypeName = financialProductOption.getReserveTypeName();
        if (financialProductEntity.getFinancialTypeName().equals("예금")) {
            if (interestRateTypeName.equals("단리")) {
                this.totalInterestRate = (saveTerm) * (interestRate/1200) * 100;
            } else {
                this.totalInterestRate = (pow((1+interestRate/1200), saveTerm)-1) * 100;
            }
        } else if (financialProductEntity.getFinancialTypeName().equals("적금")) {
            if (interestRateTypeName.equals("단리")) {
                this.totalInterestRate = (interestRate/100) * (saveTerm +1)/24 * 100;
            } else {
                int monthlySaving = 100000;
                double futurePrice = 0;
                for (int i = 0; i < saveTerm; i++) {
                    futurePrice = (futurePrice + monthlySaving) * (1 + interestRate/1200);
                }
                this.totalInterestRate = (futurePrice-(monthlySaving*saveTerm))/(monthlySaving*saveTerm) * 100;
            }
        }
    }

}
