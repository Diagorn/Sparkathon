package com.diagorn.sparkathon.auth.service

import com.diagorn.sparkathon.auth.config.properties.JwtProperties
import com.diagorn.sparkathon.auth.dto.auth.*
import com.diagorn.sparkathon.auth.repo.UserRepository
import com.diagorn.sparkathon.auth.utils.SECOND
import com.diagorn.sparkathon.common.exception.BadRequestException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.temporal.ChronoUnit

val logger: Logger = LoggerFactory.getLogger(AuthenticationService.Companion::class.java)

/**
 * Authentication, login, logout, revoke service
 *
 * @author diagorn
 */
@Service
class AuthenticationService(
    private val refreshTokenService: RefreshTokenService,
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProperties: JwtProperties,
) {
    private val REVOKE_SUCCESS_MSG = "Refresh token successfully revoked"
    private val REVOKE_ERROR_MSG = "Revoking refresh token failure: refresh token not found"
    private val LOGOUT_SUCCESS_MSG = "Logout success"

    /**
     * Attempt to log in
     * @param request - login request
     */
    fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.getByLogin(request.login)

        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            throw BadRequestException("Username or password is wrong")
        }

        val accessToken = jwtService.generateAccessToken(user)
        val refreshToken = jwtService.generateRefreshToken(user)

        refreshTokenService.saveRefreshToken(
            refreshToken,
            user.id,
            Duration.of(jwtProperties.refreshExpireTimeSec.toLong(), ChronoUnit.SECONDS)
        )

        return LoginResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresIn = SECOND * jwtProperties.accessExpireTimeSec,
            role = user.role.name
        )
    }

    /**
     * Validate token
     * @param request - validation request
     */
    fun validate(request: ValidateTokenRequest): ValidateTokenResponse {
        val login = jwtService.getUsernameFromToken(request.token)
        val user = userRepository.getByLogin(login)

        val isValid = jwtService.validateToken(request.token, user)
        val expiresAt = jwtService.getExpirationDateFromToken(request.token)

        return ValidateTokenResponse(
            isValid = isValid,
            expiresIn = expiresAt.time,
            user.role.name
        )
    }

    /**
     * Refresh access token
     * @param request - refreshment request
     */
    fun refresh(request: RefreshTokenRequest): RefreshTokenResponse {
        val login = jwtService.getUsernameFromToken(request.refreshToken)
        val user = userRepository.getByLogin(login)
        val valid = try {
            jwtService.validateToken(request.refreshToken, user)
        } catch (e: Exception) {
            val errorMsg = "Could not validate token ${request.refreshToken}"
            throw IllegalArgumentException(errorMsg, e)
        }

        if (!valid) {
            val errorMsg = "Token ${request.refreshToken} is invalid"
            throw IllegalArgumentException(errorMsg)
        }

        val accessToken = jwtService.generateAccessToken(user)
        return RefreshTokenResponse(
            accessToken = accessToken,
            expiresIn = SECOND * jwtProperties.accessExpireTimeSec
        )
    }

    /**
     * Revoke refresh token
     * @param request - revoke token request
     */
    fun revoke(request: RevokeTokenRequest): RevokeTokenResponse {
        val (message, revoked) = try {
            refreshTokenService.revoke(request.refreshToken)
            REVOKE_SUCCESS_MSG to true
        } catch (e: RuntimeException) {
            REVOKE_ERROR_MSG to false
        }

        return RevokeTokenResponse(
            revoked = revoked,
            message = message
        )
    }

    /**
     * Logout
     * @param request - logout request
     */
    fun logout(request: LogoutRequest): LogoutResponse {
        val login = jwtService.getUsernameFromToken(request.refreshToken)
        val user = userRepository.getByLogin(login)

        val (message, success) = try {
            refreshTokenService.revokeForUser(user.id)
            LOGOUT_SUCCESS_MSG to true
        } catch (e: RuntimeException) {
            logger.error("Could not logout", e)
            e.message to false
        }

        return LogoutResponse(
            success = success,
            message = message ?: COULD_NOT_LOGOUT
        )
    }

    companion object {
        const val COULD_NOT_LOGOUT = "Could not logout. Please contact support"
    }
}