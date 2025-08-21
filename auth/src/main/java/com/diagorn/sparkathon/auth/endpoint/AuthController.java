package com.diagorn.sparkathon.auth.endpoint;

import com.diagorn.sparkathon.auth.dto.auth.*;
import com.diagorn.sparkathon.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication REST endpoint
 *
 * @author diagorn
 */
@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(@RequestBody LogoutRequest request) {
        LogoutResponse response = authenticationService.logout(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(@RequestBody RefreshTokenRequest request) {
        RefreshTokenResponse response = authenticationService.refresh(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/revoke")
    public ResponseEntity<RevokeTokenResponse> revoke(@RequestBody RevokeTokenRequest request) {
        RevokeTokenResponse response = authenticationService.revoke(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidateTokenResponse> validate(@RequestBody ValidateTokenRequest request) {
        ValidateTokenResponse response = authenticationService.validate(request);
        return ResponseEntity.ok(response);
    }
}
