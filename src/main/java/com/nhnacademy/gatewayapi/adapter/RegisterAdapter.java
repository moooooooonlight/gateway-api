package com.nhnacademy.gatewayapi.adapter;

import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.request.CreateUserRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RegisterAdapter {
    private String API_SERVER_ADDRESS = "http://localhost:7070";
    private final RestTemplate restTemplate;

    public RegisterAdapter() {
        this.restTemplate = new RestTemplate();;
    }


    public ResponseEntity<ResponseDTO> signupUser(CreateUserRequest createUserRequest) {
        String url = API_SERVER_ADDRESS + "/login";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateUserRequest> requestEntity = new HttpEntity<>(createUserRequest, httpHeaders);

        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                ResponseDTO.class
        );

        return responseEntity;
    }
}
