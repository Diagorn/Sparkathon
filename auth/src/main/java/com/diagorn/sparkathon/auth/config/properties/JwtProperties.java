package com.diagorn.sparkathon.auth.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT properties
 *
 * @author diagorn
 */
@Component
@ConfigurationProperties(prefix = "application.jwt")
@Data
public class JwtProperties {
    /**
     * Access token expire time, seconds
     */
    private Integer accessExpireTimeSec;
    /**
     * Refresh token expire time, seconds
     */
    private Integer refreshExpireTimeSec;

    private String secret;
}
