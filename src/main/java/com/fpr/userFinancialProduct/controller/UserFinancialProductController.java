package com.fpr.userFinancialProduct.controller;

import com.fpr.userFinancialProduct.service.UserFinancialProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Controller
@Slf4j
@RestController
@RequestMapping("/userFinancialProducts")
public class UserFinancialProductController {
    private final UserFinancialProductService userFinancialProductService;
}
