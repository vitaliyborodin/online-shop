package com.vborodin.onlineshop.userservice.security;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/authorize",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AuthController {
    @GetMapping
    public List<String> authorize(Authentication authentication) {
        return authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.toList());
    }
}
