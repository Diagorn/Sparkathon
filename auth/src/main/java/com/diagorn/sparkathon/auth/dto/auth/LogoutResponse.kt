package com.diagorn.sparkathon.auth.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Logout response
 *
 * @author diagorn
 */
@Data
@Builder
@ToString
public class LogoutResponse {
    /**
     * Logout succeeded
     */
    @Schema(description = "Logout succeeded", example = "true")
    private boolean success;
    /**
     * Comments
     */
    @Schema(description = "Comments", example = "Success")
    private String message;
}
