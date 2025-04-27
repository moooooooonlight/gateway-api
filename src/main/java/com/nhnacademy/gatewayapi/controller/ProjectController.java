package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.ProjectAdapter;
import com.nhnacademy.gatewayapi.domain.dto.ProjectListDTO;
import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.model.Member;
import com.nhnacademy.gatewayapi.domain.model.Project;
import com.nhnacademy.gatewayapi.domain.model.ProjectStatus;
import com.nhnacademy.gatewayapi.domain.request.CreateProjectRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectAdapter projectAdapter;




    @GetMapping
    public String registerProjectPage(){
        return "projectRegister";
    }

    @PostMapping
    public String registerProject(@RequestParam String projectName,
                                  @RequestParam String projectStatus,
                                  HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");

        CreateProjectRequest createProjectRequest = new CreateProjectRequest(projectName, ProjectStatus.valueOf(projectStatus),userId);
        ResponseDTO response = projectAdapter.registerProject(createProjectRequest);

        if(response.getHttpStatus().is2xxSuccessful()){
            return "redirect:/home";
        }
        log.debug("fail");
        return "home";
    }


    @GetMapping("/{projectId}")
    public String updateProjectPage(@PathVariable Long projectId,
                                    HttpServletRequest request,
                                    Model model) {
        String userId = request.getSession().getAttribute("userId").toString();
        Project project = projectAdapter.getProject(userId, projectId);
        model.addAttribute("project", project);

        return "updateProject";
    }

    @PutMapping("/{projectId}")
    public String updateProject(@PathVariable("projectId") Long projectId,
                                HttpServletRequest request) {
        String userId = request.getSession().getAttribute("userId").toString();
        ResponseEntity<ResponseDTO> response = projectAdapter.updateProject(userId, projectId);
        if (response.getBody().getHttpStatus().is2xxSuccessful()) {
            return "redirect:/projects";
        }
        return "updateProject";
    }

    @DeleteMapping("/{projectId}")
    public String deleteProject(@PathVariable("projectId") Long projectId,
                                HttpServletRequest request) {
        String userId = request.getSession().getAttribute("userId").toString();
        ResponseEntity<ResponseDTO> response = projectAdapter.deleteProject(userId, projectId);
        if (response.getBody().getHttpStatus().is2xxSuccessful()) {
            return "redirect:/projects";
        }
        return "projects";
    }

    @GetMapping("/{projectId}/members")
    public String registerMemberPage(@PathVariable("projectId") Long projectId,
                                     HttpServletRequest request,
                                     Model model) {
        String userId = request.getSession().getAttribute("userId").toString();
        Project project = projectAdapter.getProject(userId, projectId);
        model.addAttribute("project", project);

        return "registerMember";
    }

    @PostMapping("/{projectId}/members")
    public String registerMember(@PathVariable("projectId") Long projectId,
                                 @RequestParam Member member) {


        // project에 member 등록
        return "";
    }

    @GetMapping("/users/{userId}")
    public String getMyProjects(@PathVariable("userId") String userId) {
        ProjectListDTO response = projectAdapter.getProjectList(userId);
        if (response.getProjects() != null) {
            return "redirect:/projects";
        }
        return "projects";
    }
}
