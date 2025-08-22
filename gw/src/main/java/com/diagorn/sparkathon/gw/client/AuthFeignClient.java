package com.diagorn.sparkathon.gw.client;

import com.diagorn.sparkathon.gw.dto.auth.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Authentication feign client
 *
 * @author diagorn
 */
@FeignClient(
        name = "auth",
        url = "${spring.cloud.gateway.routes[0].uri}"
)
public interface AuthFeignClient {

    @PostMapping("api/v1/auth/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);

    @PostMapping("api/v1/auth/logout")
    ResponseEntity<LogoutResponse> logout(@RequestBody LogoutRequest request);

    @PatchMapping("api/v1/auth/refresh")
    ResponseEntity<RefreshTokenResponse> refresh(@RequestBody RefreshTokenRequest request);

    @DeleteMapping("api/v1/auth/revoke")
    ResponseEntity<RevokeTokenResponse> revoke(@RequestBody RevokeTokenRequest request);

    @PostMapping("api/v1/auth/validate")
    ResponseEntity<ValidateTokenResponse> validate(@RequestBody ValidateTokenRequest request);
}
