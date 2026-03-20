package com.diagorn.sparkathon.auth.dto.auth

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Revoke token response
 *
 * @author diagorn
 */
class RevokeTokenResponse(
    /**
     * Is token revoked
     */
    @Schema(description = "Is token revoked", example = "true")
    val revoked: Boolean,

    /**
     * Comments
     */
    @Schema(description = "Comments", example = "Success")
    val message: String
)