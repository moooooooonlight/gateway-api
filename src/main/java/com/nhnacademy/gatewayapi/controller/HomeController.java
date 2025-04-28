package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.*;
import com.nhnacademy.gatewayapi.domain.dto.ProjectDTO;
import com.nhnacademy.gatewayapi.domain.dto.ProjectListDTO;
import com.nhnacademy.gatewayapi.domain.dto.TaskListDTO;
import com.nhnacademy.gatewayapi.domain.model.*;
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


    @GetMapping({"/"})
    public ModelAndView home(@AuthenticationPrincipal UserDetails userDetails,
                             @AuthenticationPrincipal OAuth2User oAuth2User,
                             HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        // 프로젝트 리스트값 요청하기
        List<Project> projectList = projectAdapter.getProjectList(userId);


        ModelAndView home = new ModelAndView("layout/mainLayout");
        home.getModel().put("projectList", projectList);
        return home;
    }


    @GetMapping("/home/{projectId}")
    public ModelAndView projectDetail(@PathVariable long projectId,
                                HttpServletRequest request ) {
        ModelAndView mav = new ModelAndView("layout/mainLayout");

        String userId = (String) request.getSession().getAttribute("userId");
        List<Project> projectList = projectAdapter.getProjectList(userId);
        Project project = projectAdapter.getProject(userId, projectId);
        List<Task> taskList = taskAdapter.getTaskList(projectId);
        List<Tag> projectTagList = tagAdapter.getProjectTagList(projectId);
        List<MileStone> projectMilestoneList = mileStoneAdapter.getProjectMilestoneList(projectId);
        List<User> users = userAdapter.getUsers();
        List<User> projectUserList = getProjectUserList(projectId);


        Map<String, Object> model = mav.getModel();
        model.put("projectList", projectList);
        model.put("contentTemplate", "content/projectDetails");
        model.put("project", project);
        model.put("projectId", projectId);
        model.put("taskList", taskList);
        model.put("tagList", projectTagList);
        model.put("milestoneList", projectMilestoneList);
        model.put("allMemberList", users);
        model.put("projectMemberList", projectUserList);
        model.put("projectOwnerId", userId);


        return mav;
    }

    @GetMapping("/home/{projectId}/{taskId}")
    public ModelAndView taskDetail(@PathVariable long projectId,
                                   @PathVariable long taskId,
                                   HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("layout/mainLayout");

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

        Map<String, Object> model = mav.getModel();
        model.put("projectList", projectList);
        model.put("contentTemplate", "content/taskDetails");
        model.put("projectId", projectId);
        model.put("project", project);
        model.put("task", task);
        model.put("taskList", taskList);
        model.put("commentList", commentList);
        log.debug("tagListSize={}",taskTagList.size());
        model.put("projectTagList", projectTagList);
        model.put("taskTagList", taskTagList);
        model.put("milestoneList", projectMilestoneList);
        model.put("projectOwnerId", userId);


        return mav;
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

