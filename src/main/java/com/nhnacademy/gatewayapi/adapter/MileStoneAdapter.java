package com.nhnacademy.gatewayapi.adapter;

import com.nhnacademy.gatewayapi.domain.dto.MileStoneDTO;
import com.nhnacademy.gatewayapi.domain.dto.MileStoneListDTO;
import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.model.MileStone;
import com.nhnacademy.gatewayapi.domain.request.CreateMileStoneRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MileStoneAdapter {
    private String API_SERVER_ADDRESS = "http://localhost:6060";
    private final RestTemplate restTemplate;
    public MileStoneAdapter() {
        this.restTemplate = new RestTemplate();
    }



    public List<MileStone> getProjectMilestoneList(Long projectId) {
        String url = String.format("%s/%d/milestone", API_SERVER_ADDRESS, projectId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        HttpEntity<MileStoneListDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                MileStoneListDTO.class
        );

        return responseEntity.getBody().getMilestones();
    }

    public ResponseDTO saveProjectMilestone(Long projectId, CreateMileStoneRequest createMileStoneRequest) {
        String url = String.format("%s/%d/milestone", API_SERVER_ADDRESS, projectId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateMileStoneRequest> requestEntity = new HttpEntity<>(createMileStoneRequest, httpHeaders);

        HttpEntity<ResponseDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                ResponseDTO.class
        );

        return responseEntity.getBody();
    }

    public ResponseDTO updateProjectMilestone(Long projectId, CreateMileStoneRequest createMileStoneRequest) {
        String url = String.format("%s/%d/milestone", API_SERVER_ADDRESS, projectId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateMileStoneRequest> requestEntity = new HttpEntity<>(createMileStoneRequest, httpHeaders);

        HttpEntity<ResponseDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                ResponseDTO.class
        );

        return responseEntity.getBody();
    }

    public ResponseDTO deleteProjectMilestone(Long projectId, Long milestoneId) {
        String url = String.format("%s/%d/milestone/%d", API_SERVER_ADDRESS, projectId, milestoneId);

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




    public MileStone getTaskMileStone(Long projectId, Long taskId) {
        String url = String.format("%s/%d/%d/milestone", API_SERVER_ADDRESS, projectId, taskId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<MileStoneDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                MileStoneDTO.class
        );

        MileStoneDTO body = responseEntity.getBody();
        return body.getMileStone();
    }

    public ResponseDTO saveTaskMileStone(Long projectId, Long taskId, Long milestoneId) {
        String url = String.format("%s/%d/%d/milestone/%d", API_SERVER_ADDRESS, projectId, taskId, milestoneId);

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

    public ResponseDTO deleteTaskMileStone(Long projectId, Long taskId) {
        String url = String.format("%s/%d/%d/milestone", API_SERVER_ADDRESS, projectId, taskId);

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
