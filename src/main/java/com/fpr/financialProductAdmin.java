package com.fpr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpr.financialProduct.DTO.FinancialProduct;
import com.fpr.financialProduct.DTO.FinancialProductOption;
import com.fpr.financialProduct.DTO.FinancialProductResponse;
import com.fpr.financialProduct.repository.JpaFinancialProductOptionRepository;
import com.fpr.financialProduct.repository.JpaFinancialProductRepository;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class financialProductAdmin {
    @Value("${financial.api_key}")
    private String financialApiKey;

    @Autowired
    JpaFinancialProductRepository jpaFinancialProductRepository;
    @Autowired
    JpaFinancialProductOptionRepository jpaFinancialProductOptionRepository;


//    @Scheduled(cron = "0 0 0 * * *") //매일 0시 0분 0초에 실행예약
    @Scheduled(fixedDelay = 1000000)
    public void updateFinancialProduct () {
        RestTemplate rt = new RestTemplate();
        String url = String.format("https://finlife.fss.or.kr/finlifeapi/depositProductsSearch.json?auth=%s&topFinGrpNo=020000&pageNo=1", financialApiKey);
        ResponseEntity<String> response = rt.getForEntity(
                url,
                String.class
        );
        System.out.println(response);
        String responseBody = response.getBody();
        //TODO Json Parser 사용하여 DTO

        try {
            jpaFinancialProductRepository.clearFinancialProduct();
            ObjectMapper objectMapper = new ObjectMapper();
            FinancialProductResponse financialProductResponse = objectMapper.readValue(responseBody, FinancialProductResponse.class);
            List<FinancialProduct> financialProductList = financialProductResponse.getResult().getBaseList(); //상품 리스트
            List<FinancialProductOption> financialProductOptionList = financialProductResponse.getResult().getOptionList(); //상품 옵션 리스트

            for (FinancialProduct fp : financialProductList) {
                jpaFinancialProductRepository.save(fp);
                for (FinancialProductOption fpo : financialProductOptionList){
                    if (fpo.getFinancialProductCode().equals(fpo.getFinancialProductCode())) {
                        jpaFinancialProductOptionRepository.save(fp, fpo);
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("결과 = " + jpaFinancialProductRepository.findAll());
    }


}
