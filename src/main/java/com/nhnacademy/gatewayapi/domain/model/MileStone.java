package com.nhnacademy.gatewayapi.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MileStone {
    private long milestoneId;
    private String milestoneName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Project project;
}
