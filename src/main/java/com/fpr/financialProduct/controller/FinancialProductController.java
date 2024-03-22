package com.fpr.financialProduct.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpr.financialProduct.dto.FinancialGoalConsultRequest;
import com.fpr.financialProduct.dto.FinancialGoalConsultResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class FinancialProductController {
    private final FinancialProductService financialProductService;

    @GetMapping("/createConsult")
    public ResponseEntity<String>createFinGoalConsult(@RequestParam String userGoal, @RequestParam int goalMoney, @RequestParam int userAge, @RequestParam LocalDate goalStart, @RequestParam LocalDate goalEnd, @RequestParam int monthIncome, @RequestParam int monthConsume, @RequestParam String preferMethod) throws JsonProcessingException {
        FinancialGoalConsultRequest financialGoalConsultRequest = FinancialGoalConsultRequest.builder().
                userGoal(userGoal).goalMoney(goalMoney).goalEnd(goalEnd).goalStart(goalStart).userAge(userAge).monthConsume(monthConsume).monthIncome(monthIncome).preferMethod(preferMethod).build();
        return ResponseEntity.ok(financialProductService.createFinGoalConsult(financialGoalConsultRequest));

    }
    @GetMapping("/candidate")
    public ResponseEntity<String> getFinancialProductCandidate() {
        List<TopFinancialProduct> resultDeposit = financialProductService.getTopFinancialProducts("예금", 24);
        List<TopFinancialProduct> resultSaving = financialProductService.getTopFinancialProducts("적금", 24);
        List<TopFinancialProduct> results = new ArrayList<>();
        results.addAll(resultDeposit);
        results.addAll(resultSaving);
        for(int i = 0; i < results.size();i++) {
            System.out.println("results = " + results.get(i));
        }
        StringBuilder sb = new StringBuilder();
        for(TopFinancialProduct s : results){
            sb.append(s.toString());
        }
        return ResponseEntity.ok(sb.toString());
    }

}
