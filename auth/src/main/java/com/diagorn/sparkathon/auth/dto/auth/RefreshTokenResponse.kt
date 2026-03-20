package com.diagorn.sparkathon.auth.dto.auth

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Refresh token response
 *
 * @author diagorn
 */
class RefreshTokenResponse(
    /**
     * New access token
     */
    @Schema(
        description = "New access token",
        example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWFnb3JuIiwiaWF0IjoxNzU1ODc1MjAzLCJleHAiOjE3NTU4ODM4NDN9.3y8Jbs4Zz0tx7j0u7ZuIbppJASYt1kIl610bMnN7Zuk"
    )
    val accessToken: String,

    /**
     * New access token lifespan (ms)
     */
    @Schema(description = "New access token lifespan (ms)", example = "86400000")
    val expiresIn: Long
) 