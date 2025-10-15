CREATE TABLE hackathon_technology_stack
(
    hackathon_id        BIGINT NOT NULL,
    technology_stack_id BIGINT NOT NULL,
    constraint pk_hackathon_technology_stack PRIMARY KEY (hackathon_id, technology_stack_id)
);

COMMENT ON TABLE hackathon_technology_stack IS 'Hackathon many-to-many technology stack'