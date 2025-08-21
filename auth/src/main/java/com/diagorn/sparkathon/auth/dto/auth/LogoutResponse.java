package com.diagorn.sparkathon.auth.dto.auth;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Logout response
 *
 * @author diagorn
 */
@Data
@Builder
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
