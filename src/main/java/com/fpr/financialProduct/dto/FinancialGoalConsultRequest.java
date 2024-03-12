package com.fpr.financialProduct.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FinancialGoalConsultRequest {
    private String userGoal;
    private int goalMoney;
    private int userAge;
    private LocalDate goalStart;
    private LocalDate goalEnd;
    private int monthIncome;
    private int monthConsume;
    private String preferMethod;
}
