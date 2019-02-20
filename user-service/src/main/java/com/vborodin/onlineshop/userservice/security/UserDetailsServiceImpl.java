package com.vborodin.onlineshop.userservice.security;

import com.vborodin.onlineshop.userservice.user.User;
import com.vborodin.onlineshop.userservice.user.UserService;
import com.vborodin.onlineshop.userservice.user.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) {
        Optional<User> user = userService.findByLogin(login);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.get().getLogin())
                .password(user.get().getPassword())
                .authorities(user.get().getRole().name())
                .accountLocked(!UserStatus.ACTIVE.equals(user.get().getStatus()))
                .build();
    }
}