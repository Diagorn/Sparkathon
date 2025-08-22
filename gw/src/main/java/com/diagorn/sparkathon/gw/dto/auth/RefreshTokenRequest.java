package com.diagorn.sparkathon.gw.dto.auth;

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
