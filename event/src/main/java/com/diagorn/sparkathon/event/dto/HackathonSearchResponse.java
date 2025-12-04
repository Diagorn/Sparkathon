package com.diagorn.sparkathon.event.dto;

import com.diagorn.sparkathon.event.utils.SwaggerConstants;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

import static com.diagorn.sparkathon.event.utils.SwaggerConstants.HACKATHON_STATUS_DTO_EXAMPLE;
import static com.diagorn.sparkathon.event.utils.SwaggerConstants.HACKATHON_TECHNOLOGIES_EXAMPLE;

/**
 * Response to hackathon search
 *
 * @param id                - hackathon id
 * @param title             - hackathon title
 * @param description       - hackathon description
 * @param status            - hackathon status
 * @param registrationStart - hackathon registration start
 * @param registrationEnd   - hackathon registration end
 * @param eventStart        - hackathon start
 * @param eventEnd          - hackathon end
 * @param maxTeamSize       - max number of members in team
 * @param maxTeams          - max teams number
 * @param technologies      - used technologies
 */
public record HackathonSearchResponse(
        @Schema(description = "Hackathon id", example = "123")
        Long id,
        @Schema(description = "Hackathon title", example = "T-Bank ML hackathon")
        String title,
        @Schema(description = "Hackathon description", example = "A hackathon for fellow programmers")
        String description,
        @Schema(description = "Hackathon status", example = HACKATHON_STATUS_DTO_EXAMPLE)
        HackathonStatusDto status,
        @Schema(description = "Hackathon registration date start", example = "2025-12-20T13:56:39.492")
        LocalDateTime registrationStart,
        @Schema(description = "Hackathon registration date end", example = "2025-12-20T13:56:39.492")
        LocalDateTime registrationEnd,
        @Schema(description = "Hackathon event date start", example = "2025-12-20T13:56:39.492")
        LocalDateTime eventStart,
        @Schema(description = "Hackathon event date end", example = "2025-12-20T13:56:39.492")
        LocalDateTime eventEnd,
        @Schema(description = "Hackathon max teams size", example = "5")
        Integer maxTeamSize,
        @Schema(description = "Hackathon max teams number", example = "30")
        Integer maxTeams,
        @Schema(description = "Technologies used in hackathon", example = HACKATHON_TECHNOLOGIES_EXAMPLE)
        List<TechnologyDto> technologies
) {
}
