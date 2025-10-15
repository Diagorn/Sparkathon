CREATE TABLE challenge_technology
(
    challenge_id  BIGINT    NOT NULL,
    technology_id BIGINT    NOT NULL,
    is_required   BOOLEAN   NOT NULL DEFAULT FALSE,
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (challenge_id, technology_id)
);

COMMENT ON TABLE challenge_technology IS 'Challenges many-to-many technologies';