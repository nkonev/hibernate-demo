-- ALTER SYSTEM SET max_connections = 400;
-- Uncomment if you need to view the full postgres logs (SQL statements, ...) via `docker logs -f postgresql-test`
ALTER SYSTEM SET log_statement = 'all';
ALTER SYSTEM SET synchronous_commit = 'off'; -- https://postgrespro.ru/docs/postgrespro/9.5/runtime-config-wal.html#GUC-SYNCHRONOUS-COMMIT
-- ALTER SYSTEM SET shared_buffers='512MB';
ALTER SYSTEM SET fsync=FALSE;
ALTER SYSTEM SET full_page_writes=FALSE;
ALTER SYSTEM SET commit_delay=100000;
ALTER SYSTEM SET commit_siblings=10;
-- ALTER SYSTEM SET work_mem='50MB';
ALTER SYSTEM SET log_line_prefix = '%a %u@%d ';


create user hiber with password 'hiberPazZw0rd';
create database hiber with owner hiber;
\connect hiber hiber;


-- poor man's migration

create table person (
    id UUID PRIMARY KEY,
    first_name text NOT NULL,
    second_name text NOT NULL
);

create table application (
    id UUID PRIMARY KEY,
    name text NOT NULL
);

CREATE TABLE main_person (
    application_id UUID REFERENCES application(id),
    UNIQUE (id, application_id)
) INHERITS (person);

CREATE TABLE secondary_person (
    application_id UUID REFERENCES application(id)
) INHERITS (person);

insert into application(id, name) VALUES
('4120d4f6-f6d6-4444-917e-278d46250433', 'First App');

insert into main_person(id, first_name, second_name, application_id) VALUES
('96dc0f4d-fa2a-4111-bf14-c190d1281ed0', 'Brat', 'Pit', '4120d4f6-f6d6-4444-917e-278d46250433');

insert into secondary_person(id, first_name, second_name, application_id) VALUES
('6136c5f6-f6d6-44ea-917e-278d46250433', 'Arnold', 'Schwartz', '4120d4f6-f6d6-4444-917e-278d46250433'),
('00aabbcc-99d6-40ea-9178-278d46250400', 'Vlad', 'Dr', '4120d4f6-f6d6-4444-917e-278d46250433');
