--liquibase formatted sql

--changeset Novacroft:12.0.15
INSERT INTO ${schemaName}.contact
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CUSTOMERID", "NAME", "VALUE", "TYPE")
VALUES
(${schemaName}.contact_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 5001, 'Phone', '01604 123456', 'HomePhone');
INSERT INTO ${schemaName}.contact
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CUSTOMERID", "NAME", "VALUE", "TYPE")
VALUES
(${schemaName}.contact_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 5001, 'Phone', '07674673563', 'MobilePhone');
