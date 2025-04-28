package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.ProjectAdapter;
import com.nhnacademy.gatewayapi.adapter.ProjectMemberAdapter;
import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.model.Member;
import com.nhnacademy.gatewayapi.domain.model.Project;
import com.nhnacademy.gatewayapi.domain.model.ProjectStatus;
import com.nhnacademy.gatewayapi.domain.request.CreateProjectRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectAdapter projectAdapter;
    private final ProjectMemberAdapter projectMemberAdapter;


    @Operation(
            summary = "프로젝트 등록 페이지 API",
            description = "프로젝트 등록 페이지 띄워주는 동작 수행"
    )
    @GetMapping
    public String registerProjectPage() {
        return "taskApi/projectRegister";
    }


    @Operation(
            summary = "프로젝트 등록 API",
            description = "프로젝트 등록 수행"
    )
    @PostMapping
    public String registerProject(@RequestParam String projectName,
                                  @RequestParam String projectStatus,
                                  HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");

        CreateProjectRequest createProjectRequest = new CreateProjectRequest(projectName, ProjectStatus.valueOf(projectStatus), userId);
        ResponseDTO response = projectAdapter.registerProject(createProjectRequest);

        if (response.getHttpStatus().is2xxSuccessful()) {
            return "redirect:/";
        }
        log.debug("fail");
        return "error/404";
    }

    @Operation(
            summary = "프로젝트에 member 등록 API",
            description = "프로젝트에 member 등록해주는 동작 수행"
    )
    @PostMapping("/{projectId}/members")
    public String registerMember(@PathVariable("projectId") Long projectId,
                                       @RequestParam String memberId) {

        ResponseDTO responseDTO = projectMemberAdapter.registerMember(projectId, memberId);
        if (responseDTO.getHttpStatus().is2xxSuccessful()) {
            return String.format("redirect:/home/%d", projectId);
        }

        return "error/404";
    }
}
