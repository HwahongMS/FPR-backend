package com.fpr.userFinancialProduct.controller;

import com.fpr.userFinancialProduct.service.UserFinancialProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//RestController와 Controller의 차이, 그리고 어떤 것을 사용할지 토의
@RequiredArgsConstructor
@Controller
@Slf4j
@RestController
@RequestMapping("/userFinancialProducts")
public class UserFinancialProductController {
    private final UserFinancialProductService userFinancialProductService;
}
