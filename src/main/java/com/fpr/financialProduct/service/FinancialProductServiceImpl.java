package com.fpr.financialProduct.service;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpr.financialProduct.dto.*;
import com.fpr.financialProduct.entity.FinancialProductEntity;
import com.fpr.financialProduct.entity.FinancialProductOptionEntity;
import com.fpr.financialProduct.repository.JpaFinancialProductOptionRepository;
import com.fpr.financialProduct.repository.JpaFinancialProductRepository;
import com.fpr.gpts.service.GptService;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Service
@RequiredArgsConstructor
public class FinancialProductServiceImpl implements FinancialProductService{

    private final GptService gptService;
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

    @Override
    public List<TopFinancialProduct> getTopFinancialProducts(String productType, int period) {
        List<TopFinancialProduct> products = jpaFinancialProductRepository.findTopProducts(productType, period);
        return products;
    }

    @Override
    public String createFinGoalConsult(FinancialGoalConsultRequest financialGoalConsultRequest) throws JsonProcessingException {
        LocalDate startDate = financialGoalConsultRequest.getGoalStart();
        LocalDate endDate = financialGoalConsultRequest.getGoalEnd();
        Period period = Period.between(startDate.withDayOfMonth(1), endDate.withDayOfMonth(1));
        int months = period.getYears() * 12 + period.getMonths();
        if(endDate.getDayOfMonth() - startDate.getDayOfMonth()<0) months--;
        System.out.println(months);
        List<TopFinancialProduct> resultDeposit = getTopFinancialProducts("예금", months);
        List<TopFinancialProduct> resultSaving = getTopFinancialProducts("적금", months);
        String body = gptService.createConsult(financialGoalConsultRequest,resultDeposit,resultSaving);

        return body;
    }
}