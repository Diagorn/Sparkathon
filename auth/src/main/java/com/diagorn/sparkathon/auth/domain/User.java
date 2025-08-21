package com.diagorn.sparkathon.auth.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * System user
 *
 * @author diagorn
 */
@Entity
@Table(name = "usr")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity {
    /**
     * User role
     */
    @ManyToOne(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
    /**
     * First name
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;
    /**
     * Middle name
     */
    @Column(name = "middle_name")
    private String middleName;
    /**
     * Last name
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;
    /**
     * User login (username)
     */
    @Column(name = "login", nullable = false, unique = true)
    private String login;
    /**
     * User password
     */
    @Column(name = "password", nullable = false)
    private String password;
    /**
     * User email
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;
}
