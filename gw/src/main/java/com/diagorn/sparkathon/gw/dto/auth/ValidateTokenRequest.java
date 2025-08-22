package com.diagorn.sparkathon.gw.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * Token validation request
 *
 * @author diagorn
 */
@Data
@ToString
@AllArgsConstructor
public class ValidateTokenRequest {
    /**
     * Token to validate
     */
    private String token;
}
