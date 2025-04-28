package com.nhnacademy.gatewayapi.adapter;

import com.nhnacademy.gatewayapi.domain.dto.MemberIdListDTO;
import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProjectMemberAdapter {
    private String API_SERVER_ADDRESS = "http://localhost:6060";
    private final RestTemplate restTemplate;

    public ProjectMemberAdapter() {
        this.restTemplate = new RestTemplate();
    }


    public List<String> getMemberIdList(Long projectId) {
        String url = String.format("%s/%d/member", API_SERVER_ADDRESS, projectId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        HttpEntity<MemberIdListDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                MemberIdListDTO.class
        );

        return responseEntity.getBody().getMemberIds();
    }



    public ResponseDTO registerMember(Long projectId, Long memberId) {
        String url = String.format("%s/%d/member/%d", API_SERVER_ADDRESS, projectId, memberId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        HttpEntity<ResponseDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                ResponseDTO.class
        );

        return responseEntity.getBody();
    }

    public ResponseDTO deleteMember(Long projectId, Long memberId) {
        String url = String.format("%s/%d/member/%d", API_SERVER_ADDRESS, projectId, memberId);

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
}
