package com.fpr.financialProduct.controller;

import com.fpr.financialProduct.service.FinancialProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class FinancialProductController {
    private final FinancialProductService financialProductService;


}
