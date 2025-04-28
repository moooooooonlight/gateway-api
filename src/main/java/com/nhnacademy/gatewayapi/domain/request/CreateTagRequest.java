package com.nhnacademy.gatewayapi.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateTagRequest {
    private String tagName;
}
