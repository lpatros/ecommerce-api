package com.lpatros.ecommerce_api.controller.auth;

import com.lpatros.ecommerce_api.dto.auth.LoginRequest;
import com.lpatros.ecommerce_api.dto.auth.LoginResponse;
import com.lpatros.ecommerce_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements Auth {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
