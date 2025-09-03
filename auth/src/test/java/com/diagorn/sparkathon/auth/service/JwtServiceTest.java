package com.diagorn.sparkathon.auth.service;

import com.diagorn.sparkathon.auth.config.properties.JwtProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    private JwtProperties jwtProperties;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtService jwtService;

    private static final String SECRET = "mySuperSecretKeyThatIsLongEnoughForHS512Algorithm";
    private static final String USERNAME = "testuser";
    private static final String ROLE = "PARTICIPANT";
    private static final Integer ACCESS_EXPIRE_TIME = 18000; // 5 hours
    private static final Integer REFRESH_EXPIRE_TIME = 86400; // 24 hours

    @BeforeEach
    void setUp() {
        when(jwtProperties.getSecret()).thenReturn(SECRET);
    }

    @Test
    void generateAccessToken_ShouldReturnValidToken() {
        // Given

        // When
        String token = jwtService.generateAccessToken(userDetails);

        // Then
        assertNotNull(token);
        assertEquals(3, token.split("\\.").length); // JWT has 3 parts
    }

    @Test
    void generateRefreshToken_ShouldReturnValidToken() {
        // Given

        // When
        String token = jwtService.generateRefreshToken(userDetails);

        // Then
        assertNotNull(token);
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    void getUsernameFromToken_ShouldReturnCorrectUsername() {
        // Given
        when(userDetails.getUsername()).thenReturn(USERNAME);
        when(userDetails.getAuthorities()).thenAnswer(invocation -> {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(() -> ROLE);
            return authorities;
        });
        when(jwtProperties.getAccessExpireTimeSec()).thenReturn(ACCESS_EXPIRE_TIME);

        String token = jwtService.generateAccessToken(userDetails);

        // When
        String username = jwtService.getUsernameFromToken(token);

        // Then
        assertEquals(USERNAME, username);
    }

    @Test
    void getExpirationDateFromToken_ShouldReturnFutureDate() {
        // Given
        when(jwtProperties.getAccessExpireTimeSec()).thenReturn(ACCESS_EXPIRE_TIME);
        String token = jwtService.generateAccessToken(userDetails);

        // When
        Date expiration = jwtService.getExpirationDateFromToken(token);

        // Then
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void validateToken_WithValidToken_ShouldReturnTrue() {
        // Given
        when(userDetails.getUsername()).thenReturn(USERNAME);
        when(userDetails.getAuthorities()).thenAnswer(invocation -> {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(() -> ROLE);
            return authorities;
        });
        when(jwtProperties.getAccessExpireTimeSec()).thenReturn(ACCESS_EXPIRE_TIME);

        String token = jwtService.generateAccessToken(userDetails);

        // When
        Boolean isValid = jwtService.validateToken(token, userDetails);

        // Then
        assertTrue(isValid);
    }

    @Test
    void validateToken_WithInvalidUsername_ShouldReturnFalse() {
        // Given
        String token = jwtService.generateAccessToken(userDetails);
        UserDetails differentUser = mock(UserDetails.class);

        // When
        Boolean isValid = jwtService.validateToken(token, differentUser);

        // Then
        assertFalse(isValid);
    }

    @Test
    void validateToken_WithExpiredToken_ShouldReturnFalse() {
        // Given
        // Create a token with very short expiration
        when(jwtProperties.getAccessExpireTimeSec()).thenReturn(0);
        String token = jwtService.generateAccessToken(userDetails);

        // Wait a bit to ensure token expires
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // When
        Boolean isValid = jwtService.validateToken(token, userDetails);

        // Then
        assertFalse(isValid);
    }

    @Test
    void validateToken_WithMalformedToken_ShouldThrowException() {
        // Given
        String malformedToken = "malformed.token.here";

        // When
        // Then
        assertFalse(jwtService.validateToken(malformedToken, userDetails));
    }

    @Test
    void validateToken_WithWrongSecret_ShouldThrowException() {
        // Given
        String token = jwtService.generateAccessToken(userDetails);

        // Change secret to wrong one
        when(jwtProperties.getSecret()).thenReturn("wrongSecret");

        // When
        // Then
        assertFalse(jwtService.validateToken(token, userDetails));
    }
}