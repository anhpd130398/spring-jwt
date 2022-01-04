package com.example.springjwt.jwt;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenFilter  extends UsernamePasswordAuthenticationFilter {
    private final static String TOKEN_HEADER="Authorization";

    private AuthenticationManager authenticationManager;


}
