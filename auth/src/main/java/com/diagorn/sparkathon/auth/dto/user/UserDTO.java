package com.diagorn.sparkathon.auth.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * User DTO
 *
 * @author diagorn
 */
@Data
public class UserDTO {
    /**
     * User ID
     */
    private Long id;
    /**
     * User role
     */
    private RoleDTO role;
    /**
     * User name
     */
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    /**
     * User middle name
     */
    private String middleName;
    /**
     * User last name
     */
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    /**
     * User login
     */
    @NotBlank(message = "Login is mandatory")
    @Size(min = 5, message = "Login must be at least 5 letters long")
    private String login;
    /**
     * User email
     */
    private String email;
    /**
     * User TG nickname
     */
    private String telegramNickname;
    /**
     * User full name (like Gasin M. A.)
     */
    private String fullName;
}
