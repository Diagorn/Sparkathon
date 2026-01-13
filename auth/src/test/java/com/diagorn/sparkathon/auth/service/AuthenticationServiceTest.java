package com.diagorn.sparkathon.auth.service;

import com.diagorn.sparkathon.auth.config.properties.JwtProperties;
import com.diagorn.sparkathon.auth.domain.Role;
import com.diagorn.sparkathon.auth.domain.User;
import com.diagorn.sparkathon.auth.dto.auth.*;
import com.diagorn.sparkathon.common.exception.BadRequestException;
import com.diagorn.sparkathon.common.exception.NotFoundException;
import com.diagorn.sparkathon.auth.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProperties jwtProperties;

    @InjectMocks
    private AuthenticationService authenticationService;

    private static final String USER_LOGIN = "testuser";
    private static final String USER_PASSWORD = "password123";
    private static final String ENCODED_PASSWORD = "encodedPassword123";
    private static final Long USER_ID = 1L;
    private static final String ROLE_NAME = "ROLE_USER";
    private static final String ACCESS_TOKEN = "accessToken123";
    private static final String REFRESH_TOKEN = "refreshToken456";
    private static final int ACCESS_EXPIRE_TIME = 3600;
    private static final int REFRESH_EXPIRE_TIME = 86400;

    @Test
    void login_WithValidCredentials_ShouldReturnLoginResponse() {
        // Given
        LoginRequest request = new LoginRequest(USER_LOGIN, USER_PASSWORD);
        User user = createTestUser();
        Date expirationDate = new Date(System.currentTimeMillis() + ACCESS_EXPIRE_TIME * 1000);

        given(userRepository.findByLogin(USER_LOGIN)).willReturn(user);
        given(passwordEncoder.matches(USER_PASSWORD, ENCODED_PASSWORD)).willReturn(true);
        given(jwtService.generateAccessToken(user)).willReturn(ACCESS_TOKEN);
        given(jwtService.generateRefreshToken(user)).willReturn(REFRESH_TOKEN);
        given(jwtProperties.getRefreshExpireTimeSec()).willReturn(REFRESH_EXPIRE_TIME);
        given(jwtProperties.getAccessExpireTimeSec()).willReturn(ACCESS_EXPIRE_TIME);

        // When
        LoginResponse response = authenticationService.login(request);

        // Then
        assertNotNull(response);
        assertEquals(ACCESS_TOKEN, response.getAccessToken());
        assertEquals(REFRESH_TOKEN, response.getRefreshToken());
        assertEquals(ACCESS_EXPIRE_TIME * 1000, response.getExpiresIn());
        assertEquals(ROLE_NAME, response.getRole());

        verify(userRepository).findByLogin(USER_LOGIN);
        verify(passwordEncoder).matches(USER_PASSWORD, ENCODED_PASSWORD);
        verify(jwtService).generateAccessToken(user);
        verify(jwtService).generateRefreshToken(user);
        verify(refreshTokenService).saveRefreshToken(
                eq(REFRESH_TOKEN),
                eq(USER_ID),
                eq(Duration.of(REFRESH_EXPIRE_TIME, java.time.temporal.ChronoUnit.SECONDS))
        );
    }

    @Test
    void login_WithNonExistingUser_ShouldThrowNotFoundException() {
        // Given
        String login = "nonexisting";
        LoginRequest request = new LoginRequest(login, USER_PASSWORD);
        given(userRepository.findByLogin("nonexisting")).willReturn(null);

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> authenticationService.login(request));

        assertEquals(String.format("User with login %s not found", login), exception.getMessage());
        verify(userRepository).findByLogin("nonexisting");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void login_WithWrongPassword_ShouldThrowBadRequestException() {
        // Given
        LoginRequest request = new LoginRequest(USER_LOGIN, "wrongpassword");
        User user = createTestUser();

        given(userRepository.findByLogin(USER_LOGIN)).willReturn(user);
        given(passwordEncoder.matches("wrongpassword", ENCODED_PASSWORD)).willReturn(false);

        // When & Then
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> authenticationService.login(request));

        assertEquals("Username or password is wrong", exception.getMessage());
        verify(userRepository).findByLogin(USER_LOGIN);
        verify(passwordEncoder).matches("wrongpassword", ENCODED_PASSWORD);
        verify(jwtService, never()).generateAccessToken(any());
    }

    @Test
    void validate_WithValidToken_ShouldReturnValidResponse() {
        // Given
        ValidateTokenRequest request = new ValidateTokenRequest(ACCESS_TOKEN);
        User user = createTestUser();
        Date expirationDate = new Date(System.currentTimeMillis() + ACCESS_EXPIRE_TIME * 1000);

        given(jwtService.getUsernameFromToken(ACCESS_TOKEN)).willReturn(USER_LOGIN);
        given(userRepository.findByLogin(USER_LOGIN)).willReturn(user);
        given(jwtService.validateToken(ACCESS_TOKEN, user)).willReturn(true);
        given(jwtService.getExpirationDateFromToken(ACCESS_TOKEN)).willReturn(expirationDate);

        // When
        ValidateTokenResponse response = authenticationService.validate(request);

        // Then
        assertNotNull(response);
        assertTrue(response.isValid());
        assertEquals(expirationDate.getTime(), response.getExpiresIn());
        assertEquals(ROLE_NAME, response.getRole());

        verify(jwtService).getUsernameFromToken(ACCESS_TOKEN);
        verify(userRepository).findByLogin(USER_LOGIN);
        verify(jwtService).validateToken(ACCESS_TOKEN, user);
        verify(jwtService).getExpirationDateFromToken(ACCESS_TOKEN);
    }

    @Test
    void validate_WithInvalidToken_ShouldReturnInvalidResponse() {
        // Given
        ValidateTokenRequest request = new ValidateTokenRequest("invalidToken");
        User user = createTestUser();
        Date expirationDate = new Date(System.currentTimeMillis() - 1000); // Expired

        given(jwtService.getUsernameFromToken("invalidToken")).willReturn(USER_LOGIN);
        given(userRepository.findByLogin(USER_LOGIN)).willReturn(user);
        given(jwtService.validateToken("invalidToken", user)).willReturn(false);
        given(jwtService.getExpirationDateFromToken("invalidToken")).willReturn(expirationDate);

        // When
        ValidateTokenResponse response = authenticationService.validate(request);

        // Then
        assertNotNull(response);
        assertFalse(response.isValid());
        assertEquals(expirationDate.getTime(), response.getExpiresIn());
        assertEquals(ROLE_NAME, response.getRole());

        verify(jwtService).getUsernameFromToken("invalidToken");
        verify(userRepository).findByLogin(USER_LOGIN);
        verify(jwtService).validateToken("invalidToken", user);
        verify(jwtService).getExpirationDateFromToken("invalidToken");
    }

    @Test
    void refresh_WithValidRefreshToken_ShouldReturnNewAccessToken() {
        // Given
        RefreshTokenRequest request = new RefreshTokenRequest(REFRESH_TOKEN);
        User user = createTestUser();

        given(jwtService.getUsernameFromToken(REFRESH_TOKEN)).willReturn(USER_LOGIN);
        given(userRepository.findByLogin(USER_LOGIN)).willReturn(user);
        given(jwtService.validateToken(REFRESH_TOKEN, user)).willReturn(true);
        given(jwtService.generateAccessToken(user)).willReturn("newAccessToken");
        given(jwtProperties.getAccessExpireTimeSec()).willReturn(ACCESS_EXPIRE_TIME);

        // When
        RefreshTokenResponse response = authenticationService.refresh(request);

        // Then
        assertNotNull(response);
        assertEquals("newAccessToken", response.getAccessToken());
        assertEquals(ACCESS_EXPIRE_TIME * 1000, response.getExpiresIn());

        verify(jwtService).getUsernameFromToken(REFRESH_TOKEN);
        verify(userRepository).findByLogin(USER_LOGIN);
        verify(jwtService).validateToken(REFRESH_TOKEN, user);
        verify(jwtService).generateAccessToken(user);
    }

    @Test
    void refresh_WithInvalidRefreshToken_ShouldThrowIllegalArgumentException() {
        // Given
        RefreshTokenRequest request = new RefreshTokenRequest("invalidRefreshToken");
        User user = createTestUser();

        given(jwtService.getUsernameFromToken("invalidRefreshToken")).willReturn(USER_LOGIN);
        given(userRepository.findByLogin(USER_LOGIN)).willReturn(user);
        given(jwtService.validateToken("invalidRefreshToken", user)).willReturn(false);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> authenticationService.refresh(request));

        assertTrue(exception.getMessage().contains("Token invalidRefreshToken is invalid"));
        verify(jwtService).getUsernameFromToken("invalidRefreshToken");
        verify(userRepository).findByLogin(USER_LOGIN);
        verify(jwtService).validateToken("invalidRefreshToken", user);
        verify(jwtService, never()).generateAccessToken(any());
    }

    @Test
    void revoke_WithExistingToken_ShouldReturnSuccessResponse() {
        // Given
        RevokeTokenRequest request = new RevokeTokenRequest(REFRESH_TOKEN);

        // When
        RevokeTokenResponse response = authenticationService.revoke(request);

        // Then
        assertNotNull(response);
        assertTrue(response.isRevoked());
        assertEquals("Refresh token successfully revoked", response.getMessage());
        verify(refreshTokenService).revoke(REFRESH_TOKEN);
    }

    @Test
    void revoke_WithNonExistingToken_ShouldReturnFailureResponse() {
        // Given
        RevokeTokenRequest request = new RevokeTokenRequest("nonExistingToken");
        doThrow(new IllegalStateException()).when(refreshTokenService).revoke("nonExistingToken");

        // When
        RevokeTokenResponse response = authenticationService.revoke(request);

        // Then
        assertNotNull(response);
        assertFalse(response.isRevoked());
        assertEquals("Revoking refresh token failure: refresh token not found", response.getMessage());
        verify(refreshTokenService).revoke("nonExistingToken");
    }

    @Test
    void logout_WithValidUser_ShouldReturnSuccessResponse() {
        // Given
        LogoutRequest request = new LogoutRequest(REFRESH_TOKEN);
        User user = createTestUser();

        given(jwtService.getUsernameFromToken(REFRESH_TOKEN)).willReturn(USER_LOGIN);
        given(userRepository.findByLogin(USER_LOGIN)).willReturn(user);

        // When
        LogoutResponse response = authenticationService.logout(request);

        // Then
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("Logout success", response.getMessage());
        verify(jwtService).getUsernameFromToken(REFRESH_TOKEN);
        verify(userRepository).findByLogin(USER_LOGIN);
        verify(refreshTokenService).revokeForUser(USER_ID);
    }

    @Test
    void logout_WithRevokeFailure_ShouldReturnFailureResponse() {
        // Given
        LogoutRequest request = new LogoutRequest(REFRESH_TOKEN);
        User user = createTestUser();

        given(jwtService.getUsernameFromToken(REFRESH_TOKEN)).willReturn(USER_LOGIN);
        given(userRepository.findByLogin(USER_LOGIN)).willReturn(user);
        doThrow(new IllegalStateException("User is already logged out")).when(refreshTokenService).revokeForUser(USER_ID);

        // When
        LogoutResponse response = authenticationService.logout(request);

        // Then
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals("User is already logged out", response.getMessage());
        verify(jwtService).getUsernameFromToken(REFRESH_TOKEN);
        verify(userRepository).findByLogin(USER_LOGIN);
        verify(refreshTokenService).revokeForUser(USER_ID);
    }

    private User createTestUser() {
        Role role = new Role();
        role.setName(ROLE_NAME);

        User user = new User();
        user.setId(USER_ID);
        user.setLogin(USER_LOGIN);
        user.setPassword(ENCODED_PASSWORD);
        user.setRole(role);

        return user;
    }
}