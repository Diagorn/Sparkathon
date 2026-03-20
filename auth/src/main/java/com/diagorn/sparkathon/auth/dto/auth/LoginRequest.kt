package com.diagorn.sparkathon.auth.dto.auth

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode

/**
 * Login request
 *
 * @author diagorn
 */
class LoginRequest(
    /**
     * Username
     */
    @Schema(description = "Username", example = "diagorn", requiredMode = RequiredMode.REQUIRED)
    val login: String,

    /**
     * User password
     */
    @Schema(description = "password", example = "qwe123", requiredMode = RequiredMode.REQUIRED)
    val password: String
)