package com.diagorn.sparkathon.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Authentication refresh token
 *
 * @author diagorn
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    /**
     * Id of user aquired this token
     */
    private Long userId;
    /**
     * If token is revoked
     */
    private boolean revoked;
    /**
     * Moment when token was created
     */
    private Instant createdAt;
}
