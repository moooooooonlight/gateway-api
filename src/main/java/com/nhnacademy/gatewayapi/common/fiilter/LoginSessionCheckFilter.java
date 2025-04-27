package com.nhnacademy.gatewayapi.common.fiilter;

import com.nhnacademy.gatewayapi.adapter.UserAdapter;
import com.nhnacademy.gatewayapi.domain.model.AcademyUser;
import com.nhnacademy.gatewayapi.domain.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class LoginSessionCheckFilter extends OncePerRequestFilter {
    private RedisTemplate<String, Object> redisTemplate;
    private UserAdapter userAdapter;

    public LoginSessionCheckFilter(RedisTemplate<String, Object> redisTemplate, UserAdapter userAdapter) {
        this.redisTemplate = redisTemplate;
        this.userAdapter = userAdapter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String sessionId = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if (cookie.getName().equals("SESSIONID")) {
                sessionId = cookie.getValue();
            }
        }

        if(!Objects.isNull(sessionId)){
            Object o = redisTemplate.opsForValue().get(sessionId);
            if(Objects.nonNull(o)){
                String id = (String) o;
                User user = userAdapter.getUser(id);
                AcademyUser academyUser = new AcademyUser(user);


                Authentication auth = new PreAuthenticatedAuthenticationToken(academyUser, null, academyUser.getAuthorities());
                auth.setAuthenticated(true);
                // context 에 사용자 인증 정보 저장해줌
                log.debug("login 유지 설정");
                request.getSession().setAttribute("userId",id);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request,response);
    }
}
