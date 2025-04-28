package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.TagAdapter;
import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.request.CreateTagRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tags")
public class TagController {
    private final TagAdapter tagAdapter;

    @Operation(
            summary = "프로젝트 Tag 등록 API",
            description = "프로젝트에 Tag 등록해주는 동작 수행"
    )
    @PostMapping("/{projectId}")
    public String registerTag(@PathVariable long projectId,
                            @RequestParam String tagName) {

        ResponseDTO responseDTO = tagAdapter.registerProjectTag(projectId, new CreateTagRequest(tagName));

        if(responseDTO.getHttpStatus().is2xxSuccessful()){
            return String.format("redirect:/home/%d",projectId);
        }
        return "error/404";
    }

    @Operation(
            summary = "Task Tag 등록 API",
            description = "Task에 프로젝트Tag 등록 동작 수행"
    )
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


    @Operation(
            summary = "프로젝트 Task 제거 API",
            description = "프로젝트 Task 제거 동작 수행"
    )
    @DeleteMapping("/{projectId}/{tagId}")
    public String deleteProjectTag(@PathVariable("projectId") long projectId,
                                   @PathVariable("tagId") long tagId) {
        ResponseDTO responseDTO = tagAdapter.deleteProjectTag(projectId, tagId);

        if(responseDTO.getHttpStatus().is2xxSuccessful()) {
            return String.format("redirect:/home/%d", projectId);
        }
        return "error/404";
    }
}
