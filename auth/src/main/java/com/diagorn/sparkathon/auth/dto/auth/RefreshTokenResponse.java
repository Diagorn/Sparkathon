package com.diagorn.sparkathon.auth.dto.auth;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Refresh token response
 *
 * @author diagorn
 */
@Data
@Builder
@ToString
public class RefreshTokenResponse {
    /**
     * New access token
     */
    private String accessToken;
    /**
     * New access token expiration timestamp
     */
    private Long expiresIn;
}
