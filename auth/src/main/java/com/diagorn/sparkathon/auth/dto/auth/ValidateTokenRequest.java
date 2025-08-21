package com.diagorn.sparkathon.auth.dto.auth;

import lombok.Data;
import lombok.ToString;

/**
 * Token validation request
 *
 * @author diagorn
 */
@Data
@ToString
public class ValidateTokenRequest {
    /**
     * Token to validate
     */
    private String token;
}
