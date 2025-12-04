package com.diagorn.sparkathon.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import static com.diagorn.sparkathon.event.utils.SwaggerConstants.HACKATHON_STATUS_DTO_EXAMPLE;

/**
 * Hackathon status DTO
 *
 * @param id          - status id
 * @param code        - status code
 * @param name        - status name
 * @param description - status description
 * @author diagorn
 */
@Schema(description = "Hackathon status DTO", example = HACKATHON_STATUS_DTO_EXAMPLE)
public record HackathonStatusDto(
        @Schema(description = "Status id", example = "1")
        Long id,
        @Schema(description = "Status code", example = "DRAFT")
        String code,
        @Schema(description = "Status name", example = "Draft")
        String name,
        @Schema(description = "Status description", example = "Hackathon in development")
        String description
) {
}
