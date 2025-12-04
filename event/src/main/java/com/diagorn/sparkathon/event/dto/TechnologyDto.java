package com.diagorn.sparkathon.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import static com.diagorn.sparkathon.event.utils.SwaggerConstants.TECHNOLOGY_CATEGORY_DTO_EXAMPLE;
import static com.diagorn.sparkathon.event.utils.SwaggerConstants.TECHNOLOGY_DTO_EXAMPLE;

/**
 * Technology DTO
 *
 * @param id          - technology id
 * @param name        - technology name
 * @param description - technology description
 * @param category    - technology category
 * @author diagorn
 */
@Schema(description = "Technology DTO", example = TECHNOLOGY_DTO_EXAMPLE)
public record TechnologyDto(
        @Schema(description = "Technology id", example = "1")
        Long id,
        @Schema(description = "Technology name", example = "Java")
        String name,
        @Schema(description = "Technology description", example = "Java programming language")
        String description,
        @Schema(description = "Technology category", example = TECHNOLOGY_CATEGORY_DTO_EXAMPLE)
        TechnologyCategoryDto category
) {
}
