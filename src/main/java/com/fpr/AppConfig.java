package com.fpr;

import com.fpr.kakao.KakaoApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public KakaoApi kakaoApi() {
        return new KakaoApi();
    }
}
