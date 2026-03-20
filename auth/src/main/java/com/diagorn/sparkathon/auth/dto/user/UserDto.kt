package com.diagorn.sparkathon.auth.dto.user

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * User DTO
 *
 * @author diagorn
 */
class UserDto(
    /**
     * User ID
     */
    @Schema(description = "User ID", example = "7", requiredMode = RequiredMode.REQUIRED)
    val id: Long,

    /**
     * User role
     */
    @Schema(
        description = "User role", example = """
                {
                    "id": 1,
                    "name": "PARTICIPANT"
                }
                """, 
        requiredMode = RequiredMode.REQUIRED
    )
    val role: RoleDto,
    
    /**
     * User name
     */
    @Schema(description = "User name", example = "Michael", requiredMode = RequiredMode.REQUIRED)
    val firstName: @NotBlank(message = "First name is mandatory") String,
    
    /**
     * User middle name
     */
    @Schema(description = "User middle name", example = "Alexandrovich", requiredMode = RequiredMode.NOT_REQUIRED)
    val middleName: String? = null,
    
    /**
     * User last name
     */
    @Schema(description = "User last name", example = "Gasin", requiredMode = RequiredMode.REQUIRED)
    val lastName: @NotBlank(message = "Last name is mandatory") String,
    
    /**
     * User login
     */
    @Schema(description = "User login", example = "diagorn", requiredMode = RequiredMode.REQUIRED)
    val login: @NotBlank(message = "Login is mandatory") @Size(
        min = 5,
        message = "Login must be at least 5 letters long"
    ) String,
    
    /**
     * User email
     */
    @Schema(description = "User email", example = "diagorn1999@yandex.ru", requiredMode = RequiredMode.REQUIRED)
    val email: String,
    
    /**
     * User TG nickname
     */
    @Schema(description = "User TG nickname", example = "@diagorn", requiredMode = RequiredMode.REQUIRED)
    val telegramNickname: String,
    
    /**
     * User full name (like Gasin M. A.)
     */
    @Schema(description = "User full name", example = "Gasin M. A.", requiredMode = RequiredMode.NOT_REQUIRED)
    val fullName: String? = null
)