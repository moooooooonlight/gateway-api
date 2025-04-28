package com.nhnacademy.gatewayapi.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class CreateMileStoneRequest {
    private String milestoneName;
    private LocalDate startDate;
    private LocalDate endDate;
}
