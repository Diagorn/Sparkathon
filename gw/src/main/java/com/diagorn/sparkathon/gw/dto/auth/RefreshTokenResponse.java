package com.diagorn.sparkathon.gw.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Refresh token response
 *
 * @author diagorn
 */
@Data
@NoArgsConstructor
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
