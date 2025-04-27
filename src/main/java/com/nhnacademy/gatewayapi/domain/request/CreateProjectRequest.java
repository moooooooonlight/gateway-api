package com.nhnacademy.gatewayapi.domain.request;

import com.nhnacademy.gatewayapi.domain.model.ProjectStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class CreateProjectRequest {

    private String projectName;
    private ProjectStatus projectStatus;
    private String adminId;

    public CreateProjectRequest(String projectName, ProjectStatus projectStatus, String adminId) {
        this.projectName = projectName;
        this.projectStatus = projectStatus;
        this.adminId = adminId;
    }
}
