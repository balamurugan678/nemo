--liquibase formatted sql

--changeset Novacroft:11.0.177
INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'mandatoryFieldEmpty.error', 'en_GB',
'You did not enter a {0}. Please type in a value and try again.');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'selectCardFieldEmpty.error', 'en_GB',
'You did not select a card. Please select a card and try again.');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'mandatorySelectFieldEmpty.error', 'en_GB',
'You did not select {0}. Please select and try again.');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'pickUpStation.label', 'en_GB',
'a pick up station');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'reasonForMissisng.label', 'en_GB',
'a reason for missing');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'missingStationId.label', 'en_GB',
'where you forgot to touch in/out');


