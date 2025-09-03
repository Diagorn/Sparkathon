package com.diagorn.sparkathon.auth.service;

import com.diagorn.sparkathon.auth.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import static com.diagorn.sparkathon.auth.utils.JsonUtils.fromJson;
import static com.diagorn.sparkathon.auth.utils.JsonUtils.toJson;

/**
 * Service for working with refresh tokens
 *
 * @author diagorn
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {
    private static final String KEY_PREFIX = "refresh_token:";
    private final StringRedisTemplate redisTemplate;

    /**
     * Save new refresh token
     *
     * @param refreshToken - refresh token
     * @param userId       - user that has this token
     * @param ttl          - time to live
     */
    public void saveRefreshToken(String refreshToken, Long userId, Duration ttl) {
        var token = new RefreshToken(userId, false, Instant.now());
        redisTemplate.opsForValue().set(getTokenKey(refreshToken), toJson(token), ttl);
    }

    /**
     * Check if token is valid
     *
     * @param refreshToken - token
     * @return validity fact
     */
    public boolean isValid(String refreshToken) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(getTokenKey(refreshToken)))
                .map(jsonTokenInfo -> !fromJson(jsonTokenInfo, RefreshToken.class).isRevoked())
                .orElse(false);
    }

    /**
     * Revoke user's refresh token
     *
     * @param refreshToken - token
     * @throws IllegalStateException if token is absent
     */
    public void revoke(String refreshToken) throws IllegalStateException {
        var key = getTokenKey(refreshToken);
        Optional.ofNullable(redisTemplate.opsForValue().get(key))
                .ifPresentOrElse(jsonTokenInfo -> {
                    var token = fromJson(jsonTokenInfo, RefreshToken.class);
                    token.setRevoked(true);
                    redisTemplate.opsForValue().set(key, toJson(token));
                }, () -> {
                    throw new IllegalStateException(
                            String.format(
                                    "Token %s does not exist",
                                    refreshToken
                            )
                    );
                });
    }

    /**
     * Revoke all refresh tokens for user
     * @param userId - user id
     */
    public void revokeForUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID is null");
        }

        var keysPattern = String.format("%s*", KEY_PREFIX);
        var keys = redisTemplate.opsForValue().getOperations().keys(keysPattern);
        if (CollectionUtils.isEmpty(keys)) {
            throw new IllegalStateException("User is already logged out");
        }

        keys.forEach(key -> {
            var tokenJsonInfo = redisTemplate.opsForValue().get(key);
            var token = fromJson(tokenJsonInfo, RefreshToken.class);
            if (Objects.equals(token.getUserId(), userId)) {
                redisTemplate.delete(key);
            }
        });
    }

    private String hash(String string) {
        return DigestUtils.sha256Hex(string);
    }

    private String getTokenKey(String token) {
        var tokenHash = hash(token);
        return String.format("%s%s", KEY_PREFIX, tokenHash);
    }
}
