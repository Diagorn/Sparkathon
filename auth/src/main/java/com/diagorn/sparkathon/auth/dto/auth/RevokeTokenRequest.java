package com.diagorn.sparkathon.auth.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Revoke token request
 *
 * @author diagorn
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RevokeTokenRequest {
    /**
     * Refresh token to be revoked
     */
    @Schema(description = "Refresh token to be revoked", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWFnb3JuIiwiaWF0IjoxNzU1ODc1MjAzLCJleHAiOjE3NTU4ODM4NDN9.3y8Jbs4Zz0tx7j0u7ZuIbppJASYt1kIl610bMnN7Zuk")
    @NotBlank(message = "Refresh token must be present")
    private String refreshToken;
}
