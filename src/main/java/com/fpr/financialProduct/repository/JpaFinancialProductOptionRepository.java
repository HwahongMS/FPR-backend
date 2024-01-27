package com.fpr.financialProduct.repository;

import com.fpr.financialProduct.entity.FinancialProductOptionEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Data
@Repository
@Slf4j
@Transactional
public class JpaFinancialProductOptionRepository {
    private final EntityManager em;

    public FinancialProductOptionEntity save(FinancialProductOptionEntity financialProductOptionEntity) {
        //financialProductEntity와 financialProductOptionEntity의 관계 설정

        em.persist(financialProductOptionEntity);

        return financialProductOptionEntity;
    }

    public void clearFinancialProductOption() {
        em.createQuery("delete from FinancialProductOptionEntity").executeUpdate();
    }
}
