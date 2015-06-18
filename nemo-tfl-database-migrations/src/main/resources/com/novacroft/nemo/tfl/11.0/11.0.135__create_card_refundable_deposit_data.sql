--liquibase formatted sql

--changeset Novacroft:11.0.135
INSERT INTO ${schemaName}.CARDREFUNDABLEDEPOSIT
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "PRICE", "STARTDATE", "ENDDATE")
VALUES
(${schemaName}.cardrefundabledeposit_seq.nextval, 'Installer', SYSDATE, NULL, NULL, '500', SYSDATE, NULL);
