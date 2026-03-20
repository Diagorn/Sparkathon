package com.diagorn.sparkathon.auth.dto.user

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * Request to register a new user
 *
 * @author diagorn
 */
class UserRegistrationRequest(
    /**
     * New user role ID
     */
    @Schema(description = "New user role ID", example = "1", requiredMode = REQUIRED)
    val roleId: Long,

    /**
     * New user login
     */
    @Schema(description = "New user login", example = "diagorn", requiredMode = REQUIRED)
    val login: @NotBlank(message = "Login is mandatory") @Size(
        min = 5,
        message = "Login must be at least 5 letters long"
    ) String,

    /**
     * New user password
     */
    @Schema(description = "New user password", example = "qwe123", requiredMode = REQUIRED)
    val password: @Size(min = 7, message = "Password must be at least 7 letters long") String,

    /**
     * New user email
     */
    @Schema(description = "New user email", example = "diagorn1999@yandex.ru", requiredMode = REQUIRED)
    val email: @NotBlank(message = "Email is mandatory") @Email(message = "Email must be valid") String,

    /**
     * New user TG nickname
     */
    @Schema(description = "New user telegram nickname", example = "@diagorn", requiredMode = REQUIRED)
    val telegramNickname: String,

    /**
     * New user firstname
     */
    @Schema(description = "New user first name", example = "Michael", requiredMode = REQUIRED)
    val firstName: String,

    /**
     * New user middle name
     */
    @Schema(description = "New user middle name", example = "Alexandrovich", requiredMode = NOT_REQUIRED)
    val middleName: String?,

    /**
     * New user last name
     */
    @Schema(description = "New user last name", example = "Gasin", requiredMode = REQUIRED)
    val lastName: String
)