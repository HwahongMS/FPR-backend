package com.fpr.user.service;

import com.fpr.user.dto.UserDTO;
import com.fpr.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public void saveUserInfo(UserDTO userDTO);

    public void deleteUserInfo(User user);
}
