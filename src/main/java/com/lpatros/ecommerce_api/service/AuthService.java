package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.configuration.JwtTokenConfig;
import com.lpatros.ecommerce_api.dto.auth.LoginRequest;
import com.lpatros.ecommerce_api.dto.auth.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenConfig jwtTokenConfig;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtTokenConfig jwtTokenConfig) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenConfig = jwtTokenConfig;
    }

    public LoginResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        String token = jwtTokenConfig.generateToken(authentication.getName());
        return new LoginResponse(token);
    }
}
