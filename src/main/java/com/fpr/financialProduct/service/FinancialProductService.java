package com.fpr.financialProduct.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpr.financialProduct.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FinancialProductService {
    public void saveFinancial(List<FinancialProduct> financialProductList, List<FinancialProductOption> financialProductOptionList);

    public void clearFinancial();

    List<TopFinancialProduct> getTopFinancialProducts(String productType, int period);
    public String createFinGoalConsult(FinancialGoalConsultRequest financialGoalConsultRequest) throws JsonProcessingException;
}
