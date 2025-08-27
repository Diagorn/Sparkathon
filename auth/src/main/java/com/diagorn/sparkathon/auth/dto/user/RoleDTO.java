package com.diagorn.sparkathon.auth.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Role DTO
 *
 * @author diagorn
 */
@Data
public class RoleDTO {
    /**
     * Role ID
     */
    @Schema(description = "Role ID", example = "1")
    private Long id;
    /**
     * Role name
     */
    @Schema(description = "Role name", example = "PARTICIPANT")
    private String name;
}
