package com.diagorn.sparkathon.gw.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Revoke token response
 *
 * @author diagorn
 */
@Data
@NoArgsConstructor
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
