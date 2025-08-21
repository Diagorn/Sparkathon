package com.diagorn.sparkathon.auth.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Response to an authentication request
 *
 * @author mikhail.gasin
 */
@Data
@Builder
public class AuthResponse {
    /**
     * Main JWT token
     */
    private String accessToken;
    /**
     * Refresh JWT token
     */
    private String refreshToken;
}
