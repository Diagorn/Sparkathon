-- Adding foreign keys
ALTER TABLE hackathon
    ADD CONSTRAINT fk_hackathon_status
        FOREIGN KEY (status_id) REFERENCES hackathon_status (id);

ALTER TABLE hackathon_status_history
    ADD CONSTRAINT fk_status_history_hackathon
        FOREIGN KEY (hackathon_id) REFERENCES hackathon (id) ON DELETE CASCADE;

ALTER TABLE hackathon_status_history
    ADD CONSTRAINT fk_old_status
        FOREIGN KEY (old_status_id) REFERENCES hackathon_status (id);

ALTER TABLE hackathon_status_history
    ADD CONSTRAINT fk_new_status
        FOREIGN KEY (new_status_id) REFERENCES hackathon_status (id);

ALTER TABLE challenge
    ADD CONSTRAINT fk_challenge_hackathon
        FOREIGN KEY (hackathon_id) REFERENCES hackathon (id) ON DELETE CASCADE;

ALTER TABLE challenge_technology
    ADD CONSTRAINT fk_challenge_tech_challenge
        FOREIGN KEY (challenge_id) REFERENCES challenge (id) ON DELETE CASCADE;

ALTER TABLE technology_stack
    ADD CONSTRAINT fk_technology_stack_category
        FOREIGN KEY (category_id) REFERENCES technology_category (id) ON DELETE CASCADE;

ALTER TABLE challenge_technology
    ADD CONSTRAINT fk_challenge_tech_technology
        FOREIGN KEY (technology_id) REFERENCES technology_stack (id) ON DELETE CASCADE;

ALTER TABLE hackathon_schedule
    ADD CONSTRAINT fk_schedule_hackathon
        FOREIGN KEY (hackathon_id) REFERENCES hackathon (id) ON DELETE CASCADE;

ALTER TABLE hackathon_technology_stack
    ADD CONSTRAINT fk_hackathon_technology_stack
        FOREIGN KEY (hackathon_id) REFERENCES hackathon (id) ON DELETE CASCADE;

ALTER TABLE hackathon_technology_stack
    ADD CONSTRAINT fk_technology_stack_hackathon
        FOREIGN KEY (technology_stack_id) REFERENCES technology_stack (id) ON DELETE CASCADE;

-- Adding indexes for performance
CREATE INDEX idx_hackathon_status_id ON hackathon (status_id);
CREATE INDEX idx_hackathon_created_at ON hackathon (created_at);
CREATE INDEX idx_hackathon_event_dates ON hackathon (event_start, event_end);
CREATE INDEX idx_status_history_hackathon_id ON hackathon_status_history (hackathon_id);
CREATE INDEX idx_status_history_created_at ON hackathon_status_history (created_at);
CREATE INDEX idx_challenge_hackathon_id ON challenge (hackathon_id);
CREATE INDEX idx_challenge_tech_challenge_id ON challenge_technology (challenge_id);
CREATE INDEX idx_challenge_tech_technology_id ON challenge_technology (technology_id);
CREATE INDEX idx_schedule_hackathon_id ON hackathon_schedule (hackathon_id);
CREATE INDEX idx_schedule_times ON hackathon_schedule (start_time, end_time);