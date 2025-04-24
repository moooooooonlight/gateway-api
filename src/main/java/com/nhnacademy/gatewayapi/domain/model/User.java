package com.nhnacademy.gatewayapi.domain.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class User {
    private String userId;
    private String userPassword;
    private String userEmail;
    private String userName;
    @JsonSerialize(using = ToStringSerializer.class)
    private CUD userCud;

    public User(String userId, String userPassword, String userEmail, String userName, CUD userCud) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userCud = userCud;
    }
}
