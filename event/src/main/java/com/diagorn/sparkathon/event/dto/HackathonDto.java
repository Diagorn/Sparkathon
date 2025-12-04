package com.diagorn.sparkathon.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

import static com.diagorn.sparkathon.event.utils.SwaggerConstants.*;

/**
 * Hackathon
 *
 * @param id                - id
 * @param title             - title
 * @param description       - description
 * @param status            - status
 * @param registrationStart - time when registration starts
 * @param registrationEnd   - time when registration ends
 * @param eventStart        - time when event starts
 * @param eventEnd          - time when event ends
 * @param maxTeamSize       - max members in team
 * @param maxTeams          - max teams
 * @param technologies      - used technologies
 * @param challenges        - list of challenges
 * @param events            - schedule
 *
 * @author diagorn
 */
@Schema(description = "Main hackathon DTO", example = HACKATHON_DTO_EXAMPLE)
public record HackathonDto (
        @Schema(description = "Hackathon ID", example = "123")
        Long id,
        @Schema(description = "Hackathon title", example = "Main T-Bank hackathon")
        String title,
        @Schema(description = "Hackathon description", example = "A hackathon for fellow programmers")
        String description,
        @Schema(description = "Hackathon status", example = HACKATHON_STATUS_DTO_EXAMPLE)
        HackathonStatusDto status,
        @Schema(description = "Registration start date", example = "2025-12-20T13:56:39.492")
        LocalDateTime registrationStart,
        @Schema(description = "Registration end date", example = "2025-12-20T13:56:39.492")
        LocalDateTime registrationEnd,
        @Schema(description = "Hackathon start date", example = "2025-12-20T13:56:39.492")
        LocalDateTime eventStart,
        @Schema(description = "Hackathon end date", example = "2025-12-20T13:56:39.492")
        LocalDateTime eventEnd,
        @Schema(description = "Max team size", example = "5")
        Integer maxTeamSize,
        @Schema(description = "Max number of teams", example = "30")
        Integer maxTeams,
        @Schema(description = "Used technologies", example = HACKATHON_TECHNOLOGIES_EXAMPLE)
        List<TechnologyDto> technologies,
        @Schema(description = "Hackathon challenges", example = HACKATHON_CHALLENGES_EXAMPLE)
        List<ChallengeDto> challenges,
        @Schema(description = "Hackathon events", example = HACKATHON_EVENTS_EXAMPLE)
        List<HackathonScheduleDto> events
) {
}
