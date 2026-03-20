package com.diagorn.sparkathon.auth.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Token validation response
 *
 * @author diagorn
 */
@Data
@Builder
@ToString
public class ValidateTokenResponse {
    /**
     * If token is valid
     */
    @Schema(description = "If token is valid", example = "true")
    private boolean isValid;
    /**
     * Token lifespan (ms)
     */
    @Schema(description = "Token lifespan (ms)", example = "86400000")
    private Long expiresIn;
    /**
     * Token owner's role
     */
    @Schema(description = "Token owner's role", example = "PARTICIPANT")
    private String role;
}
