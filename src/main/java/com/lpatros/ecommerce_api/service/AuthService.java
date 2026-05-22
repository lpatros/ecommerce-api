package com.lpatros.ecommerce_api.service;

import com.lpatros.ecommerce_api.configuration.JwtTokenConfig;
import com.lpatros.ecommerce_api.dto.auth.LoginRequest;
import com.lpatros.ecommerce_api.dto.auth.LoginResponse;
import com.lpatros.ecommerce_api.entity.User;
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
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        User authenticationUser = (User) authenticationManager.authenticate(userAndPass).getPrincipal();

        String token = jwtTokenConfig.generateToken(authenticationUser.getId(), authenticationUser.getEmail());
        return new LoginResponse(token);
    }
}
