package com.diagorn.sparkathon.auth.dto.auth

import io.swagger.v3.oas.annotations.media.Schema

class RefreshTokenRequest(
    /**
     * Refresh token
     */
    @Schema(
        description = "Refresh token",
        example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWFnb3JuIiwiaWF0IjoxNzU1ODc1MjAzLCJleHAiOjE3NTU4ODM4NDN9.3y8Jbs4Zz0tx7j0u7ZuIbppJASYt1kIl610bMnN7Zuk"
    )
    var refreshToken: String
) {
}