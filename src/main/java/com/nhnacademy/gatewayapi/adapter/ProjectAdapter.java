package com.nhnacademy.gatewayapi.adapter;

import com.nhnacademy.gatewayapi.domain.dto.ProjectDTO;
import com.nhnacademy.gatewayapi.domain.dto.ProjectListDTO;
import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.model.Project;
import com.nhnacademy.gatewayapi.domain.request.CreateProjectRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class ProjectAdapter {

    private String API_SERVER_ADDRESS = "http://localhost:6060";
    private final RestTemplate restTemplate;

    public ProjectAdapter() {
        this.restTemplate = new RestTemplate();;
    }


    /**
     *
     * @param createProjectRequest
     * @return
     */
    public ResponseDTO registerProject(CreateProjectRequest createProjectRequest) {
        String url = UriComponentsBuilder.fromHttpUrl(API_SERVER_ADDRESS + "/")
                .toUriString();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateProjectRequest> requestEntity = new HttpEntity<>(createProjectRequest, httpHeaders);

        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                ResponseDTO.class
        );

        return responseEntity.getBody();
    }


    /**
     *
     * @param userId
     * @return
     */
    public ProjectListDTO getProjectList(String userId){
        String url = UriComponentsBuilder.fromHttpUrl(API_SERVER_ADDRESS + "/")
                .queryParam("adminId",userId)
                .toUriString();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        HttpEntity<ProjectListDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                ProjectListDTO.class
        );

        return responseEntity.getBody();
    }


    /**
     *
     * @param userId
     * @param projectId
     * @return
     */
    public Project getProject(String userId, Long projectId){
        String url = UriComponentsBuilder.fromHttpUrl(API_SERVER_ADDRESS + "/{projectId}").toUriString();
        Map<String, Long> pathVariables = Map.of("projectId", projectId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<ProjectDTO> exchange = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                ProjectDTO.class,
                pathVariables
        );

        ProjectDTO body = exchange.getBody();
        return new Project(body.getProjectId(), body.getProjectName(), body.getCreatedAt(), body.getAdminId(), body.getProjectStatus());
    }


    /**
     *
     * @param userId
     * @param projectId
     * @return
     */
    public ResponseEntity<ResponseDTO> updateProject(String userId, Long projectId){
        String url = UriComponentsBuilder.fromHttpUrl(API_SERVER_ADDRESS + "/{projectId}")
                .queryParam("userId",userId)
                .toUriString();
        Map<String, Long> pathVariables = Map.of("projectId", projectId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ResponseDTO> httpEntity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(
                url,
                HttpMethod.PUT,
                httpEntity,
                ResponseDTO.class,
                pathVariables
        );
    }

    /**
     *
     * @param userId
     * @param projectId
     * @return
     */
    public ResponseEntity<ResponseDTO> deleteProject(String userId, Long projectId) {
        String url = UriComponentsBuilder.fromHttpUrl(API_SERVER_ADDRESS + "/{projectId}").toUriString();
        Map<String, Long> pathVariables = Map.of("projectId", projectId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ResponseDTO> httpEntity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                httpEntity,
                ResponseDTO.class,
                pathVariables
        );
    }
}