package com.nhnacademy.gatewayapi.domain.dto;

import com.nhnacademy.gatewayapi.domain.model.CUD;
import com.nhnacademy.gatewayapi.domain.model.User;
import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserResponseDTO {
    private String userId;

    private String userPassword;

    private String userEmail;

    private String userName;

    private CUD userCud;
}
