package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.RegisterAdapter;
import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.request.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class RegisterController {
    private final RegisterAdapter accountAdapter;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/register")
    public String signup(){
        return "register";
    }


    @PostMapping("/register")
    public String doSignup(@RequestParam String id,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam String name){

        password = passwordEncoder.encode(password);
        CreateUserRequest createUserRequest = new CreateUserRequest(id, password, email, name);
        ResponseEntity<ResponseDTO> response = accountAdapter.signupUser(createUserRequest);

        if(response.getBody().getHttpStatus().is2xxSuccessful()){
            return "login";
        }
        return "404";
    }
}
