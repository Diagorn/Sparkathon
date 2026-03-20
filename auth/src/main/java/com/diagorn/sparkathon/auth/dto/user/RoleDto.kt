package com.diagorn.sparkathon.auth.dto.user

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Role DTO
 *
 * @author diagorn
 */
class RoleDto (
    /**
     * Role ID
     */
    @Schema(description = "Role ID", example = "1")
    val id: Long,

    /**
     * Role name
     */
    @Schema(description = "Role name", example = "PARTICIPANT")
    val name: String
)