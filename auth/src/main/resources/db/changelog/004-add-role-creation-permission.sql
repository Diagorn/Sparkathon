-- liquibase formatted sql

-- changeset diagorn:8
-- rollback alter table roles drop column is_allowed_for_creation
alter table role
add column is_allowed_for_creation boolean not null default false;
update role set is_allowed_for_creation = false where id = 4;
update role set is_allowed_for_creation = true where id in (1, 2, 3);