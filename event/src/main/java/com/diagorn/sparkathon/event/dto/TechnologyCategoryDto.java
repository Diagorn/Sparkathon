package com.diagorn.sparkathon.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import static com.diagorn.sparkathon.event.utils.SwaggerConstants.TECHNOLOGY_CATEGORY_DTO_EXAMPLE;

/**
 * Technology category DTO
 *
 * @param id          - technology category id
 * @param code        - technology category code
 * @param name        - technology category name
 * @param description - technology category description
 * @author diagorn
 */
@Schema(description = "Technology category DTO", example = TECHNOLOGY_CATEGORY_DTO_EXAMPLE)
public record TechnologyCategoryDto(
        @Schema(description = "Technology category id", example = "1")
        Long id,
        @Schema(description = "Technology category code", example = "BACKEND")
        String code,
        @Schema(description = "Technology category name", example = "Backend")
        String name,
        @Schema(description = "Technology category description", example = "Server technologies and frameworks")
        String description
) {
}
