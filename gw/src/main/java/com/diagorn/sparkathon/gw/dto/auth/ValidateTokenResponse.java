package com.diagorn.sparkathon.gw.dto.auth;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Token validation response
 *
 * @author diagorn
 */
@Data
@NoArgsConstructor
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
