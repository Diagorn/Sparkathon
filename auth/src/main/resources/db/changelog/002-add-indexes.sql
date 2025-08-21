-- liquibase formatted sql

-- changeset diagorn:4
-- rollback drop index usr_login_idx;
create index usr_login_idx on usr(login);

-- changeset diagorn:5
-- rollback drop index usr_email_idx;
create index usr_email_idx on usr(email);