package com.nhnacademy.gatewayapi.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class LogoutController {
    private final RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        String sessionId = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("SESSIONID")){
                sessionId = cookie.getValue();
                break;
            }
        }

        if(Objects.isNull(sessionId)){
            throw new RuntimeException("사용자 쿠키값을 찾지 못했습니다");
        }

        Cookie cookie = new Cookie("SESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        redisTemplate.delete(sessionId);
        SecurityContextHolder.clearContext();
        response.sendRedirect("/");
    }

}
