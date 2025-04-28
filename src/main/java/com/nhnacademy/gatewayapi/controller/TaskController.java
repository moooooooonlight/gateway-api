package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.ProjectAdapter;
import com.nhnacademy.gatewayapi.adapter.TaskAdapter;
import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.model.Project;
import com.nhnacademy.gatewayapi.domain.model.ProjectStatus;
import com.nhnacademy.gatewayapi.domain.model.Task;
import com.nhnacademy.gatewayapi.domain.request.CreateProjectRequest;
import com.nhnacademy.gatewayapi.domain.request.CreateTaskRequest;
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

    @GetMapping("/{projectId}")
    public ModelAndView getTaskRegisterPage(@PathVariable String projectId,
                                            HttpServletRequest request){
        ModelAndView mav = new ModelAndView("taskApi/taskRegister");
        Map<String, Object> model = mav.getModel();

        String userId = (String) request.getSession().getAttribute("userId");
        model.put("userId", userId);
        model.put("projectId", projectId);

        return mav;
    }

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


    @GetMapping("/{projectId}/{taskId}")
    public ModelAndView updateTask(@PathVariable long projectId,
                             @PathVariable long taskId){

        ModelAndView mav = new ModelAndView("taskApi/taskUpdateForm");

        Map<String, Object> model = mav.getModel();
        Task task = taskAdapter.getTask(projectId, taskId);
        model.put("task", task);

        return mav;
    }


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
