--liquibase formatted sql

--changeset Novacroft:11.0.169
INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'OysterCardLost', 'Oyster card has been lost', 99, 0);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'OysterCardStolen', 'Oyster card has been stolen', 99, 0);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'OysterCardHotlisted', 'Oyster card has been hotlisted', 99, 0);

