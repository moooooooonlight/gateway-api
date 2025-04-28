package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.MileStoneAdapter;
import com.nhnacademy.gatewayapi.adapter.TaskAdapter;
import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.request.CreateMileStoneRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/milestones")
public class MileStoneController {
    private final MileStoneAdapter mileStoneAdapter;

    @Operation(
            summary = "마일스톤 등록 Form API",
            description = "마일스톤 등록 Form 가져오는 동작 수행"
    )
    @GetMapping("/{projectId}")
    public String getMileStoneRegisterForm(@PathVariable long projectId,
                                           Model model){

        model.addAttribute("projectId", projectId);

        return "taskApi/mileStoneRegister";
    }

    @Operation(
            summary = "프로젝트 마일스톤 등록 API",
            description = "프로젝트에 마일스톤 등록 수행"
    )
    @PostMapping("/{projectId}")
    public String registerMileStone(@PathVariable long projectId,
                                          @RequestParam String milestoneName,
                                          @RequestParam(required = false) LocalDate startDate,
                                          @RequestParam(required = false) LocalDate endDate) {

        ResponseDTO responseDTO = mileStoneAdapter.saveProjectMilestone(projectId, new CreateMileStoneRequest(milestoneName, startDate, endDate));
        if (responseDTO.getHttpStatus().is2xxSuccessful()) {
            return String.format("redirect:/home/%d", projectId);
        }
        return "error/404";
    }


    @Operation(
            summary = "Task 마일스톤 등록 API",
            description = "Task에 마일스톤 등록 수행"
    )
    @PostMapping("/{projectId}/{taskId}")
    public String registerTaskMilestone(@PathVariable long projectId,
                                              @PathVariable long taskId,
                                              @RequestParam long milestoneId){

        ResponseDTO responseDTO = mileStoneAdapter.saveTaskMileStone(projectId, taskId, milestoneId);
        if(responseDTO.getHttpStatus().is2xxSuccessful()){
            return String.format("redirect:/home/%d/%d", projectId, taskId);
        }

        return "error/404";
    }


    @Operation(
            summary = "Task의 마일스톤 삭제 API",
            description = "Task의 마일스톤 삭제 수행"
    )
    @DeleteMapping("/{projectId}/{milestoneId}")
    public String deleteProjectMilestone(@PathVariable long projectId,
                                         @PathVariable long milestoneId){

        ResponseDTO responseDTO = mileStoneAdapter.deleteProjectMilestone(projectId, milestoneId);

        if(responseDTO.getHttpStatus().is2xxSuccessful()) {
            return String.format("redirect:/home/%d", projectId);
        }
        return "error/404";
    }
}

