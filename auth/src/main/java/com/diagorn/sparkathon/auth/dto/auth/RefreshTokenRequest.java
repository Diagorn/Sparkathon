package com.diagorn.sparkathon.auth.dto.auth;

import lombok.Data;
import lombok.ToString;

/**
 * Refresh token request
 *
 * @author diagorn
 */
@Data
@ToString
public class RefreshTokenRequest {
    /**
     * Refresh token
     */
    private String refreshToken;
}
