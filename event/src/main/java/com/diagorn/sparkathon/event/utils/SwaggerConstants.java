package com.diagorn.sparkathon.event.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SwaggerConstants {

    public static final String CHALLENGE_CREATE_DTO_EXAMPLE = """
            {
                "title": "Mobile app",
                "description": "You need to create a mobile app",
                "rules": "No DeepSeek allowed",
                "evaluationCriteria": "Good -> 5, Bad -> 2"
            }
            """;

    public static final String CHALLENGE_DTO_EXAMPLE = """
            {
                "id": 1,
                "hackathonId": 123,
                "title": "Mobile app",
                "description": "You need to create a mobile app",
                "rules": "No DeepSeek allowed",
                "evaluationCriteria": "Good -> 5, Bad -> 2"
            }
            """;

    public static final String HACKATHON_DTO_EXAMPLE = """
            {
                "id": 123,
                "title": "T-bank hackathon",
                "description": "Hackathon for enthusiastic developers",
                "status": {
                    "id": 2,
                    "code": "REGISTRATION_OPEN",
                    "name": "Registration open",
                    "description": "Registration is open: members awaited"
                },
                "registrationStart": "2025-12-20T13:56:39.492",
                "registrationEnd": "2025-12-21T13:56:39.492",
                "eventStart": "2025-12-22T13:56:39.492",
                "eventEnd": "2025-12-23T13:56:39.492",
                "maxTeamSize": 5,
                "maxTeams": 20,
                "technologies": [
                    "id": 1,
                    "name": "Java",
                    "description": "Java programming language",
                    "category": {
                        "id": 1,
                        "code": "BACKEND",
                        "name": "Backend",
                        "description": "Server technologies and frameworks"
                    }
                ],
                "challenges": [
                    {
                        "id": 1,
                        "hackathonId": 123,
                        "title": "Mobile app",
                        "description": "You need to create a mobile app",
                        "rules": "No DeepSeek allowed",
                        "evaluationCriteria": "Good -> 5, Bad -> 2"
                    }
                ],
                "events": [
                    {
                        "id": 56,
                        "hackathonId": 123,
                        "title": "Welcome coffee",
                        "description": "All participants drink coffee and have fun",
                        "startTime": "2025-12-22T14:56:39.492",
                        "endTime": "2025-12-22T15:56:39.492",
                        "type": "Welcome"
                    }
                ]
            }
            """;

    public static final String HACKATHON_REQUEST_EXAMPLE = """
            {
                "title": "T-bank hackathon",
                "description": "Hackathon for enthusiastic developers",
                "status": {
                    "id": 2,
                    "code": "REGISTRATION_OPEN",
                    "name": "Registration open",
                    "description": "Registration is open: members awaited"
                },
                "registrationStart": "2025-12-20T13:56:39.492",
                "registrationEnd": "2025-12-21T13:56:39.492",
                "eventStart": "2025-12-22T13:56:39.492",
                "eventEnd": "2025-12-23T13:56:39.492",
                "maxTeamSize": 5,
                "maxTeams": 20,
                "technologies": [
                    "id": 1,
                    "name": "Java",
                    "description": "Java programming language",
                    "category": {
                        "id": 1,
                        "code": "BACKEND",
                        "name": "Backend",
                        "description": "Server technologies and frameworks"
                    }
                ],
                "challenges": [
                    {
                        "id": 1,
                        "hackathonId": 123,
                        "title": "Mobile app",
                        "description": "You need to create a mobile app",
                        "rules": "No DeepSeek allowed",
                        "evaluationCriteria": "Good -> 5, Bad -> 2"
                    }
                ],
                "events": [
                    {
                        "id": 56,
                        "hackathonId": 123,
                        "title": "Welcome coffee",
                        "description": "All participants drink coffee and have fun",
                        "startTime": "2025-12-22T14:56:39.492",
                        "endTime": "2025-12-22T15:56:39.492",
                        "type": "Welcome"
                    }
                ]
            }
            """;

    public static final String HACKATHON_STATUS_DTO_EXAMPLE = """
            {
                "id": 1,
                "code": "DRAFT",
                "name": "draft",
                "description": "Hackathon in development"
            }
            """;

    public static final String HACKATHON_TECHNOLOGIES_EXAMPLE = """
            [
                "id": 1,
                "name": "Java",
                "description": "Java programming language",
                "category": {
                    "id": 1,
                    "code": "BACKEND",
                    "name": "Backend",
                    "description": "Server technologies and frameworks"
                }
            ]
            """;

    public static final String HACKATHON_CHALLENGES_EXAMPLE = """
            [
              {
                "id": 1,
                "hackathonId": 123,
                "title": "Mobile app",
                "description": "You need to create a mobile app",
                "rules": "No DeepSeek allowed",
                "evaluationCriteria": "Good -> 5, Bad -> 2"
              }
            ]
            """;

    public static final String HACKATHON_EVENTS_EXAMPLE = """
            [
              {
                "id": 56,
                "hackathonId": 123,
                "title": "Welcome coffee",
                "description": "All participants drink coffee and have fun",
                "startTime": "2025-12-22T14:56:39.492",
                "endTime": "2025-12-22T15:56:39.492",
                "type": "Welcome"
              }
            ]
            """;

    public static final String HACKATHON_SCHEDULE_CREATE_DTO_EXAMPLE = """
            {
                "title": "Welcome coffee",
                "description": "All participants drink coffee and have fun",
                "startTime": "2025-12-22T14:56:39.492",
                "endTime": "2025-12-22T15:56:39.492",
                "type": "Welcome"
            }
            """;

    public static final String HACKATHON_SCHEDULE_DTO_EXAMPLE = """
            {
                "id": 56,
                "hackathonId": 123,
                "title": "Welcome coffee",
                "description": "All participants drink coffee and have fun",
                "startTime": "2025-12-22T14:56:39.492",
                "endTime": "2025-12-22T15:56:39.492",
                "type": "Welcome"
            }
            """;

    public static final String HACKATHON_SEARCH_REQUEST_EXAMPLE = """
            {
                "title": "T-bank hackathon",
                "description": "building AI-models",
                "statusIds": [1, 2],
                "registrationStartFrom": "2025-12-20T13:56:39.492",
                "registrationStartTo": "2025-12-23T13:56:39.492",
                "eventStartFrom": "2025-12-25T13:56:39.492",
                "eventStartTo": "2025-12-25T13:56:39.492",
                technologyIds: [5, 3, 8],
            }
            """;

    public static final String HACKATHON_STATUS_CHANGE_REQUEST_EXAMPLE = """
            {
                "statusId": 1,
                "newStatusId": 2
            }
            """;

    public static final String TECHNOLOGY_CATEGORY_DTO_EXAMPLE = """
            {
                "id": 1,
                "code": "BACKEND",
                "name": "Backend",
                "description": "Server technologies and frameworks"
            }
            """;

    public static final String TECHNOLOGY_DTO_EXAMPLE = """
            {
                "id": 1,
                "name": "Java",
                "description": "Java programming language",
                "category": {
                    "id": 1,
                    "code": "BACKEND",
                    "name": "Backend",
                    "description": "Server technologies and frameworks"
                }
            }
            """;
}
