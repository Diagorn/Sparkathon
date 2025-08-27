package com.diagorn.sparkathon.auth.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

/**
 * Token validation request
 *
 * @author diagorn
 */
@Data
@ToString
public class ValidateTokenRequest {
    /**
     * Token to validate
     */
    @Schema(description = "Token to validate", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWFnb3JuIiwiaWF0IjoxNzU1ODc1MjAzLCJleHAiOjE3NTU4ODM4NDN9.3y8Jbs4Zz0tx7j0u7ZuIbppJASYt1kIl610bMnN7Zuk")
    private String token;
}
