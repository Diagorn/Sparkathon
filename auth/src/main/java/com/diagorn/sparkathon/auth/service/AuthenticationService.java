package com.diagorn.sparkathon.auth.service;

import com.diagorn.sparkathon.auth.config.properties.JwtProperties;
import com.diagorn.sparkathon.auth.domain.User;
import com.diagorn.sparkathon.auth.dto.auth.*;
import com.diagorn.sparkathon.auth.exception.BadRequestException;
import com.diagorn.sparkathon.auth.exception.NotFoundException;
import com.diagorn.sparkathon.auth.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * Authentication, login, logout, revoke service
 *
 * @author diagorn
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final String REVOKE_SUCCESS_MSG = "Refresh token successfully revoked";
    private final String REVOKE_ERROR_MSG = "Revoking refresh token failure: refresh token not found";
    private final String LOGOUT_SUCCESS_MSG = "Logout success";

    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;

    /**
     * Attempt to log in
     * @param request - login request
     */
    public LoginResponse login(LoginRequest request) {
        var user = getUser(request.getLogin());
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Username or password is wrong");
        }

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        refreshTokenService.saveRefreshToken(
                refreshToken,
                user.getId(),
                Duration.of(jwtProperties.getRefreshExpireTimeSec(), ChronoUnit.SECONDS)
        );

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(1_000L * jwtProperties.getAccessExpireTimeSec())
                .role(user.getRole().getName())
                .build();
    }

    /**
     * Validate token
     * @param request - validation request
     */
    public ValidateTokenResponse validate(ValidateTokenRequest request) {
        var login = jwtService.getUsernameFromToken(request.getToken());
        var user = getUser(login);

        var isValid = jwtService.validateToken(request.getToken(), user);
        var expiresAt = jwtService.getExpirationDateFromToken(request.getToken());

        return ValidateTokenResponse.builder()
                .isValid(isValid)
                .expiresIn(expiresAt.getTime())
                .role(user.getRole().getName())
                .build();
    }

    /**
     * Refresh access token
     * @param request - refreshment request
     */
    public RefreshTokenResponse refresh(RefreshTokenRequest request) {
        var user = getUser(jwtService.getUsernameFromToken(request.getRefreshToken()));
        boolean valid;
        try {
            valid = jwtService.validateToken(request.getRefreshToken(), user);
        } catch (Exception e) {
            String errorMsg = String.format("Could not validate token %s", request.getRefreshToken());
            log.error("Could not validate token {}", request.getRefreshToken());
            throw new IllegalArgumentException(errorMsg, e);
        }

        if (!valid) {
            log.error("Token {} is invalid", request.getRefreshToken());
            throw new IllegalArgumentException(
                    String.format("Token %s is invalid", request.getRefreshToken())
            );
        }

        var accessToken = jwtService.generateAccessToken(user);
        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .expiresIn(1_000L * jwtProperties.getAccessExpireTimeSec())
                .build();
    }

    /**
     * Revoke refresh token
     * @param request - revoke token request
     */
    public RevokeTokenResponse revoke(RevokeTokenRequest request) {
        boolean revoked;
        String message;
        try {
            refreshTokenService.revoke(request.getRefreshToken());
            message = REVOKE_SUCCESS_MSG;
            revoked = true;
        } catch (IllegalStateException e) {
            message = REVOKE_ERROR_MSG;
            revoked = false;
        }

        return RevokeTokenResponse.builder()
                .revoked(revoked)
                .message(message)
                .build();
    }

    /**
     * Logout
     * @param request - logout request
     */
    public LogoutResponse logout(LogoutRequest request) {
        var user = getUser(jwtService.getUsernameFromToken(request.getRefreshToken()));

        boolean success;
        String message;
        try {
            refreshTokenService.revokeForUser(user.getId());
            message = LOGOUT_SUCCESS_MSG;
            success = true;
        } catch (RuntimeException e) {
            message = e.getMessage();
            success = false;
        }

        return LogoutResponse.builder()
                .success(success)
                .message(message)
                .build();
    }

    private User getUser(String login) {
        return Optional.ofNullable(userRepository.findByLogin(login))
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with login %s not found", login)
                ));
    }
}
