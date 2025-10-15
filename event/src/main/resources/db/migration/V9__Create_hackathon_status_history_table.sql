CREATE TABLE hackathon_status_history
(
    id            BIGSERIAL PRIMARY KEY,
    hackathon_id  BIGINT    NOT NULL,
    old_status_id BIGINT,
    new_status_id BIGINT    NOT NULL,
    changed_by    BIGINT    NOT NULL,
    change_reason TEXT,
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE hackathon_status_history IS 'Hackathon status history';