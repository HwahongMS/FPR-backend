package com.fpr.financialProduct.repository;

import com.fpr.financialProduct.dto.TopFinancialProduct;
import com.fpr.financialProduct.entity.FinancialProductEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    public List<TopFinancialProduct> findTopProducts(String financialTypeName, int saveTerm) {
        String hql = "SELECT koreanCompanyName, financialProductName, maturityInterest, joinMember, etcNote, financialTypeName, interestRateTypeName, saveTerm, interestRate " +
                "FROM FinancialProductEntity fpe " +
                "JOIN FinancialProductOptionEntity fpo ON fpe.financialProductCode = fpo.financialProduct.financialProductCode " +
                "WHERE fpe.financialTypeName = :financialTypeName AND fpo.saveTerm <= :saveTerm " +
                "GROUP BY fpe.financialProductCode " +
                "ORDER BY max(interestRate) DESC";
        List<Object[]> resultList = em.createQuery(hql)
                .setParameter("financialTypeName", financialTypeName)
                .setParameter("saveTerm", saveTerm)
                .setMaxResults(3)
                .getResultList();
        List<TopFinancialProduct> result = new ArrayList<>();
        for (Object[] object : resultList) {
            TopFinancialProduct topFinancialProduct = new TopFinancialProduct(object);
            result.add(topFinancialProduct);
        }
        return result;
    }

    public void clearFinancialProduct() {
        em.createQuery("delete from FinancialProductEntity").executeUpdate();
    }
}
