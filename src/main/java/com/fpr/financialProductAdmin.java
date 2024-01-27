package com.fpr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpr.financialProduct.DTO.FinancialProductResponseDTO;
import com.fpr.financialProduct.FinancialProductResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class financialProductAdmin {
    @Value("${financial.api_key}")
    private String financialApiKey;

    //    @Scheduled(cron = "0 0 0 * * *") //매일 0시 0분 0초에 실행예약
    @Scheduled(fixedDelay = 1000000)
    public void updateFinancialProduct() throws IOException {
        RestTemplate rt = new RestTemplate();

        String url = String.format("https://finlife.fss.or.kr/finlifeapi/depositProductsSearch.json?auth=%s&topFinGrpNo=020000&pageNo=1", financialApiKey);
        ResponseEntity<String> response = rt.getForEntity(
                url,
                String.class
        );
        String responseBody = response.getBody();
        //TODO Json Parser 사용하여 DTO
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //정의되지 않은 json 필드는 무시하도록 설정
        try{
            FinancialProductResponse financialProductResponse = mapper.readValue(responseBody, FinancialProductResponse.class);
            System.out.println(financialProductResponse.getResult());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}