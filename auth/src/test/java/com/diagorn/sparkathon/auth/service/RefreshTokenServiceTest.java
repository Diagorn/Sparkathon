package com.diagorn.sparkathon.auth.service;

import com.diagorn.sparkathon.auth.domain.RefreshToken;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    private static final String TEST_REFRESH_TOKEN = "testRefreshToken123";
    private static final Long TEST_USER_ID = 1L;
    private static final Duration TEST_TTL = Duration.ofHours(1);
    private static final String EXPECTED_TOKEN_HASH = "f3999431bcc4d15b77ff0aa7f54a97be61a20bb729081391b18769cd5f9b4509";
    private static final String EXPECTED_TOKEN_KEY = "refresh_token:" + EXPECTED_TOKEN_HASH;


    @Test
    void saveRefreshToken_ShouldSaveTokenWithCorrectKeyAndTtl() {
        try (MockedStatic<DigestUtils> digestUtilsMock = mockStatic(DigestUtils.class)) {
            // Given
            digestUtilsMock.when(() -> DigestUtils.sha256Hex(TEST_REFRESH_TOKEN))
                    .thenReturn(EXPECTED_TOKEN_HASH);
            given(redisTemplate.opsForValue()).willReturn(valueOperations);

            // When
            refreshTokenService.saveRefreshToken(TEST_REFRESH_TOKEN, TEST_USER_ID, TEST_TTL);

            // Then
            verify(valueOperations).set(eq(EXPECTED_TOKEN_KEY), anyString(), eq(TEST_TTL));
        }
    }

    @Test
    void isValid_WithValidToken_ShouldReturnTrue() {
        try (MockedStatic<DigestUtils> digestUtilsMock = mockStatic(DigestUtils.class)) {
            // Given
            digestUtilsMock.when(() -> DigestUtils.sha256Hex(TEST_REFRESH_TOKEN))
                    .thenReturn(EXPECTED_TOKEN_HASH);

            String tokenJson = "{\"userId\":1,\"revoked\":false,\"createdAt\":\"2023-01-01T00:00:00Z\"}";
            given(redisTemplate.opsForValue()).willReturn(valueOperations);
            given(valueOperations.get(EXPECTED_TOKEN_KEY)).willReturn(tokenJson);

            // When
            boolean isValid = refreshTokenService.isValid(TEST_REFRESH_TOKEN);

            // Then
            assertTrue(isValid);
            verify(valueOperations).get(EXPECTED_TOKEN_KEY);
        }
    }

    @Test
    void isValid_WithRevokedToken_ShouldReturnFalse() {
        try (MockedStatic<DigestUtils> digestUtilsMock = mockStatic(DigestUtils.class)) {
            // Given
            digestUtilsMock.when(() -> DigestUtils.sha256Hex(TEST_REFRESH_TOKEN))
                    .thenReturn(EXPECTED_TOKEN_HASH);

            String tokenJson = "{\"userId\":1,\"revoked\":true,\"createdAt\":\"2023-01-01T00:00:00Z\"}";
            given(redisTemplate.opsForValue()).willReturn(valueOperations);
            given(valueOperations.get(EXPECTED_TOKEN_KEY)).willReturn(tokenJson);

            // When
            boolean isValid = refreshTokenService.isValid(TEST_REFRESH_TOKEN);

            // Then
            assertFalse(isValid);
            verify(valueOperations).get(EXPECTED_TOKEN_KEY);
        }
    }

    @Test
    void isValid_WithNonExistentToken_ShouldReturnFalse() {
        try (MockedStatic<DigestUtils> digestUtilsMock = mockStatic(DigestUtils.class)) {
            // Given
            digestUtilsMock.when(() -> DigestUtils.sha256Hex(TEST_REFRESH_TOKEN))
                    .thenReturn(EXPECTED_TOKEN_HASH);
            given(redisTemplate.opsForValue()).willReturn(valueOperations);
            given(valueOperations.get(EXPECTED_TOKEN_KEY)).willReturn(null);

            // When
            boolean isValid = refreshTokenService.isValid(TEST_REFRESH_TOKEN);

            // Then
            assertFalse(isValid);
            verify(valueOperations).get(EXPECTED_TOKEN_KEY);
        }
    }

    @Test
    void revoke_WithExistingToken_ShouldMarkTokenAsRevoked() {
        try (MockedStatic<DigestUtils> digestUtilsMock = mockStatic(DigestUtils.class)) {
            // Given
            digestUtilsMock.when(() -> DigestUtils.sha256Hex(TEST_REFRESH_TOKEN))
                    .thenReturn(EXPECTED_TOKEN_HASH);

            String originalJson = "{\"userId\":1,\"revoked\":false,\"createdAt\":\"2023-01-01T00:00:00Z\"}";
            given(redisTemplate.opsForValue()).willReturn(valueOperations);
            given(valueOperations.get(EXPECTED_TOKEN_KEY)).willReturn(originalJson);

            // When
            refreshTokenService.revoke(TEST_REFRESH_TOKEN);

            // Then
            verify(valueOperations).get(EXPECTED_TOKEN_KEY);
            verify(valueOperations).set(eq(EXPECTED_TOKEN_KEY), contains("\"revoked\":true"));
        }
    }

    @Test
    void revoke_WithNonExistentToken_ShouldThrowException() {
        try (MockedStatic<DigestUtils> digestUtilsMock = mockStatic(DigestUtils.class)) {
            // Given
            digestUtilsMock.when(() -> DigestUtils.sha256Hex(TEST_REFRESH_TOKEN))
                    .thenReturn(EXPECTED_TOKEN_HASH);

            given(redisTemplate.opsForValue()).willReturn(valueOperations);
            given(valueOperations.get(EXPECTED_TOKEN_KEY)).willReturn(null);

            // When & Then
            IllegalStateException exception = assertThrows(IllegalStateException.class,
                    () -> refreshTokenService.revoke(TEST_REFRESH_TOKEN));

            assertEquals("Token testRefreshToken123 does not exist", exception.getMessage());
            verify(valueOperations).get(EXPECTED_TOKEN_KEY);
            verify(valueOperations, never()).set(anyString(), anyString());
        }
    }

    @Test
    void revokeForUser_WithValidUserId_ShouldDeleteUserTokens() {
        // Given
        Set<String> keys = new HashSet<>();
        keys.add("refresh_token:hash1");
        keys.add("refresh_token:hash2");
        keys.add("refresh_token:hash3");

        given(redisTemplate.keys("refresh_token:*")).willReturn(keys);

        String tokenJson1 = "{\"userId\":1,\"revoked\":false,\"createdAt\":\"2023-01-01T00:00:00Z\"}";
        String tokenJson2 = "{\"userId\":2,\"revoked\":false,\"createdAt\":\"2023-01-01T00:00:00Z\"}";
        String tokenJson3 = "{\"userId\":1,\"revoked\":false,\"createdAt\":\"2023-01-01T00:00:00Z\"}";

        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(valueOperations.getOperations()).willReturn(redisTemplate);
        given(valueOperations.get("refresh_token:hash1")).willReturn(tokenJson1);
        given(valueOperations.get("refresh_token:hash2")).willReturn(tokenJson2);
        given(valueOperations.get("refresh_token:hash3")).willReturn(tokenJson3);

        // When
        refreshTokenService.revokeForUser(TEST_USER_ID);

        // Then
        verify(redisTemplate).delete("refresh_token:hash1");
        verify(redisTemplate).delete("refresh_token:hash3");
        verify(redisTemplate, never()).delete("refresh_token:hash2");
    }

    @Test
    void revokeForUser_WithNoUserTokens_ShouldThrowException() {
        // Given
        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(valueOperations.getOperations()).willReturn(redisTemplate);
        given(redisTemplate.keys("refresh_token:*")).willReturn(Collections.emptySet());

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> refreshTokenService.revokeForUser(TEST_USER_ID));

        assertEquals("User is already logged out", exception.getMessage());
        verify(redisTemplate, never()).delete(anyString());
    }

    @Test
    void revokeForUser_WithNullUserId_ShouldThrowException() {
        // Given
        Long nullUserId = null;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> refreshTokenService.revokeForUser(nullUserId));

        assertEquals("User ID is null", exception.getMessage());
        verify(redisTemplate, never()).keys(anyString());
    }
}