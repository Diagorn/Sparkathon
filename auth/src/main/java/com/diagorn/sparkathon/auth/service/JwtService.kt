package com.diagorn.sparkathon.auth.service

import com.diagorn.sparkathon.auth.config.properties.JwtProperties
import com.diagorn.sparkathon.auth.utils.SECOND
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.function.Function

/**
 * JWT management service
 *
 * @author diagorn
 */
@Service
class JwtService(
    private val jwtProperties: JwtProperties
) {

    /**
     * Generate access token for user
     *
     * @param userDetails - user
     * @return access token
     */
    fun generateAccessToken(userDetails: UserDetails): String {
        val claims: Map<String, Any?> = emptyMap()
        return doGenerateToken(
            claims,
            userDetails.username,
            getRole(userDetails),
            jwtProperties.accessExpireTimeSec.toLong()
        )
    }

    /**
     * Generate refresh token for user
     *
     * @param userDetails - user
     * @return refresh token
     */
    fun generateRefreshToken(userDetails: UserDetails): String {
        val claims: Map<String, Any?> = HashMap()
        return doGenerateToken(
            claims,
            userDetails.username,
            getRole(userDetails),
            jwtProperties.refreshExpireTimeSec.toLong()
        )
    }

    /**
     * Validate token
     *
     * @param token       - token to validate
     * @param userDetails - user
     * @return validity fact
     */
    fun validateToken(token: String?, userDetails: UserDetails): Boolean {
        val username = try {
            getUsernameFromToken(token)
        } catch (e: Exception) {
            null
        }

        return username?.isNotEmpty() ?: false
                && username == userDetails.username
                && !isTokenExpired(token)
    }

    /**
     * Get token expiration date
     *
     * @param token - token
     * @return expiration date
     */
    fun getExpirationDateFromToken(token: String?): Date = getClaimFromToken(token) { obj: Claims -> obj.expiration }

    /**
     * Get username from token
     *
     * @param token - token
     * @return username
     */
    fun getUsernameFromToken(token: String?): String = getClaimFromToken(token) { obj: Claims -> obj.subject }

    private fun isTokenExpired(token: String?): Boolean {
        val expiration = try {
            getExpirationDateFromToken(token)
        } catch (e: Exception) {
            null
        }
        return expiration == null || expiration.before(Date())
    }

    private fun <T> getClaimFromToken(token: String?, claimResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimResolver.apply(claims)
    }

    private fun getAllClaimsFromToken(token: String?): Claims = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray(StandardCharsets.UTF_8)))
            .build()
            .parseClaimsJws(token)
            .body

    private fun doGenerateToken(claims: Map<String, Any?>, subject: String, role: String?, expireSec: Long): String =
        Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .claim(ROLE, role)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expireSec * SECOND))
            .signWith(Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray(StandardCharsets.UTF_8)))
            .compact()

    private fun getRole(userDetails: UserDetails): String? = userDetails.authorities.firstOrNull()?.authority

    companion object {
        const val ROLE = "role"
    }
}