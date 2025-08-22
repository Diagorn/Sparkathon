package com.diagorn.sparkathon.gw.dto.auth;

import lombok.Data;
import lombok.ToString;

/**
 * Revoke token request
 *
 * @author diagorn
 */
@Data
@ToString
public class RevokeTokenRequest {
    /**
     * Refresh token to be revoked
     */
    private String refreshToken;
}
