package com.diagorn.sparkathon.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import static com.diagorn.sparkathon.event.utils.SwaggerConstants.CHALLENGE_CREATE_DTO_EXAMPLE;

/**
 * Hackathon challenge for creation DTO
 *
 * @param title              - challenge title
 * @param description        - challenge description
 * @param rules              - challenge rules
 * @param evaluationCriteria - how the challenge will be evaluated
 *
 * @author diagorn
 */
@Schema(description = "Create challenge DTO", example = CHALLENGE_CREATE_DTO_EXAMPLE)
public record ChallengeCreateDto(
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
