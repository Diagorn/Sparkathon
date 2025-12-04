package com.diagorn.sparkathon.event.dto;

import com.diagorn.sparkathon.event.utils.SwaggerConstants;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

import static com.diagorn.sparkathon.event.utils.SwaggerConstants.*;

/**
 * Create hackathon request
 *
 * @param title - hackathon title
 * @param description - hackathon description
 * @param status - hackathon status
 * @param registrationStart - hackathon registration start date
 * @param registrationEnd - hackathon registration end date
 * @param eventStart - hackathon start date
 * @param eventEnd - hackathon end date
 * @param maxTeamSize - hackathon max teams size
 * @param maxTeams - hackathon max teams number
 * @param technologies - technologies used in hackathon
 * @param challenges - hackathon challenges
 * @param events - hackathon events (schedule)
 *
 * @author diagorn
 */
@Schema(description = "Hackathon create/update request", example = HACKATHON_REQUEST_EXAMPLE)
public record HackathonRequest(
        @Schema(description = "Hackathon title", example = "T-Bank main hackathon")
        String title,
        @Schema(description = "Hackathon description", example = "The best hackathon in the world")
        String description,
        @Schema(description = "Hackathon status", example = HACKATHON_STATUS_DTO_EXAMPLE)
        HackathonStatusDto status,
        @Schema(description = "Hackathon registration start date", example = "2025-12-20T13:56:39.492")
        LocalDateTime registrationStart,
        @Schema(description = "Hackathon registration end date", example = "2025-12-20T13:56:39.492")
        LocalDateTime registrationEnd,
        @Schema(description = "Hackathon event start date", example = "2025-12-20T13:56:39.492")
        LocalDateTime eventStart,
        @Schema(description = "Hackathon event end date", example = "2025-12-20T13:56:39.492")
        LocalDateTime eventEnd,
        @Schema(description = "Hackathon max team size", example = "5")
        Integer maxTeamSize,
        @Schema(description = "Hackathon max teams", example = "30")
        Integer maxTeams,
        @Schema(description = "Technologies used in hackathon", example = HACKATHON_TECHNOLOGIES_EXAMPLE)
        List<TechnologyDto> technologies,
        @Schema(description = "Hackathon challenges", example = HACKATHON_CHALLENGES_EXAMPLE)
        List<ChallengeCreateDto> challenges,
        @Schema(description = "Hackathon events (schedule)", example = HACKATHON_EVENTS_EXAMPLE)
        List<HackathonScheduleCreateDto> events
) {
}
