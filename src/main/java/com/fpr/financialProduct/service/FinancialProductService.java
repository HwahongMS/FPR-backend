package com.fpr.financialProduct.service;

import com.fpr.financialProduct.DTO.FinancialProduct;
import com.fpr.financialProduct.DTO.FinancialProductOption;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FinancialProductService {
    public void saveFinancial(List<FinancialProduct> financialProductList, List<FinancialProductOption> financialProductOptionList);

    public void clearFinancial();
    }
