package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.*;
import com.nhnacademy.gatewayapi.domain.dto.ProjectDTO;
import com.nhnacademy.gatewayapi.domain.dto.ProjectListDTO;
import com.nhnacademy.gatewayapi.domain.dto.TaskListDTO;
import com.nhnacademy.gatewayapi.domain.model.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {
    private final UserAdapter userAdapter;
    private final ProjectAdapter projectAdapter;
    private final TaskAdapter taskAdapter;
    private final CommentAdapter commentAdapter;
    private final TagAdapter tagAdapter;
    private final MileStoneAdapter mileStoneAdapter;
    private final ProjectMemberAdapter projectMemberAdapter;

    @Operation(
            summary = "기본 페이지 API",
            description = "로그인 성공 시 기본 페이지"
    )
    @GetMapping({"/"})
    public String home(@AuthenticationPrincipal UserDetails userDetails,
                       @AuthenticationPrincipal OAuth2User oAuth2User,
                       HttpServletRequest request,
                       Model model) {

        String userId = (String) request.getSession().getAttribute("userId");
        // 프로젝트 리스트값 요청하기
        List<Project> projectList = projectAdapter.getProjectList(userId);
        model.addAttribute("projectList", projectList);


        return "layout/mainLayout";
    }


    @Operation(
            summary = "프로젝트 페이지 API",
            description = "프로젝트 선택 시 프로젝트 정보 띄워줌"
    )
    @GetMapping("/home/{projectId}")
    public String projectDetail(@PathVariable long projectId,
                                HttpServletRequest request,
                                Model model) {

        String userId = (String) request.getSession().getAttribute("userId");
        List<Project> projectList = projectAdapter.getProjectList(userId);
        Project project = projectAdapter.getProject(userId, projectId);
        List<Task> taskList = taskAdapter.getTaskList(projectId);
        List<Tag> projectTagList = tagAdapter.getProjectTagList(projectId);
        List<MileStone> projectMilestoneList = mileStoneAdapter.getProjectMilestoneList(projectId);
        List<User> users = userAdapter.getUsers();
        List<User> projectUserList = getProjectUserList(projectId);


        model.addAttribute("projectList", projectList);
        model.addAttribute("contentTemplate", "content/projectDetails");
        model.addAttribute("project", project);
        model.addAttribute("projectId", projectId);
        model.addAttribute("taskList", taskList);
        model.addAttribute("tagList", projectTagList);
        model.addAttribute("milestoneList", projectMilestoneList);
        model.addAttribute("allMemberList", users);
        model.addAttribute("projectMemberList", projectUserList);
        model.addAttribute("projectOwnerId", userId);


        return "layout/mainLayout";
    }


    @Operation(
            summary = "Task 페이지 API",
            description = "Task 선택 시 Task 정보 띄워줌"
    )
    @GetMapping("/home/{projectId}/{taskId}")
    public String taskDetail(@PathVariable long projectId,
                             @PathVariable long taskId,
                             HttpServletRequest request,
                             Model model) {

        String userId = (String) request.getSession().getAttribute("userId");
        List<Project> projectList = projectAdapter.getProjectList(userId);
        List<Task> taskList = taskAdapter.getTaskList(projectId);
        Task task = taskAdapter.getTask(projectId, taskId);
        Project project = projectAdapter.getProject(userId, projectId);
        List<Comment> commentList = commentAdapter.getCommentList(projectId, taskId);
        List<Tag> taskTagList = tagAdapter.getTaskTagList(projectId, taskId);
        List<Tag> projectTagList = tagAdapter.getProjectTagList(projectId);
        List<MileStone> projectMilestoneList = mileStoneAdapter.getProjectMilestoneList(projectId);
        MileStone mileStone = mileStoneAdapter.getTaskMileStone(projectId, taskId);

        model.addAttribute("projectList", projectList);
        model.addAttribute("contentTemplate", "content/taskDetails");
        model.addAttribute("projectId", projectId);
        model.addAttribute("project", project);
        model.addAttribute("task", task);
        model.addAttribute("taskList", taskList);
        model.addAttribute("commentList", commentList);
        model.addAttribute("projectTagList", projectTagList);
        model.addAttribute("taskTagList", taskTagList);
        model.addAttribute("milestoneList", projectMilestoneList);
        model.addAttribute("projectOwnerId", userId);


        return "layout/mainLayout";
    }




    private List<User> getProjectUserList(long projectId) {
        List<String> memberIdList = projectMemberAdapter.getMemberIdList(projectId);
        List<User> userList = new ArrayList<>();
        for(String memberId : memberIdList){
            userList.add(userAdapter.getUser(memberId));
        }

        return userList;
    }
}

