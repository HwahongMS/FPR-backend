package com.fpr.financialProduct.service;

import com.fpr.financialProduct.repository.JpaFinancialProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinancialProductOptionServiceImpl implements FinancialProductOptionService{
    private final JpaFinancialProductOptionRepository jpaFinancialProductOptionRepository;

}
