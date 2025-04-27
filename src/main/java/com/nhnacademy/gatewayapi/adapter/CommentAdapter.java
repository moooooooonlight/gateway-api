package com.nhnacademy.gatewayapi.adapter;

import com.nhnacademy.gatewayapi.domain.dto.CommentListDTO;
import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.model.Comment;
import com.nhnacademy.gatewayapi.domain.request.CreateCommentRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CommentAdapter {
    private String API_SERVER_ADDRESS = "http://localhost:6060";
    private final RestTemplate restTemplate;
    public CommentAdapter() {
        this.restTemplate = new RestTemplate();;
    }



    public List<Comment> getCommentList(Long projectId, Long taskId) {
        String url = String.format("%s/%d/%d/",API_SERVER_ADDRESS,projectId,taskId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<CommentListDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                CommentListDTO.class
        );

        return responseEntity.getBody().getComments();
    }


    public ResponseDTO registerComment(Long projectId, Long taskId, CreateCommentRequest createCommentRequest) {
        String url = String.format("%s/%d/%d/",API_SERVER_ADDRESS,projectId,taskId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateCommentRequest> requestEntity = new HttpEntity<>(createCommentRequest, httpHeaders);

        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                ResponseDTO.class
        );

        return responseEntity.getBody();
    }
}
