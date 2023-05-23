--liquibase formatted sql

        --changeset maris:1

        create table if not exists weather
        (
            id serial not null primary key,
            ip varchar(100) not null,
            city varchar(100) not null,
            country varchar(100) not null,
            longitude decimal not null,
            latitude decimal not null,
            weather varchar(100) not null,
            temperature varchar(100) not null,
            time varchar(100) not null
        );

        -- rollback drop table weather;