package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.ProjectAdapter;
import com.nhnacademy.gatewayapi.adapter.RegisterAdapter;
import com.nhnacademy.gatewayapi.domain.dto.ProjectListDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final RegisterAdapter accountAdapter;
    private final ProjectAdapter projectAdapter;


    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal UserDetails userDetails,
                             @AuthenticationPrincipal OAuth2User oAuth2User,
                             HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        // 프로젝트 리스트값 요청하기
        ProjectListDTO projectListDTO = projectAdapter.getProjectList(userId);


        ModelAndView home = new ModelAndView("home");
        home.getModel().put("projectList", projectListDTO.getProjects());
        return home;
    }
}

