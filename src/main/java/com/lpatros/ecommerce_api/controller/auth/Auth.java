package com.lpatros.ecommerce_api.controller.auth;

import com.lpatros.ecommerce_api.dto.auth.LoginRequest;
import com.lpatros.ecommerce_api.dto.auth.LoginResponse;
import com.lpatros.ecommerce_api.exception.RestErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Authentication")
@RequestMapping("auth")
public interface Auth {

    @Operation(summary = "Authenticate user and return JWT token", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authentication", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class))),
            @ApiResponse(responseCode = "401", description = "Invalid email or password", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class)))
    })
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest);
}
