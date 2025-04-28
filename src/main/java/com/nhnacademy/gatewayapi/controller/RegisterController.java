package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.RegisterAdapter;
import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.request.CreateUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/register")
public class RegisterController {
    private final RegisterAdapter accountAdapter;
    private final PasswordEncoder passwordEncoder;

    @Operation(
            summary = "회원가입 페이지 API",
            description = "회원가입 페이지 띄워주는 동작 수행"
    )
    @GetMapping
    public String signup(){
        return "login/register";
    }


    @Operation(
            summary = "회원가입 API",
            description = "회원가입 동작 수행"
    )
    @PostMapping
    public String doSignup(@RequestParam String id,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam String name){

        password = passwordEncoder.encode(password);
        CreateUserRequest createUserRequest = new CreateUserRequest(id, password, email, name);
        ResponseEntity<ResponseDTO> response = accountAdapter.signupUser(createUserRequest);

        if(response.getBody().getHttpStatus().is2xxSuccessful()){
            return "login/login";
        }
        return "error/404";
    }
}
