package com.nhnacademy.gatewayapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {
    private long commentId;
    private String writerId;
    private LocalDateTime createdAt;
    private String content;
}
