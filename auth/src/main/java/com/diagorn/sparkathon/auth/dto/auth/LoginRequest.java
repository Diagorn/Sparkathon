package com.diagorn.sparkathon.auth.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * Login request
 *
 * @author diagorn
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    /**
     * Username
     */
    @NotBlank(message = "Login must be present")
    @Schema(description = "Username", example = "diagorn", requiredMode = REQUIRED)
    private String login;
    /**
     * User password
     */
    @NotBlank(message = "Password must be present")
    @Schema(description = "password", example = "qwe123", requiredMode = REQUIRED)
    private String password;
}
