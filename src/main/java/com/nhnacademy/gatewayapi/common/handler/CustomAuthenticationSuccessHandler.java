package com.nhnacademy.gatewayapi.common.handler;

import com.nhnacademy.gatewayapi.domain.model.AcademyUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final RedisTemplate<String, Object> sessionRedisTemplate;


    public CustomAuthenticationSuccessHandler(RedisTemplate<String, Object> sessionRedisTemplate) {
        this.sessionRedisTemplate = sessionRedisTemplate;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String sessionId = UUID.randomUUID().toString();
        Cookie sessionCookie = new Cookie("SESSIONID",sessionId);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setMaxAge(60 * 60);
        sessionCookie.setPath("/");

        response.addCookie(sessionCookie);
        AcademyUser academyUser = (AcademyUser) authentication.getPrincipal();
        sessionRedisTemplate.opsForValue().set(sessionId, academyUser.getUsername());

        String id = request.getParameter("id");

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
