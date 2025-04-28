package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.ProjectAdapter;
import com.nhnacademy.gatewayapi.adapter.UserAdapter;
import com.nhnacademy.gatewayapi.domain.model.Project;
import com.nhnacademy.gatewayapi.domain.model.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class MyPageController {
    private final UserAdapter userAdapter;
    private final ProjectAdapter projectAdapter;

    @Operation(
            summary = "myPage 띄워주는 API",
            description = "myPage 띄워주는 수행"
    )
    @GetMapping("/myPage")
    public String getMyPage(HttpServletRequest request,
                            Model model){

        String userId = (String) request.getSession().getAttribute("userId");
        User user = userAdapter.getUser(userId);
        List<Project> projectList = projectAdapter.getProjectList(userId);

        model.addAttribute("projectList", projectList);
        model.addAttribute("user",user);
        model.addAttribute("contentTemplate", "content/myPage");

        return "layout/mainLayout";
    }
}
