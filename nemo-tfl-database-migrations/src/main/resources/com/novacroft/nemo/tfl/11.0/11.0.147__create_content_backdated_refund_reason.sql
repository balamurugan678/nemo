--liquibase formatted sql

--changeset Novacroft:11.0.147
INSERT INTO ${schemaName}.backdatedrefundreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, 1, 'Medical', '');

INSERT INTO ${schemaName}.backdatedrefundreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, 2, 'Other reason', '');
