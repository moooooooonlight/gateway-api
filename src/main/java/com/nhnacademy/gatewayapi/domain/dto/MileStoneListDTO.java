package com.nhnacademy.gatewayapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhnacademy.gatewayapi.domain.model.MileStone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MileStoneListDTO {
    List<MileStone> milestones;
}
