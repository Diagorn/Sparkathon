CREATE TABLE hackathon_schedule
(
    id           BIGSERIAL PRIMARY KEY,
    hackathon_id BIGINT       NOT NULL,
    title        VARCHAR(255) NOT NULL,
    description  TEXT,
    start_time   TIMESTAMP    NOT NULL,
    end_time     TIMESTAMP    NOT NULL,
    type         VARCHAR(50)  NOT NULL,
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE hackathon_schedule IS 'Hackathon events table';