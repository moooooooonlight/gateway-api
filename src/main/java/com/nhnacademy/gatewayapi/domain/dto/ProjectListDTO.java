package com.nhnacademy.gatewayapi.domain.dto;

import com.nhnacademy.gatewayapi.domain.model.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProjectListDTO {
    private List<Project> projects;
}
