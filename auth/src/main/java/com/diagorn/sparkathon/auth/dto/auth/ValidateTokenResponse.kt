package com.diagorn.sparkathon.auth.dto.auth

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Token validation response
 *
 * @author diagorn
 */
class ValidateTokenResponse(
    /**
     * If token is valid
     */
    @Schema(description = "If token is valid", example = "true")
    var isValid: Boolean,
    /**
     * Token lifespan (ms)
     */
    @Schema(description = "Token lifespan (ms)", example = "86400000")
    val expiresIn: Long,
    
    /**
     * Token owner's role
     */
    @Schema(description = "Token owner's role", example = "PARTICIPANT")
    val role: String
)