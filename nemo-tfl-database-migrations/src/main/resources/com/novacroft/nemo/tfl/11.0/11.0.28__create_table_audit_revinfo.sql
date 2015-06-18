--liquibase formatted sql

--changeset Novacroft:11.0.28
CREATE SEQUENCE ${schemaName}.HIBERNATE_SEQUENCE start with 1 increment by 1 nomaxvalue;

create table ${schemaName}.REVINFO (
REV NUMBER,
REVTSTMP NUMBER,
primary key (REV)
);