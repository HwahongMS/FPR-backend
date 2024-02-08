package com.fpr.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpr.kakao.KakaoApi;
import com.fpr.user.dto.UserDTO;
import com.fpr.user.entity.User;
import com.fpr.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {

    private final KakaoApi kakaoApi;
    private final UserService userService;

    @GetMapping("/users/login")
    public String loginForm(Model model) {
        String loginUrl = "https://kauth.kakao.com/oauth/authorize?client_id="+ kakaoApi.getKakaoApiKey()
                +"&redirect_uri="+kakaoApi.getKakaoRedirectUri()+"&response_type=code";
        model.addAttribute("loginUrl", loginUrl);
        return "login"; // login.html 뷰를 반환
    }

    @GetMapping("login/oauth/kakao")
    @ResponseBody
    public String kakaoCallback(String code) throws ParseException {
        String accessToken = kakaoApi.getKakaoAccessToken(code);
        ResponseEntity<String> userInfo = kakaoApi.getUserInfo(accessToken);
        String userInfoBody = userInfo.getBody();
// parsing 기능 service 계층으로 옮기기
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserDTO userDTO = objectMapper.readValue(userInfoBody, UserDTO.class);
            userService.saveUserInfo(userDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "저장완료";
    }
}
