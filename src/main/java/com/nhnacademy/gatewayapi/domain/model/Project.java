package com.nhnacademy.gatewayapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Project {

    private Long projectId;

    private String projectName;

    private LocalDateTime createdAt;

    private String adminId;

    private ProjectStatus projectStatus;
}
