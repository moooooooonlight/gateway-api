package com.nhnacademy.gatewayapi.adapter;

import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.dto.TagListDTO;
import com.nhnacademy.gatewayapi.domain.model.Tag;
import com.nhnacademy.gatewayapi.domain.request.CreateTagRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TagAdapter {
    private String API_SERVER_ADDRESS = "http://localhost:6060";
    private final RestTemplate restTemplate;

    public TagAdapter() {
        this.restTemplate = new RestTemplate();;
    }

    public List<Tag> getProjectTagList(Long projectId) {
        String url = String.format("%s/%d/tag", API_SERVER_ADDRESS, projectId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        HttpEntity<TagListDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                TagListDTO.class
        );

        return responseEntity.getBody().getTags();
    }

    public ResponseDTO registerProjectTag(Long projectId, CreateTagRequest createTagRequest) {
        String url = String.format("%s/%d/tag", API_SERVER_ADDRESS, projectId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateTagRequest> requestEntity = new HttpEntity<>(createTagRequest, httpHeaders);

        HttpEntity<ResponseDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                ResponseDTO.class
        );

        return responseEntity.getBody();
    }

    public ResponseDTO updateProjectTag(Long projectId, CreateTagRequest createTagRequest) {
        String url = String.format("%s/%d/tag", API_SERVER_ADDRESS, projectId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateTagRequest> requestEntity = new HttpEntity<>(createTagRequest, httpHeaders);

        HttpEntity<ResponseDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                ResponseDTO.class
        );

        return responseEntity.getBody();
    }

    public ResponseDTO deleteProjectTag(Long projectId, Long tagId) {
        String url = String.format("%s/%d/tag/%d", API_SERVER_ADDRESS, projectId, tagId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        HttpEntity<ResponseDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                httpEntity,
                ResponseDTO.class
        );

        return responseEntity.getBody();
    }





    public List<Tag> getTaskTagList(Long projectId, Long taskId) {
        String url = String.format("%s/%d/%d/tag", API_SERVER_ADDRESS, projectId, taskId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<TagListDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                TagListDTO.class
        );

        return responseEntity.getBody().getTags();
    }

    public ResponseDTO saveTaskTag(Long projectId, Long taskId, Long tagId) {
        String url = String.format("%s/%d/%d/tag/%d", API_SERVER_ADDRESS, projectId, taskId, tagId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                ResponseDTO.class
        );

        return responseEntity.getBody();
    }

    public ResponseDTO deleteTaskTag(Long projectId, Long taskId, Long tagId) {
        String url = String.format("%s/%d/%d/tag/%d", API_SERVER_ADDRESS, projectId, taskId, tagId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                httpEntity,
                ResponseDTO.class
        );

        return responseEntity.getBody();
    }

}
