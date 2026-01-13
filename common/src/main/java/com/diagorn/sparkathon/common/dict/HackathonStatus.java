package com.diagorn.sparkathon.common.dict;

import com.diagorn.sparkathon.common.exception.InternalServerError;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Hackathon statuses
 *
 * @author diagorn
 */
@AllArgsConstructor
@Getter
public enum HackathonStatus {

    DRAFT(1L, "Draft", "Draft"),
    REGISTRATION_OPEN(2L, "Registration open", "REGISTRATION_OPEN"),
    REGISTRATION_CLOSED(3L, "Registration closed", "REGISTRATION_CLOSED"),
    IN_PROGRESS(4L, "In progress", "IN_PROGRESS"),
    JUDGING(5L, "Judging", "JUDGING"),
    COMPLETED(6L, "Completed", "COMPLETED"),
    CANCELLED(7L, "Cancelled", "CANCELLED");

    /**
     * Status id
     */
    private final Long id;
    /**
     * Status name
     */
    private final String name;
    /**
     * Status code
     */
    private final String code;

    public static HackathonStatus ofCode(String code) {
        return Arrays.stream(values())
                .filter(val -> val.code.equals(code))
                .findAny()
                .orElseThrow(() -> new InternalServerError(String.format("No sufficient status with code %s", code)));
    }
}
