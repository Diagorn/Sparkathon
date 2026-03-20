package com.diagorn.sparkathon.auth.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * Request to register a new user
 *
 * @author diagorn
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {
    /**
     * New user role ID
     */
    @Schema(description = "New user role ID", example = "1", requiredMode = REQUIRED)
    private Long roleId;
    /**
     * New user login
     */
    @NotBlank(message = "Login is mandatory")
    @Size(min = 5, message = "Login must be at least 5 letters long")
    @Schema(description = "New user login", example = "diagorn", requiredMode = REQUIRED)
    private String login;
    /**
     * New user password
     */
    @Size(min = 7, message = "Password must be at least 7 letters long")
    @Schema(description = "New user password", example = "qwe123", requiredMode = REQUIRED)
    private String password;
    /**
     * New user email
     */
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email must be valid")
    @Schema(description = "New user email", example = "diagorn1999@yandex.ru", requiredMode = REQUIRED)
    private String email;
    /**
     * New user TG nickname
     */
    @Schema(description = "New user telegram nickname", example = "@diagorn", requiredMode = REQUIRED)
    private String telegramNickname;
}
