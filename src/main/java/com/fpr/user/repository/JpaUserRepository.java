package com.fpr.user.repository;

import com.fpr.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Data
@Repository
@Slf4j
@Transactional
public class JpaUserRepository {
    private final EntityManager em;

    public User save(User user) {
        em.persist(user);
        return user;
    }

    public User update(User existUser,User user){
        existUser.setImgUrl(user.getImgUrl());
        existUser.setThumbnailUrl(user.getThumbnailUrl());
        existUser.setNickname(user.getNickname());
        return existUser;
    }


    public Optional<User> findByEmail(String email) {
        List<User> existUsers = em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
        return existUsers.stream().findAny();
    }

    public void clearUser() {
        em.createQuery("delete from User").executeUpdate();
    }
}
