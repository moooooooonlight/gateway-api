package com.nhnacademy.gatewayapi.config;

import com.nhnacademy.gatewayapi.adapter.UserAdapter;
import com.nhnacademy.gatewayapi.common.fiilter.LoginSessionCheckFilter;
import com.nhnacademy.gatewayapi.common.handler.CustomAuthenticationFailureHandler;
import com.nhnacademy.gatewayapi.common.handler.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    private final RedisTemplate<String, Object> redisTemplate;
    private final UserAdapter userAdapter;

    @Bean
    public SecurityFilterChain securityFIlterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable); // CSRF 보호 기능을 끄기 설정
        http.logout(AbstractHttpConfigurer::disable);


        // 필터 적용
        http.addFilterBefore(new LoginSessionCheckFilter(redisTemplate, userAdapter), UsernamePasswordAuthenticationFilter.class);


        // 인증
        http.formLogin(formLogin ->
                formLogin.loginPage("/login")
                        .usernameParameter("id")
                        .passwordParameter("pwd")
                        .loginProcessingUrl("/login/process")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler(customAuthenticationFailureHandler)
        );



        // 인가
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/login/process").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/404").permitAll()
                        .anyRequest().authenticated()
        );


        // 예외 : exceptionHandler


        return http.build();
    }
}
