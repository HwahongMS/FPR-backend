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
        if(existUser.isPresent()){
            String existImgUrl = existUser.get().getImgUrl();
            String existNickName = existUser.get().getNickname();
            if(!existImgUrl.equals(userDTO.getProperties().getProfileImage()) || !existNickName.equals(userDTO.getProperties().getNickname()))
                jpaUserRepository.update(existUser.get(),user);
        }
    }

    @Override
    public void deleteUserInfo(User user) {

    }
}
