package com.diagorn.sparkathon.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import static com.diagorn.sparkathon.event.utils.SwaggerConstants.CHALLENGE_DTO_EXAMPLE;

/**
 * Hackathon challenge DTO
 *
 * @param id                 - challenge id
 * @param hackathonId        - hackathon id
 * @param title              - challenge title
 * @param description        - challenge description
 * @param rules              - challenge rules
 * @param evaluationCriteria - how the challenge will be evaluated
 *
 * @author diagorn
 */
@Schema(description = "Hackathon challenge DTO", example = CHALLENGE_DTO_EXAMPLE)
public record ChallengeDto(
        @Schema(description = "Challenge id", example = "1")
        Long id,
        @Schema(description = "Related hackathon id", example = "123")
        Long hackathonId,
        @Schema(description = "Challenge title", example = "Telegram bot development")
        String title,
        @Schema(description = "Challenge description", example = "You need to develop a telegram bot")
        String description,
        @Schema(description = "Challenge rules", example = "No ChatGPT allowed")
        String rules,
        @Schema(description = "How the challenge will be evaluated", example = "Good -> 5, Bad -> 2")
        String evaluationCriteria
) {
}
