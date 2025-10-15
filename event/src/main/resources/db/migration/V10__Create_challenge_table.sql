CREATE TABLE challenge
(
    id                  BIGSERIAL PRIMARY KEY,
    hackathon_id        BIGINT       NOT NULL,
    title               VARCHAR(255) NOT NULL,
    description         TEXT         NOT NULL,
    rules               TEXT,
    evaluation_criteria TEXT,
    created_at          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE challenge IS 'Tasks devoted to hackathon';