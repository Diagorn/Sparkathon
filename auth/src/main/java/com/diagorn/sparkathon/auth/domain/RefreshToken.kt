package com.diagorn.sparkathon.auth.domain

import java.time.Instant

/**
 * Authentication refresh token
 *
 * @author diagorn
 */
class RefreshToken(
    /**
     * Id of user aquired this token
     */
    var userId: Long,

    /**
     * If token is revoked
     */
    var revoked: Boolean = false,

    /**
     * Moment when token was created
     */
    val createdAt: Instant? = null,
)