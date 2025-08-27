package com.diagorn.sparkathon.auth.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * User DTO
 *
 * @author diagorn
 */
@Data
public class UserDTO {
    /**
     * User ID
     */
    @Schema(description = "User ID", example = "7", requiredMode = REQUIRED)
    private Long id;
    /**
     * User role
     */
    @Schema(description = "User role", example = """
            {
                "id": 1,
                "name": "PARTICIPANT"
            }
            """, requiredMode = REQUIRED)
    private RoleDTO role;
    /**
     * User name
     */
    @NotBlank(message = "First name is mandatory")
    @Schema(description = "User name", example = "Michael", requiredMode = REQUIRED)
    private String firstName;
    /**
     * User middle name
     */
    @Schema(description = "User middle name", example = "Alexandrovich", requiredMode = NOT_REQUIRED)
    private String middleName;
    /**
     * User last name
     */
    @NotBlank(message = "Last name is mandatory")
    @Schema(description = "User last name", example = "Gasin", requiredMode = REQUIRED)
    private String lastName;
    /**
     * User login
     */
    @NotBlank(message = "Login is mandatory")
    @Size(min = 5, message = "Login must be at least 5 letters long")
    @Schema(description = "User login", example = "diagorn", requiredMode = REQUIRED)
    private String login;
    /**
     * User email
     */
    @Schema(description = "User email", example = "diagorn1999@yandex.ru", requiredMode = REQUIRED)
    private String email;
    /**
     * User TG nickname
     */
    @Schema(description = "User TG nickname", example = "@diagorn", requiredMode = REQUIRED)
    private String telegramNickname;
    /**
     * User full name (like Gasin M. A.)
     */
    @Schema(
            description = "User full name",
            example = "Gasin M. A.",
            requiredMode = NOT_REQUIRED
    )
    private String fullName;
}
