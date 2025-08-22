package com.diagorn.sparkathon.gw.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Login response
 *
 * @author diagorn
 */
@Data
@NoArgsConstructor
@ToString
public class LoginResponse {
    /**
     * Access token
     */
    private String accessToken;
    /**
     * Refresh token
     */
    private String refreshToken;
    /**
     * User role
     */
    private String role;
    /**
     * Access token expire timestamp
     */
    private Long expiresIn;
}
