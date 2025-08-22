package com.diagorn.sparkathon.gw.dto.auth;

import lombok.Data;
import lombok.ToString;

/**
 * Login request
 *
 * @author diagorn
 */
@Data
@ToString
public class LoginRequest {
    /**
     * Username
     */
    private String login;
    /**
     * User password
     */
    private String password;
}
