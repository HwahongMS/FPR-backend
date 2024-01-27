package com.fpr.financialProduct.service;



import com.fpr.financialProduct.dto.FinancialProduct;
import com.fpr.financialProduct.dto.FinancialProductOption;
import com.fpr.financialProduct.entity.FinancialProductEntity;
import com.fpr.financialProduct.entity.FinancialProductOptionEntity;
import com.fpr.financialProduct.repository.JpaFinancialProductOptionRepository;
import com.fpr.financialProduct.repository.JpaFinancialProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
@Service
@RequiredArgsConstructor
public class FinancialProductServiceImpl implements FinancialProductService{
    @Autowired
    JpaFinancialProductRepository jpaFinancialProductRepository;
    @Autowired
    JpaFinancialProductOptionRepository jpaFinancialProductOptionRepository;
    @Override
    public void saveFinancial(List<FinancialProduct> financialProductList, List<FinancialProductOption> financialProductOptionList) {
        for (FinancialProduct fp : financialProductList) {
            FinancialProductEntity financialProductEntity = new FinancialProductEntity(fp);
            jpaFinancialProductRepository.save(financialProductEntity);
            for (FinancialProductOption fpo : financialProductOptionList) {
                if (Objects.equals(fpo.getFinancialProductCode(), fp.getFinancialProductCode())) {
                    FinancialProductOptionEntity financialProductOptionEntity = new FinancialProductOptionEntity(financialProductEntity, fpo);
                    jpaFinancialProductOptionRepository.save(financialProductOptionEntity);
                }
            }
        }
    }
    @Override
    public void clearFinancial() {
        jpaFinancialProductOptionRepository.clearFinancialProductOption();
        jpaFinancialProductRepository.clearFinancialProduct();
    }
}