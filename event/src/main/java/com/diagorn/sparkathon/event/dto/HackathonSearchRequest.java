package com.diagorn.sparkathon.event.dto;

import com.diagorn.sparkathon.event.utils.SwaggerConstants;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

import static com.diagorn.sparkathon.event.utils.SwaggerConstants.HACKATHON_SEARCH_REQUEST_EXAMPLE;

/**
 * Request for searching hackathons
 *
 * @param title                 - hackathon title
 * @param description           - hackathon description containing
 * @param statusIds             - hackathon status id
 * @param registrationStartFrom - registration date start
 * @param registrationStartTo   - registration date end
 * @param eventStartFrom        - event date start
 * @param eventStartTo          - event date end
 * @param technologyIds         - must include technologies with these ids
 */
@Schema(description = "Hackathon search request", example = HACKATHON_SEARCH_REQUEST_EXAMPLE)
public record HackathonSearchRequest(
        @Schema(description = "Hackathon title", example = "T-bank ML hackathon")
        String title,
        @Schema(description = "Hackathon description part", example = "building AI-models")
        String description,
        @Schema(description = "Hackathon status id", example = "1")
        List<Long> statusIds,
        @Schema(description = "Registration date start", example = "2025-12-20T13:56:39.492")
        LocalDateTime registrationStartFrom,
        @Schema(description = "Registration date end", example = "2025-12-23T13:56:39.492")
        LocalDateTime registrationStartTo,
        @Schema(description = "Event date start", example = "2025-12-25T13:56:39.492")
        LocalDateTime eventStartFrom,
        @Schema(description = "Event date end", example = "2025-12-27T13:56:39.492")
        LocalDateTime eventStartTo,
        @Schema(description = "Ids of technologies used in hackathon", example = "[1, 2, 3]")
        List<Long> technologyIds
) {
}
