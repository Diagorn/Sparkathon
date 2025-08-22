package com.diagorn.sparkathon.gw.config;

import com.diagorn.sparkathon.gw.client.AuthFeignClient;
import com.diagorn.sparkathon.gw.dto.auth.ValidateTokenRequest;
import com.diagorn.sparkathon.gw.dto.auth.ValidateTokenResponse;
import com.diagorn.sparkathon.gw.service.JwtService;
import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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
public class JwtAuthFilter implements GlobalFilter {

    private final AuthFeignClient authClient;

    private final JwtService jwtService;

    @Value("${spring.cloud.gateway.routes[0].id}")
    private String authServiceId;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        String authServicePrefix = String.format("/%s", authServiceId);

        if (path.startsWith("/api/v1/auth/login")
                || path.startsWith("/api/v1/users") && HttpMethod.POST.equals(exchange.getRequest().getMethod())) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        try {
            ResponseEntity<ValidateTokenResponse> validationResponse = authClient.validate(new ValidateTokenRequest(token));
            if (!validationResponse.getStatusCode().is2xxSuccessful() || !validationResponse.getBody().isValid()) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            Claims claims = jwtService.extractAllClaims(token, Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)));
            String userId = claims.getSubject();
            String role = claims.get("role", String.class);

            // Добавляем пользовательские заголовки для downstream сервисов
            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                    .header("X-User-Id", userId)
                    .header("X-User-Role", role)
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}
