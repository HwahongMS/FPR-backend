package com.fpr.user.repository;

import com.fpr.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

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

    public Optional<User> findById(String userId) {
        User user = em.find(User.class, userId);
        return Optional.ofNullable(user);
    }

    public void clearUser() {
        em.createQuery("delete from User").executeUpdate();
    }
}
