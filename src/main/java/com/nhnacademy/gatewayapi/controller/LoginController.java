package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.RegisterAdapter;
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


    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal UserDetails userDetails,
                             @AuthenticationPrincipal OAuth2User oAuth2User ){


        ModelAndView home = new ModelAndView("home");
        return home;
    }
}
