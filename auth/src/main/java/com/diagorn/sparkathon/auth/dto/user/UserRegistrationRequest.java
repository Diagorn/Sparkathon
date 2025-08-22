package com.diagorn.sparkathon.auth.dto.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Request to register a new user
 *
 * @author diagorn
 */
@Data
public class UserRegistrationRequest {
    /**
     * New user role ID
     */
    private Long roleId;
    /**
     * New user login
     */
    @NotBlank(message = "Login is mandatory")
    @Size(min = 5, message = "Login must be at least 5 letters long")
    private String login;
    /**
     * New user password
     */
    @Size(min = 7, message = "Password must be at least 7 letters long")
    private String password;
    /**
     * New user email
     */
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email must be valid")
    private String email;
    /**
     * New user TG nickname
     */
    private String telegramNickname;
}
