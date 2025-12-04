package com.diagorn.sparkathon.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

import static com.diagorn.sparkathon.event.utils.SwaggerConstants.HACKATHON_SCHEDULE_CREATE_DTO_EXAMPLE;

/**
 * Create event for hackathon DTO
 *
 * @param title       - title
 * @param description - description
 * @param startTime   - time of start
 * @param endTime     - time of end
 * @param type        - type
 */
@Schema(description = "Schedule create DTO", example = HACKATHON_SCHEDULE_CREATE_DTO_EXAMPLE)
public record HackathonScheduleCreateDto(
        @Schema(description = "Event title", example = "Registration")
        String title,
        @Schema(description = "Event description", example = "Networking for you to get new friends")
        String description,
        @Schema(description = "Event start date", example = "2025-12-20T13:56:39.492")
        LocalDateTime startTime,
        @Schema(description = "Event end date", example = "2025-12-20T13:56:39.492")
        LocalDateTime endTime,
        @Schema(description = "Event type", example = "Registration")
        String type
) {
}
