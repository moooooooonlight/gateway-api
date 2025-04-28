package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.CommentAdapter;
import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.request.CreateCommentRequest;
import com.nhnacademy.gatewayapi.domain.request.CreateTaskRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentAdapter commentAdapter;


    @Operation(
            summary = "task에 Comment 등록 API",
            description = "Task에 Comment 등록해주는 동작"
    )
    @PostMapping("/{projectId}/{taskId}")
    public String registerComment(@PathVariable long projectId,
                                        @PathVariable long taskId,
                                        @RequestParam String content,
                                        HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userId");
        CreateCommentRequest createCommentRequest = new CreateCommentRequest(userId, content);
        ResponseDTO responseDTO = commentAdapter.registerComment(projectId, taskId, createCommentRequest);
        if(responseDTO.getHttpStatus().is2xxSuccessful()){
            return String.format("redirect:/home/%d/%d", projectId, taskId);
        }

        return "error/404";
    }
}
