package com.fpr.userFinancialGoal.controller;


import com.fpr.userFinancialGoal.service.UserFinancialGoalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Controller
@Slf4j
@RestController
@RequestMapping("/userFinancialGoals")
public class UserFinancialGoalController {
    private final UserFinancialGoalService userFinancialGoalService;

}
