package com.nhnacademy.gatewayapi.domain.dto;

import com.nhnacademy.gatewayapi.domain.model.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ProjectDTO {
    private Long projectId;
    private String projectName;
    private LocalDateTime createdAt;
    private String adminId;
    private ProjectStatus projectStatus;
}
