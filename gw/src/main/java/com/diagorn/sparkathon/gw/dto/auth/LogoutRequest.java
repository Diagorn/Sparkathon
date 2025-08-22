package com.diagorn.sparkathon.gw.dto.auth;

import lombok.Data;
import lombok.ToString;

/**
 * Logout request
 *
 * @author diagorn
 */
@Data
@ToString
public class LogoutRequest {
    /**
     * Last refresh token
     */
    private String refreshToken;
}
