package com.diagorn.sparkathon.auth.web

import com.diagorn.sparkathon.auth.dto.auth.*
import com.diagorn.sparkathon.auth.service.AuthenticationService
import com.diagorn.sparkathon.common.AbstractWebErrorHandler
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Authentication REST endpoint
 *
 * @author diagorn
 */
@RestController
@RequestMapping("/api/v1/auth/")
@Tag(name = "Auth", description = "Security management API")
class AuthController(
    private val authenticationService: AuthenticationService
) : AbstractWebErrorHandler() {
    
    @PostMapping("/login")
    @Operation(summary = "Log in")
    fun login(
        @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody request: @Valid LoginRequest
    ): ResponseEntity<LoginResponse> {
        val response = authenticationService.login(request)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/logout")
    @Operation(summary = "Log out")
    fun logout(
        @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody request: @Valid LogoutRequest
    ): ResponseEntity<LogoutResponse> {
        val response = authenticationService.logout(request)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/refresh")
    @Operation(summary = "Refresh access token")
    fun refresh(
        @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody request: @Valid RefreshTokenRequest
    ): ResponseEntity<RefreshTokenResponse> {
        val response = authenticationService.refresh(request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/revoke")
    @Operation(summary = "Revoke specific refresh token")
    fun revoke(
        @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody request: @Valid RevokeTokenRequest
    ): ResponseEntity<RevokeTokenResponse> {
        val response = authenticationService.revoke(request)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/validate")
    @Operation(summary = "Validate token")
    fun validate(
        @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody request: @Valid ValidateTokenRequest
    ): ResponseEntity<ValidateTokenResponse> {
        val response = authenticationService.validate(request)
        return ResponseEntity.ok(response)
    }
}