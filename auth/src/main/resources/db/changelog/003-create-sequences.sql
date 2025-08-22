-- liquibase formatted sql

-- changeset diagorn:6
-- rollback drop sequence if exists usr_id_seq
create sequence if not exists usr_id_seq start with 1 increment by 1;

-- changeset diagorn:7
-- rollback drop sequence if exists role_id_seq
create sequence if not exists role_id_seq start with 1 increment by 1;