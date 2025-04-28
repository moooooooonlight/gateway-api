package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.ProjectAdapter;
import com.nhnacademy.gatewayapi.adapter.ProjectMemberAdapter;
import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.model.Member;
import com.nhnacademy.gatewayapi.domain.model.Project;
import com.nhnacademy.gatewayapi.domain.model.ProjectStatus;
import com.nhnacademy.gatewayapi.domain.request.CreateProjectRequest;
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

    @GetMapping
    public String registerProjectPage() {
        return "taskApi/projectRegister";
    }

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

    @PostMapping("/{projectId}/members")
    public ModelAndView registerMember(@PathVariable("projectId") Long projectId,
                                       @RequestParam long memberId) {
        ModelAndView mav = new ModelAndView(String.format("redirect:/home/%d", projectId));

        ResponseDTO responseDTO = projectMemberAdapter.registerMember(projectId, memberId);
        if (responseDTO.getHttpStatus().is2xxSuccessful()) {
            return mav;
        }

        return new ModelAndView("error/404");
    }
}
