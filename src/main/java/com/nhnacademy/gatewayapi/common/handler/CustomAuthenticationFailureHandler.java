package com.nhnacademy.gatewayapi.common.handler;

import com.nhnacademy.gatewayapi.service.LoginFailCountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final LoginFailCountService loginFailCountService;

    private final RedisTemplate<String, Object> sessionRedisTemplate;
    private String HASH_NAME = "BLOCK:";


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String id = request.getParameter("id");
        int cnt = loginFailCountService.increase(id);

        if(loginFailCountService.check5count(id)){
            log.info("5번 이상 로그인 실패");
            // 따로 로직 처리 필요할 듯!
            response.sendRedirect("/block");
        }

        response.sendRedirect("/login");
    }
}
