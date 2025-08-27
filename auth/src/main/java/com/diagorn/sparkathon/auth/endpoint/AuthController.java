package com.diagorn.sparkathon.auth.endpoint;

import com.diagorn.sparkathon.auth.dto.auth.*;
import com.diagorn.sparkathon.auth.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication REST endpoint
 *
 * @author diagorn
 */
@RestController
@RequestMapping("api/v1/auth/")
@Tag(name = "Auth", description = "Security management API")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "Log in")
    public ResponseEntity<LoginResponse> login(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody
            LoginRequest request) {
        LoginResponse response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    @Operation(summary = "Log out")
    public ResponseEntity<LogoutResponse> logout(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody
            LogoutRequest request) {
        LogoutResponse response = authenticationService.logout(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/refresh")
    @Operation(summary = "Refresh access token")
    public ResponseEntity<RefreshTokenResponse> refresh(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody
            RefreshTokenRequest request) {
        RefreshTokenResponse response = authenticationService.refresh(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/revoke")
    @Operation(summary = "Revoke specific refresh token")
    public ResponseEntity<RevokeTokenResponse> revoke(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody
            RevokeTokenRequest request) {
        RevokeTokenResponse response = authenticationService.revoke(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    @Operation(summary = "Validate token")
    public ResponseEntity<ValidateTokenResponse> validate(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody
            ValidateTokenRequest request) {
        ValidateTokenResponse response = authenticationService.validate(request);
        return ResponseEntity.ok(response);
    }
}
