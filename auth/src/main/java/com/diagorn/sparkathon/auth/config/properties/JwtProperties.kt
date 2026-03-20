package com.diagorn.sparkathon.auth.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * JWT properties
 *
 * @author diagorn
 */
@ConfigurationProperties(prefix = "application.jwt")
class JwtProperties(
    /**
     * Access token expire time, seconds
     */
    val accessExpireTimeSec: Int,

    /**
     * Refresh token expire time, seconds
     */
    val refreshExpireTimeSec: Int,

    val secret: String
)