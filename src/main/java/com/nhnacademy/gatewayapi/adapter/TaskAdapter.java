package com.nhnacademy.gatewayapi.adapter;

import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.dto.TaskDTO;
import com.nhnacademy.gatewayapi.domain.dto.TaskListDTO;
import com.nhnacademy.gatewayapi.domain.model.Task;
import com.nhnacademy.gatewayapi.domain.request.CreateTaskRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class TaskAdapter {
    private String API_SERVER_ADDRESS = "http://localhost:6060";
    private final RestTemplate restTemplate;

    public TaskAdapter() {
        this.restTemplate = new RestTemplate();
        ;
    }


    public List<Task> getTaskList(Long projectId) {
        String url = String.format("%s/%d/",API_SERVER_ADDRESS,projectId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<TaskListDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                TaskListDTO.class
        );

        return responseEntity.getBody().getTasks();
    }

    public ResponseDTO registerTask(Long projectId, CreateTaskRequest createTaskRequest) {
        String rawUrl = String.format("%s/%d/", API_SERVER_ADDRESS, projectId);
        String url = UriComponentsBuilder.fromHttpUrl(rawUrl)
                .queryParam("userId", createTaskRequest.getUserId())
                .toUriString();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateTaskRequest> requestEntity = new HttpEntity<>(createTaskRequest, httpHeaders);

        ResponseEntity<ResponseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                ResponseDTO.class
        );
        return response.getBody();
    }


    public Task getTask(Long projectId, Long taskId) {
        String url = String.format("%s/%d/%d",API_SERVER_ADDRESS,projectId, taskId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<TaskDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                TaskDTO.class
        );

        TaskDTO body = responseEntity.getBody();
        Task task = new Task(body.getTaskId(),
                body.getProjectId(),
                body.getMileStoneId(),
                body.getTaskName(),
                body.getCreatorId(),
                body.getManagerId(),
                body.getCreatedAt());

        return task;
    }



    public ResponseDTO updateTask(Long projectId, Long taskId, CreateTaskRequest taskRequest) {
        String url = String.format("%s/%d/%d",API_SERVER_ADDRESS, projectId,taskId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateTaskRequest> httpEntity = new HttpEntity<>(taskRequest,httpHeaders);

        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                httpEntity,
                ResponseDTO.class
        );

        return responseEntity.getBody();
    }

    public ResponseDTO deleteTask(Long projectId, Long taskId) {
        String url = String.format("%s/%d/%d",API_SERVER_ADDRESS, projectId,taskId);

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
