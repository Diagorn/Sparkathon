CREATE TABLE hackathon (
                           id BIGSERIAL PRIMARY KEY,
                           title VARCHAR(255) NOT NULL,
                           description TEXT,
                           status_id BIGINT NOT NULL,
                           registration_start TIMESTAMP NOT NULL,
                           registration_end TIMESTAMP NOT NULL,
                           event_start TIMESTAMP NOT NULL,
                           event_end TIMESTAMP NOT NULL,
                           max_team_size INTEGER NOT NULL DEFAULT 5,
                           max_teams INTEGER,
                           created_by BIGINT NOT NULL,
                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE hackathon IS 'Hackathon list table';