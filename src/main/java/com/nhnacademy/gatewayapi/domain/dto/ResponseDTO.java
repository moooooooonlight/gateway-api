package com.nhnacademy.gatewayapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDTO {
    HttpStatus httpStatus;
    String responseMessage;
}
