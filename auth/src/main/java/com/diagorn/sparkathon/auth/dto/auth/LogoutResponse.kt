package com.diagorn.sparkathon.auth.dto.auth

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Logout response
 *
 * @author diagorn
 */
class LogoutResponse(
    /**
     * Logout succeeded
     */
    @Schema(description = "Logout succeeded", example = "true")
    val success: Boolean,

    /**
     * Comments
     */
    @Schema(description = "Comments", example = "Success")
    val message: String
)