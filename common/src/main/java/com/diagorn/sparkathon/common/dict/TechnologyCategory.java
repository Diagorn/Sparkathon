package com.diagorn.sparkathon.common.dict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

/**
 * Technology category dict enum
 *
 * @author diagorn
 */
@RequiredArgsConstructor
@Getter
public enum TechnologyCategory {
    BACKEND("Backend"),
    FRONTEND("Frontend"),
    DATABASE("Database"),
    DEVOPS("DevOps"),
    CLOUD("Cloud"),
    AI_ML("AI/ML");

    /**
     * Name to be displayed on client
     */
    private final String displayName;

    public static Optional<TechnologyCategory> ofCode(String code) {
        return Arrays.stream(values())
                .filter(cat -> cat.name().equalsIgnoreCase(code))
                .findFirst();
    }
}
