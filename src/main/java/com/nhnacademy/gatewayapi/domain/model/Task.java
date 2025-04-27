package com.nhnacademy.gatewayapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Task {
    private Long taskId;
    private Long projectId;
    private Long mileStoneId;
    private String taskName;
    private String creatorId;
    private String managerId;
    private LocalDateTime createdAt;

    public Task(Long projectId, Long mileStoneId, String taskName, String creatorId, String managerId, LocalDateTime createdAt) {
        this.projectId = projectId;
        this.mileStoneId = mileStoneId;
        this.taskName = taskName;
        this.creatorId = creatorId;
        this.managerId = managerId;
        this.createdAt = createdAt;
    }
}
