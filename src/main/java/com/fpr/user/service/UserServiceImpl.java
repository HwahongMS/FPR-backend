package com.fpr.user.service;

import com.fpr.user.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final JpaUserRepository jpaUserRepository;

}
