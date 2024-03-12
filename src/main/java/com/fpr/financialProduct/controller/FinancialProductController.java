package com.fpr.financialProduct.controller;

import com.fpr.financialProduct.dto.TopFinancialProduct;
import com.fpr.financialProduct.service.FinancialProductService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class FinancialProductController {
    private final FinancialProductService financialProductService;

    @GetMapping("/candidate")
    public void getFinancialProductCandidate() {
        List<TopFinancialProduct> resultDeposit = financialProductService.getTopFinancialProducts("예금", 24);
        List<TopFinancialProduct> resultSaving = financialProductService.getTopFinancialProducts("적금", 24);
        List<TopFinancialProduct> results = new ArrayList<>();
        results.addAll(resultDeposit);
        results.addAll(resultSaving);
        for(int i = 0; i < results.size();i++) {
            System.out.println("results = " + results.get(i));
        }
    }

}
