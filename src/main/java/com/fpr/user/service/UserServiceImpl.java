package com.fpr.user.service;

import com.fpr.user.dto.UserDTO;
import com.fpr.user.entity.User;
import com.fpr.user.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final JpaUserRepository jpaUserRepository;

    @Override
    public void saveUserInfo(UserDTO userDTO) {
        User user = new User(userDTO);
        Optional<User> existUser = jpaUserRepository.findByEmail(user.getEmail());
        if (existUser.isEmpty()) {
            jpaUserRepository.save(user);
        }
    }

    @Override
    public void deleteUserInfo(User user) {

    }
}
