--liquibase formatted sql

--changeset Novacroft:11.0.43
-- depends on select access on ${innovatorTflSchemaName}.nlclookup

create or replace synonym ${schemaName}.nlclookup for ${innovatorTflSchemaName}.nlclookup;
