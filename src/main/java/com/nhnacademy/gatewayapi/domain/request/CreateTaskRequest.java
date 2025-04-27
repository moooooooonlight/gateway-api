package com.nhnacademy.gatewayapi.domain.request;

import com.nhnacademy.gatewayapi.domain.model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.annotation.Target;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateTaskRequest {
    private String taskName;
    private String userId;
    private String managerId;
}
