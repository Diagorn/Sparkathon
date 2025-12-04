package com.diagorn.sparkathon.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import static com.diagorn.sparkathon.event.utils.SwaggerConstants.HACKATHON_STATUS_CHANGE_REQUEST_EXAMPLE;

/**
 * Hackathon status change request
 *
 * @param statusId    - current status id
 * @param newStatusId - new status id
 * @author diagorn
 */
@Schema(description = "Hackathon status change request", example = HACKATHON_STATUS_CHANGE_REQUEST_EXAMPLE)
public record HackathonStatusChangeRequest(
        @Schema(description = "Current status id", example = "1")
        Long statusId,
        @Schema(description = "New status id", example = "2")
        Long newStatusId
) {
}
