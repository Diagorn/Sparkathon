package com.diagorn.sparkathon.auth.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class User extends AbstractEntity implements UserDetails {
    /**
     * Identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usr_id_gen")
    @SequenceGenerator(name = "usr_id_gen", sequenceName = "usr_id_seq", allocationSize = 1)
    @Column(name = "id")
    protected Long id;
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
    /**
     * Telegram nickname
     */
    @Column(name = "telegram_nickname")
    private String telegramNickname;

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
