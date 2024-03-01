package com.fpr.financialProduct.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpr.financialProduct.dto.FinancialProduct;
import com.fpr.financialProduct.dto.FinancialProductOption;
import com.fpr.financialProduct.dto.FinancialProductResponse;
import com.fpr.financialProduct.service.FinancialProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Component
public class financialProductScheduler {
    @Value("${financial.api_key}")
    private String financialApiKey;

    @Autowired
    FinancialProductServiceImpl financialProductService;

//    @Scheduled(cron = "0 0 0 * * *") //매일 0시 0분 0초에 실행예약
    @Scheduled(fixedDelay = 1000000)
    public void updateFinancialProduct() {
        String url = "https://finlife.fss.or.kr/finlifeapi/%s.json?auth=%s&topFinGrpNo=020000&pageNo=%d";
        financialProductService.clearFinancial();
        int maxPage = findMaxPage(String.format(url, "depositProductsSearch", financialApiKey, 1));
        for(int i=1; i<=maxPage;i++) {
            saveDatabase(String.format(url, "depositProductsSearch", financialApiKey, i), "예금");
        }
        maxPage = findMaxPage(String.format(url, "savingProductsSearch", financialApiKey, 1));
        for(int i=1; i<=maxPage; i++) {
            saveDatabase(String.format(url, "savingProductsSearch", financialApiKey, i), "적금");
        }
    }
    private void saveDatabase(String url, String financialProductType) {
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.getForEntity(
                url,
                String.class
        );
        String responseBody = response.getBody();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FinancialProductResponse financialProductResponse = objectMapper.readValue(responseBody, FinancialProductResponse.class);
            List<FinancialProduct> financialProductList = financialProductResponse.getResult().getBaseList(); //상품 리스트
            List<FinancialProductOption> financialProductOptionList = financialProductResponse.getResult().getOptionList(); //상품 옵션 리스트

            for (FinancialProduct product : financialProductList) {
                product.setFinancialTypeName(financialProductType);
            }
            financialProductService.saveFinancial(financialProductList, financialProductOptionList);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private int findMaxPage(String url) {
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.getForEntity(
                url,
                String.class
        );
        int maxPage = 0;
        String responseBody = response.getBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FinancialProductResponse financialProductResponse = objectMapper.readValue(responseBody, FinancialProductResponse.class);
            maxPage = financialProductResponse.getResult().getMaxPageNo();;
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("maxPage = " + maxPage);
        return maxPage;
    }
}