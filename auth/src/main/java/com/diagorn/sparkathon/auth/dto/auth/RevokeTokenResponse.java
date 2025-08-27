package com.diagorn.sparkathon.auth.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Revoke token response
 *
 * @author diagorn
 */
@Data
@Builder
@ToString
public class RevokeTokenResponse {
    /**
     * Is token revoked
     */
    @Schema(description = "Is token revoked", example = "true")
    private boolean revoked;
    /**
     * Comments
     */
    @Schema(description = "Comments", example = "Success")
    private String message;
}
