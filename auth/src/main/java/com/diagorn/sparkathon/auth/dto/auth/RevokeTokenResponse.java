package com.diagorn.sparkathon.auth.dto.auth;

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
    private boolean revoked;
    /**
     * Comments
     */
    private String message;
}
