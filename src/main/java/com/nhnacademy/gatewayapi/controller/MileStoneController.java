package com.nhnacademy.gatewayapi.controller;

import com.nhnacademy.gatewayapi.adapter.MileStoneAdapter;
import com.nhnacademy.gatewayapi.adapter.TaskAdapter;
import com.nhnacademy.gatewayapi.domain.dto.ResponseDTO;
import com.nhnacademy.gatewayapi.domain.request.CreateMileStoneRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/milestones")
public class MileStoneController {
    private final MileStoneAdapter mileStoneAdapter;

    @GetMapping("/{projectId}")
    public ModelAndView getMileStoneRegisterForm(@PathVariable long projectId){
        ModelAndView mav = new ModelAndView("taskApi/mileStoneRegister");
        Map<String, Object> model = mav.getModel();
        model.put("projectId", projectId);

        return mav;
    }

    @PostMapping("/{projectId}")
    public ModelAndView registerMileStone(@PathVariable long projectId,
                                          @RequestParam String milestoneName,
                                          @RequestParam LocalDate startDate,
                                          @RequestParam LocalDate endDate){
        ModelAndView mav = new ModelAndView(String.format("redirect:/home/%d",projectId));

        ResponseDTO responseDTO = mileStoneAdapter.saveProjectMilestone(projectId, new CreateMileStoneRequest(milestoneName, startDate, endDate));
        if (responseDTO.getHttpStatus().is2xxSuccessful()) {
            return mav;
        }
        return new ModelAndView("error/404");
    }


    @PostMapping("/{projectId}/{taskId}")
    public ModelAndView registerTaskMilestone(@PathVariable long projectId,
                                              @PathVariable long taskId,
                                              @RequestParam long milestoneId){
        ModelAndView mav = new ModelAndView(String.format("redirect:/home/%d/%d", projectId, taskId));

        ResponseDTO responseDTO = mileStoneAdapter.saveTaskMileStone(projectId, taskId, milestoneId);
        if(responseDTO.getHttpStatus().is2xxSuccessful()){
            return mav;
        }

        return new ModelAndView("error/404");
    }
}
