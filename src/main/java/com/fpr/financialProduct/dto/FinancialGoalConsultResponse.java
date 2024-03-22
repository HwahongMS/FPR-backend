package com.fpr.financialProduct.dto;

import lombok.Data;

@Data
public class FinancialGoalConsultResponse {
    private String userGoal;
    private int goalMoney;
    private int userAge;
    private int goalPeriod;
    private int monthIncome;
    private int monthConsume;
    private String preferMethod;
}
