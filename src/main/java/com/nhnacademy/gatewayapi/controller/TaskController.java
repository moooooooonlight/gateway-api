package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.ProjectAdapter;
import com.nhnacademy.gatewayapi.adapter.TaskAdapter;
import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.model.Project;
import com.nhnacademy.gatewayapi.domain.model.ProjectStatus;
import com.nhnacademy.gatewayapi.domain.model.Task;
import com.nhnacademy.gatewayapi.domain.request.CreateProjectRequest;
import com.nhnacademy.gatewayapi.domain.request.CreateTaskRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskAdapter taskAdapter;

    @Operation(
            summary = "Task 등록 페이지 API",
            description = "Task 등록 페이지 띄워주는 동작 수행"
    )
    @GetMapping("/{projectId}")
    public String getTaskRegisterPage(@PathVariable String projectId,
                                      HttpServletRequest request,
                                      Model model) {


        String userId = (String) request.getSession().getAttribute("userId");
        model.addAttribute("userId", userId);
        model.addAttribute("projectId", projectId);

        return "taskApi/taskRegister";
    }

    @Operation(
            summary = "Task 등록 API",
            description = "Task 등록 수행"
    )
    @PostMapping("/{projectId}")
    public String registerTask(@PathVariable long projectId,
                                  @RequestParam String userId,
                                  @RequestParam String taskName) {
        CreateTaskRequest createTaskRequest = new CreateTaskRequest(taskName, userId, userId);
        ResponseDTO responseDTO = taskAdapter.registerTask(projectId, createTaskRequest);

        if(responseDTO.getHttpStatus().is2xxSuccessful()){
            return String.format("redirect:/home/%s", projectId);
        }
        return "error/404";
    }

    @Operation(
            summary = "Task 수정 페이지 API",
            description = "Task 업데이트 페이지 띄워주는 동작 수행"
    )
    @GetMapping("/{projectId}/{taskId}")
    public String updateTask(@PathVariable long projectId,
                             @PathVariable long taskId,
                             Model model){

        Task task = taskAdapter.getTask(projectId, taskId);
        model.addAttribute("task", task);

        return "taskApi/taskUpdateForm";
    }


    @Operation(
            summary = "Task 수정 API",
            description = "Task 업데이트 수행"
    )
    @PutMapping("/{projectId}/{taskId}")
    public String updateTask(@PathVariable long projectId,
                             @PathVariable long taskId,
                             @RequestParam String taskName,
                             HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userId");
        CreateTaskRequest taskRequest = new CreateTaskRequest(taskName, userId, userId);
        ResponseDTO responseDTO = taskAdapter.updateTask(projectId, taskId, taskRequest);
        if(responseDTO.getHttpStatus().is2xxSuccessful()){
            return String.format("redirect:/home/%s", projectId);
        }

        return "error/404";
    }


    @Operation(
            summary = "Task 삭제 API",
            description = "Task 삭제 수행"
    )
    @DeleteMapping("/{projectId}/{taskId}")
    public String deleteTask(@PathVariable long projectId,
                             @PathVariable long taskId){

        ResponseDTO responseDTO = taskAdapter.deleteTask(projectId, taskId);
        if(responseDTO.getHttpStatus().is2xxSuccessful()){
            return String.format("redirect:/home/%s", projectId);
        }

        return "error/404";
    }
}
