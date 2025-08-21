package com.diagorn.sparkathon.auth.dto.auth;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Token validation response
 *
 * @author diagorn
 */
@Data
@Builder
@ToString
public class ValidateTokenResponse {
    /**
     * If token is valid
     */
    private boolean isValid;
    /**
     * Token expire timestamp
     */
    private Long expiresIn;
    /**
     * Token owner's role
     */
    private String role;
}
