package com.fpr.gpts.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class Message {
        String role;
        String content;
}
