package com.diagorn.sparkathon.auth.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

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
public class User extends AbstractEntity implements UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(this.role)
                .map(role -> Collections.singleton(new SimpleGrantedAuthority(this.role.getName())))
                .orElse(Collections.emptySet());
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
