package com.fpr.financialProduct.service;

import com.fpr.financialProduct.dto.FinancialProduct;
import com.fpr.financialProduct.dto.FinancialProductOption;
import com.fpr.financialProduct.dto.TopFinancialProduct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FinancialProductService {
    public void saveFinancial(List<FinancialProduct> financialProductList, List<FinancialProductOption> financialProductOptionList);

    public void clearFinancial();

    List<TopFinancialProduct> getTopFinancialProducts(String productType, int period);
}
