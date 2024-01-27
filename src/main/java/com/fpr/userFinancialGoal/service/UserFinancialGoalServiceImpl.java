package com.fpr.userFinancialGoal.service;

import com.fpr.userFinancialGoal.repository.JpaUserFinancialGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFinancialGoalServiceImpl implements UserFinancialGoalService{
    private final JpaUserFinancialGoalRepository jpaUserFinancialGoalRepository;
}
