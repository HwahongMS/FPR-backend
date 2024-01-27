package com.fpr.financialProduct.repository;

import com.fpr.financialProduct.entity.FinancialProductEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class JpaFinancialProductRepository {
    private final EntityManager em;

    public FinancialProductEntity save(FinancialProductEntity financialProductEntity) {
        em.persist(financialProductEntity);
        return financialProductEntity;
    }

    public Optional<FinancialProductEntity> findById(String financialProductCode) {
        FinancialProductEntity financialProductEntity = em.find(FinancialProductEntity.class, financialProductCode);
        return Optional.ofNullable(financialProductEntity);
    }

    public List<FinancialProductEntity> findAll() {
        List<FinancialProductEntity> financialProducts = em.createQuery("select f.financialProductCode from FinancialProductEntity f", FinancialProductEntity.class)
                .getResultList();
        return financialProducts;

    }

    public void clearFinancialProduct() {
        em.createQuery("delete from FinancialProductEntity").executeUpdate();
    }
}
