-- liquibase formatted sql

-- changeset diagorn:1
-- rollback drop table role;
create table role (
    id bigint primary key,
    name varchar not null unique,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp
);
comment on table role is 'User roles';

-- changeset diagorn:2
-- rollback drop table usr;
create table usr (
    id bigint primary key,
    role_id bigint not null,
    first_name varchar not null,
    last_name varchar not null,
    middle_name varchar,
    login varchar not null unique,
    password varchar not null,
    email varchar not null unique,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp,
    constraint usr_role_fk foreign key (role_id) references role(id)
        on delete set null
        on update cascade
);
comment on table usr is 'Users of the system';

-- changeset diagorn:3
-- rollback delete from usr_role where id in (1, 2, 3, 4);
insert into role (id, name) values
                                (1, 'PARTICIPANT'),
                                (2, 'CAPTAIN'),
                                (3, 'JUDGE'),
                                (4, 'ADMIN')