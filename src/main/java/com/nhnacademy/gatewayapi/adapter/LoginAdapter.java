package com.nhnacademy.gatewayapi.adapter;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class LoginAdapter {
    private String API_SERVER_ADDRESS = "http://localhost:7070";

    private final RestTemplate restTemplate;

    public LoginAdapter() {
        this.restTemplate = new RestTemplate();;
    }


    public ResponseEntity<Void> doLogin(String userId, String password){
        String url = UriComponentsBuilder.fromHttpUrl(API_SERVER_ADDRESS+"/login")
                .queryParam("userId", userId)
                .queryParam("password", password)
                .toUriString();


        return restTemplate.getForEntity(
                url,
                Void.class
        );
    }
}
