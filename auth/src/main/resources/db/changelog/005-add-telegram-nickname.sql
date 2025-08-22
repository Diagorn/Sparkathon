-- liquibase formatted sql

-- changeset diagorn:8
-- rollback alter table usr drop column telegram_nickname
alter table usr
    add column telegram_nickname varchar;