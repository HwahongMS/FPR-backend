package com.fpr.userFinancialProduct.service;

import com.fpr.userFinancialProduct.repository.JpaUserFinancialProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserFinancialProductServiceImpl implements UserFinancialProductService{
    private final JpaUserFinancialProductRepository jpaUserFinancialProductRepository;
}
