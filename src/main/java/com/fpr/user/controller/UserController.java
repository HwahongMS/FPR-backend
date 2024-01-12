package com.fpr.user.controller;

import com.fpr.kakao.KakaoApi;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final KakaoApi kakaoApi;

    @GetMapping("user/login")
    public String loginForm(Model model){
        model.addAttribute("kakaoApiKey", kakaoApi.getKakaoApiKey());
        model.addAttribute("redirectUri", kakaoApi.getKakaoRedirectUri());
        return "login";
    }

    @GetMapping("/login/oauth/kakao")
    @ResponseBody
    public String kakaoCallback(String code) throws ParseException {
        String accessToken = kakaoApi.getKakaoAccessToken(code);
        ResponseEntity<String> userInfo = kakaoApi.getUserInfo(accessToken);
        return "카카오 인증완료 반환값: " + userInfo;
    }

}
