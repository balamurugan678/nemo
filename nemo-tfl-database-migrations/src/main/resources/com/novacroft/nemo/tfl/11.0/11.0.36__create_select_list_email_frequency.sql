--liquibase formatted sql

--changeset Novacroft:11.0.36
INSERT INTO ${schemaName}."SELECTLIST"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "NAME", "DESCRIPTION")
VALUES
(${schemaName}."SELECTLIST_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'StatementEmailFrequencies', 'Journey History Statement Email Frequency');

INSERT INTO ${schemaName}."SELECTLISTOPTION"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "SELECTLISTID", "VALUE", "DISPLAYORDER")
SELECT
  ${schemaName}."SELECTLISTOPTION_SEQ".nextval,
  'Installer',
  SYSDATE,
  NULL,
  NULL,
  "ID",
  'None',
  0
FROM ${schemaName}."SELECTLIST" WHERE "NAME" = 'StatementEmailFrequencies';

INSERT INTO ${schemaName}."SELECTLISTOPTION"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "SELECTLISTID", "VALUE", "DISPLAYORDER")
SELECT
  ${schemaName}."SELECTLISTOPTION_SEQ".nextval,
  'Installer',
  SYSDATE,
  NULL,
  NULL,
  "ID",
  'Weekly',
  10
FROM ${schemaName}."SELECTLIST" WHERE "NAME" = 'StatementEmailFrequencies';

INSERT INTO ${schemaName}."SELECTLISTOPTION"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "SELECTLISTID", "VALUE", "DISPLAYORDER")
SELECT
  ${schemaName}."SELECTLISTOPTION_SEQ".nextval,
  'Installer',
  SYSDATE,
  NULL,
  NULL,
  "ID",
  'Monthly',
  20
FROM ${schemaName}."SELECTLIST" WHERE "NAME" = 'StatementEmailFrequencies';

