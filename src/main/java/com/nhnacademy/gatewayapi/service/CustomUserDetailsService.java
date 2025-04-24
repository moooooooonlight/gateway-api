package com.nhnacademy.gatewayapi.service;

import com.nhnacademy.gatewayapi.adapter.UserAdapter;
import com.nhnacademy.gatewayapi.domain.model.AcademyUser;
import com.nhnacademy.gatewayapi.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAdapter userAdapter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User) userAdapter.getUser(username);
        return new AcademyUser(user);
    }
}
