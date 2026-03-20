package com.diagorn.sparkathon.auth.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Login response
 *
 * @author diagorn
 */
@Data
@Builder
@ToString
public class LoginResponse {
    /**
     * Access token
     */
    @Schema(description = "Access token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWFnb3JuIiwiaWF0IjoxNzU1ODc1MjAzLCJleHAiOjE3NTU4ODM4NDN9.3y8Jbs4Zz0tx7j0u7ZuIbppJASYt1kIl610bMnN7Zuk")
    private String accessToken;
    /**
     * Refresh token
     */
    @Schema(description = "Refresh token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWFnb3JuIiwiaWF0IjoxNzU1ODc1MjAzLCJleHAiOjE3NTU4ODM4NDN9.3y8Jbs4Zz0tx7j0u7ZuIbppJASYt1kIl610bMnN7Zuk")
    private String refreshToken;
    /**
     * User role
     */
    @Schema(description = "User role", example = "PARTICIPANT")
    private String role;
    /**
     * Access token expire lifespan (ms)
     */
    @Schema(description = "Access token lifespan (ms)", example = "86400000")
    private Long expiresIn;
}
