package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.TagAdapter;
import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.request.CreateTagRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tags")
public class TagController {
    private final TagAdapter tagAdapter;

    @PostMapping("/{projectId}")
    public String registerTag(@PathVariable long projectId,
                            @RequestParam String tagName) {

        ResponseDTO responseDTO = tagAdapter.registerProjectTag(projectId, new CreateTagRequest(tagName));

        if(responseDTO.getHttpStatus().is2xxSuccessful()){
            return String.format("redirect:/home/%d",projectId);
        }
        return "error/404";
    }

    @PostMapping("/{projectId}/{taskId}")
    public String registerTaskTag(@PathVariable long projectId,
                              @PathVariable long taskId,
                              @RequestParam long tagId) {

        ResponseDTO responseDTO = tagAdapter.saveTaskTag(projectId, taskId, tagId);

        if(responseDTO.getHttpStatus().is2xxSuccessful()){
            return String.format("redirect:/home/%d/%d",projectId, taskId);
        }
        return "error/404";
    }
}
