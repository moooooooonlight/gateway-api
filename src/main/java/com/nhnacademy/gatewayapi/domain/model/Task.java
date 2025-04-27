package com.nhnacademy.gatewayapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Task {

    private Long taskId;

    private Long projectId;

    private Long mileStoneId;

    private String taskName;

    private String creatorId;

    private String managerId;

    private LocalDateTime createdAt;
}
