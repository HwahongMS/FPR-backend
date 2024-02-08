package com.fpr.user.entity;

import com.fpr.user.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Persistent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserId;

    private String nickname;

    private String email;

    private String createDate;

    public User(UserDTO userDTO) {
        this.nickname = userDTO.getProperties().getNickname();
        this.email = userDTO.getKakaoAccount().getEmail();
        // 문자열을 LocalDateTime으로 파싱
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime parsedDateTime = LocalDateTime.parse(userDTO.getConnectedAt(), formatter);

        // UTC 시간대를 한국 시간대로 변환
        ZonedDateTime gmtZonedDateTime = ZonedDateTime.of(parsedDateTime, ZoneId.of("GMT"));
        ZonedDateTime kstZonedDateTime = gmtZonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
        System.out.println("kstZonedDateTime = " + kstZonedDateTime);
        String localDateString = kstZonedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
        // 변환된 시간을 connectedAt에 저장
        this.createDate = localDateString;
    }

    //prePersist createDate 삭제 - 카카오 Response - 서비스 가입일이 기재되어 있음 (그리니치 천문대 기준)
    // 따라서 한국시간으로 바꾸는 과정을 User 생성자에 넣음

}
