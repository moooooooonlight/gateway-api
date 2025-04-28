package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.ProjectAdapter;
import com.nhnacademy.gatewayapi.adapter.UserAdapter;
import com.nhnacademy.gatewayapi.domain.model.Project;
import com.nhnacademy.gatewayapi.domain.model.User;
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

    @GetMapping("/myPage")
    public ModelAndView getMyPage(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("layout/mainLayout");

        String userId = (String) request.getSession().getAttribute("userId");
        User user = userAdapter.getUser(userId);
        List<Project> projectList = projectAdapter.getProjectList(userId);

        Map<String, Object> model = mav.getModel();

        model.put("projectList", projectList);
        model.put("user",user);
        model.put("contentTemplate", "content/myPage");
        return mav;
    }
}
