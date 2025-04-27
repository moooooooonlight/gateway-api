package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.UserAdapter;
import com.nhnacademy.gatewayapi.domain.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class MyPageController {
    private final UserAdapter userAdapter;

    @GetMapping("/myPage")
    public String getMyPage(Model model, HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userId");

        User user = userAdapter.getUser(userId);
        model.addAttribute("user",user);
        return "myPage";
    }

    @PostMapping("/myPage/status")
    public String updateUserCUD(@RequestParam String userCud, HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userId");
        userAdapter.updateUserCUD(userId, userCud);

        return "redirect: /myPage";
    }
}
