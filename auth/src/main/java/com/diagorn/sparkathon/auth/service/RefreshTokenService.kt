package com.diagorn.sparkathon.auth.service

import com.diagorn.sparkathon.auth.domain.RefreshToken
import com.diagorn.sparkathon.auth.utils.JsonUtils
import com.diagorn.sparkathon.auth.utils.Messages
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant

/**
 * Service for working with refresh tokens
 *
 * @author diagorn
 */
@Service
class RefreshTokenService(
    private val redisTemplate: StringRedisTemplate
) {

    /**
     * Save new refresh token
     *
     * @param refreshToken - refresh token
     * @param userId       - user that has this token
     * @param ttl          - time to live
     */
    fun saveRefreshToken(refreshToken: String, userId: Long, ttl: Duration) {
        val token = RefreshToken(userId, false, Instant.now())
        redisTemplate.opsForValue()[getTokenKey(refreshToken), JsonUtils.toJson(token)] = ttl
    }

    /**
     * Check if token is valid
     *
     * @param refreshToken - token
     * @return validity fact
     */
    fun isValid(refreshToken: String): Boolean {
        val tokenKey = getTokenKey(refreshToken)
        val jsonTokenInfo = redisTemplate.opsForValue()[tokenKey]
        val tokenInfo = JsonUtils.fromJson(jsonTokenInfo, RefreshToken::class.java)
        return !tokenInfo.revoked
    }

    /**
     * Revoke user's refresh token
     *
     * @param refreshToken - token
     * @throws IllegalStateException if token is absent
     */
    fun revoke(refreshToken: String) {
        val key = getTokenKey(refreshToken)
        val jsonTokenInfo = redisTemplate.opsForValue()[key]
            ?: throw IllegalStateException(Messages.tokenDoesNotExist(refreshToken))

        val token = JsonUtils.fromJson(
            jsonTokenInfo,
            RefreshToken::class.java
        )
        token.revoked = true
        redisTemplate.opsForValue()[key] = JsonUtils.toJson(token)
    }

    /**
     * Revoke all refresh tokens for user
     * @param id - user id
     */
    fun revokeForUser(id: Long?) {
        val userId = id ?: throw IllegalArgumentException(Messages.nullUserId())

        val keysPattern = "$KEY_PREFIX*"
        val keys = redisTemplate.opsForValue().operations.keys(keysPattern)

        if (keys.isNullOrEmpty()) {
            throw IllegalStateException(Messages.userLoggedOut())
        }

        keys.forEach {
            val tokenJsonInfo = redisTemplate.opsForValue()[it]
            val token = JsonUtils.fromJson(tokenJsonInfo, RefreshToken::class.java)
            if (token.userId == userId) {
                redisTemplate.delete(it)
            }
        }
    }

    private fun hash(string: String) = DigestUtils.sha256Hex(string)

    private fun getTokenKey(token: String) = KEY_PREFIX + hash(token)

    companion object {
        private const val KEY_PREFIX = "refresh_token:"
    }
}