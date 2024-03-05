package com.fpr.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    @JsonProperty("connected_at")
    private String connectedAt;
    private PropertiesDTO properties;
    @JsonProperty("kakao_account")
    private KakaoAccountDTO kakaoAccount;
}
