package com.diagorn.sparkathon.auth.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Logout request
 *
 * @author diagorn
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequest {
    /**
     * Last refresh token
     */
    @NotBlank(message = "Refresh token required to log out")
    @Schema(
            description = "Last refresh token",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWFnb3JuIiwiaWF0IjoxNzU1ODc1MjAzLCJleHAiOjE3NTU4ODM4NDN9.3y8Jbs4Zz0tx7j0u7ZuIbppJASYt1kIl610bMnN7Zuk",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String refreshToken;
}
