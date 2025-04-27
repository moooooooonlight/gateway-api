package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.CommentAdapter;
import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.request.CreateCommentRequest;
import com.nhnacademy.gatewayapi.domain.request.CreateTaskRequest;
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

    @PostMapping("/{projectId}/{taskId}")
    public ModelAndView registerComment(@PathVariable long projectId,
                                        @PathVariable long taskId,
                                        @RequestParam String content,
                                        HttpServletRequest request){
        ModelAndView mav = new ModelAndView(String.format("redirect:/home/%d/%d", projectId, taskId));

        String userId = (String) request.getSession().getAttribute("userId");
        CreateCommentRequest createCommentRequest = new CreateCommentRequest(userId, content);
        ResponseDTO responseDTO = commentAdapter.registerComment(projectId, taskId, createCommentRequest);
        if(responseDTO.getHttpStatus().is2xxSuccessful()){
            return mav;
        }

        return new ModelAndView("error/404");
    }
}
