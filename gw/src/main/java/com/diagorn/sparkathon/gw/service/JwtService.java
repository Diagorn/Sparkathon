package com.diagorn.sparkathon.gw.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.security.Key;

/**
 * JWT helper service
 *
 * @author diagorn
 */
@Service
public class JwtService {

    /**
     * Get claims from token
     *
     * @param token     - access token
     * @param secretKey - JWT key
     * @return claims
     */
    public Claims extractAllClaims(String token, Key secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
