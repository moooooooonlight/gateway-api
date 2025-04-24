package com.nhnacademy.gatewayapi.domain.dto;

import com.nhnacademy.gatewayapi.domain.model.CUD;
import com.nhnacademy.gatewayapi.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreateUserRequest {
    private String userId;
    private String userPassword;
    private String userEmail;
    private String userName;


    public User makeUser(){
        User user = new User(this.userId,
                this.userPassword,
                this.userEmail,
                this.userName,
                CUD.ACTIVE);

        return user;
    }
}
