package com.nhnacademy.gatewayapi.adapter;

import com.nhnacademy.gatewayapi.domain.dto.UserResponseDTO;
import com.nhnacademy.gatewayapi.domain.model.User;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class UserAdapter {
    private String API_SERVER_ADDRESS = "http://localhost:7070";

    private final RestTemplate restTemplate;

    public UserAdapter() {
        this.restTemplate = new RestTemplate();;
    }



    public User getUser(String userId){
        String url = API_SERVER_ADDRESS + "/user/{userId}";
        Map<String, String> pathVariable = Map.of("userId", userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);


        ResponseEntity<UserResponseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                UserResponseDTO.class,
                pathVariable
        );

        UserResponseDTO userResponseDTO = response.getBody();
        User user = new User(
                userResponseDTO.getUserId(),
                userResponseDTO.getUserPassword(),
                userResponseDTO.getUserEmail(),
                userResponseDTO.getUserName(),
                userResponseDTO.getUserCud()
        );

        return user;
    }



    public ResponseEntity<Void> updateUserCUD(String userId, String cud){
        String url = API_SERVER_ADDRESS + "/user/{userId}" + "?userCUD={userCUD}";
        Map<String, String> pathVariable = Map.of("userId", userId, "userCUD", cud);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);


        return restTemplate.exchange(
                url,
                HttpMethod.PUT,
                httpEntity,
                Void.class,
                pathVariable
        );
    }
}
