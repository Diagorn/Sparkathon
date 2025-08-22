package com.diagorn.sparkathon.gw.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Logout response
 *
 * @author diagorn
 */
@Data
@NoArgsConstructor
@ToString
public class LogoutResponse {
    /**
     * Logout succeeded
     */
    private boolean success;
    /**
     * Comments
     */
    private String message;
}
