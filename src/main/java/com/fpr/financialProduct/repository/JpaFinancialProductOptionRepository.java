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

    //중복확인 메서드 code+saveTerm 은 키이므로 유일해야함
    public Optional<FinancialProductOptionEntity> findByCode_SaveTerm(String financialProductCode, String saveTerm) {

        String jpql = "select f from financialProductOption f where f.financialProduct.financialProductCode = :financialProductCode and f.saveTerm = :saveTerm";
        TypedQuery<FinancialProductOptionEntity> query = em.createQuery(jpql, FinancialProductOptionEntity.class);
        query.setParameter("financialProductCode", financialProductCode);
        query.setParameter("saveTerm", saveTerm);
        FinancialProductOptionEntity financialProductOptionEntity = query.getSingleResult();

        return Optional.ofNullable(financialProductOptionEntity);
    }
}
