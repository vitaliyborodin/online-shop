package com.vborodin.onlineshop.productservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private HttpServletRequest request;

    @Value("${user-service-url:http://localhost:8085}")
    private String userServiceUrl;

    @Override
    public UserDetails loadUserByUsername(String login) {
        String role = WebClient.create(userServiceUrl)
                .get()
                .uri("/authorize")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, request.getHeader(AUTHORIZATION))
                .retrieve().bodyToMono(new ParameterizedTypeReference<List<String>>() {})
        .block().get(0);

        return org.springframework.security.core.userdetails.User.builder()
                .username(login)
                .password(extractPassword(request))
                .authorities(role)
                .build();
    }

    private String extractPassword(HttpServletRequest request) {
        final String authorization = request.getHeader(AUTHORIZATION);
        String password = "";
        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            password = credentials.split(":", 2)[1];
        }
        return password;
    }
}