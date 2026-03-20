package com.diagorn.sparkathon.gw.config;

import com.diagorn.sparkathon.common.exception.UnauthorizedException;
import com.diagorn.sparkathon.gw.client.AuthFeignClient;
import com.diagorn.sparkathon.gw.dto.auth.ValidateTokenRequest;
import com.diagorn.sparkathon.gw.dto.auth.ValidateTokenResponse;
import com.diagorn.sparkathon.gw.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * Authentication filter
 *
 * @author diagorn
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class JwtAuthFilter implements GlobalFilter, Ordered {

    private static final int TOKEN_START_INDEX = 7;
    private static final String TOKEN_PREFIX = "Bearer ";

    private static final String LOGIN_PATH = "api/v1/auth/login";
    private static final String USERS_PATH = "api/v1/users";

    private static final String USER_ID_CUSTOM_HEADER = "X-User-Id";
    private static final String USER_ROLE_CUSTOM_HEADER = "X-User-Role";

    private AuthFeignClient authClient;
    private final JwtService jwtService;
    private final ApplicationContext applicationContext;

    @Value("${spring.cloud.gateway.routes[0].id}")
    private String authServiceId;

    @Value("${jwt.secret}")
    private String jwtSecret;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (isAllowedPathWithoutAuthentication(exchange)) {
            return chain.filter(exchange);
        }

        try {
            String token = validateAuthHeader(exchange);
            Claims claims = getClaims(token);
            String userId = claims.getSubject();
            String role = claims.get("role", String.class);

            // Добавляем пользовательские заголовки для downstream сервисов
            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                    .header(USER_ID_CUSTOM_HEADER, userId)
                    .header(USER_ROLE_CUSTOM_HEADER, role)
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    private boolean isAllowedPathWithoutAuthentication(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        boolean isLoginPath = pathMatches(path, getLoginPath());
        boolean isUsersPath = pathMatches(path, getUsersPath()) && HttpMethod.POST.equals(exchange.getRequest().getMethod());
        return isLoginPath || isUsersPath;
    }

    private Claims getClaims(String token) {
        return jwtService.extractAllClaims(token, Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)));
    }

    private boolean pathMatches(String path, String pathPrefix) {
        return path != null && path.startsWith(pathPrefix);
    }

    private String getUsersPath() {
        return String.format("/%s/%s", authServiceId, USERS_PATH);
    }

    private String getLoginPath() {
        return String.format("/%s/%s", authServiceId, LOGIN_PATH);
    }

    private String validateAuthHeader(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
            throw new UnauthorizedException("Auth header is missing");
        }

        String token = authHeader.substring(TOKEN_START_INDEX);
        validateToken(token);
        return token;
    }

    private void validateToken(String token) {
        ResponseEntity<ValidateTokenResponse> validationResponse = getAuthClient().validate(new ValidateTokenRequest(token));
        if (!validationResponse.getStatusCode().is2xxSuccessful()
                || validationResponse.getBody() == null
                || !validationResponse.getBody().isValid()) {
            throw new UnauthorizedException("Bearer token is invalid");
        }
    }

    private AuthFeignClient getAuthClient() {
        if (this.authClient == null) {
            this.authClient = applicationContext.getBean(AuthFeignClient.class);
        }

        return this.authClient;
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
