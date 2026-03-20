package com.diagorn.sparkathon.auth.dto.auth

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Login response
 *
 * @author diagorn
 */
class LoginResponse(
    /**
     * Access token
     */
    @Schema(
        description = "Access token",
        example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWFnb3JuIiwiaWF0IjoxNzU1ODc1MjAzLCJleHAiOjE3NTU4ODM4NDN9.3y8Jbs4Zz0tx7j0u7ZuIbppJASYt1kIl610bMnN7Zuk"
    )
    val accessToken: String,

    /**
     * Refresh token
     */
    @Schema(
        description = "Refresh token",
        example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWFnb3JuIiwiaWF0IjoxNzU1ODc1MjAzLCJleHAiOjE3NTU4ODM4NDN9.3y8Jbs4Zz0tx7j0u7ZuIbppJASYt1kIl610bMnN7Zuk"
    )
    val refreshToken: String,

    /**
     * User role
     */
    @Schema(description = "User role", example = "PARTICIPANT")
    val role: String,

    /**
     * Access token expire lifespan (ms)
     */
    @Schema(description = "Access token lifespan (ms)", example = "86400000")
    val expiresIn: Long
)