package com.nhnacademy.gatewayapi.domain.model;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@AllArgsConstructor
public class AcademyUser implements UserDetails{
    private String userId;

    private String userPassword;

    private String userEmail;

    private String userName;

    private CUD userCud;
    public AcademyUser(User user) {
        this.userId = user.getUserId();
        this.userPassword = user.getUserPassword();
        this.userEmail = user.getUserEmail();
        this.userName = user.getUserName();
        this.userCud = user.getUserCud();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_USER";
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }
}
