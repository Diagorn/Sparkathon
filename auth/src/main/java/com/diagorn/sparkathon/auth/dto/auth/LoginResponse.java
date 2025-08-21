package com.diagorn.sparkathon.auth.dto.auth;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Login response
 *
 * @author diagorn
 */
@Data
@Builder
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
