--liquibase formatted sql

--changeset Novacroft:11.0.187
INSERT INTO ${schemaName}."SELECTLIST"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "NAME", "DESCRIPTION")
VALUES
(${schemaName}."SELECTLIST_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'SecurityQuestions', 'Security Questions');


INSERT INTO ${schemaName}."SELECTLISTOPTION"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "SELECTLISTID", "VALUE", "DISPLAYORDER")
SELECT
  ${schemaName}."SELECTLISTOPTION_SEQ".nextval,
  'Installer',
  SYSDATE,
  NULL,
  NULL,
  "ID",
  'yourmothersmaidenname',
  10
FROM ${schemaName}."SELECTLIST" WHERE "NAME" = 'SecurityQuestions';

INSERT INTO ${schemaName}."SELECTLISTOPTION"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "SELECTLISTID", "VALUE", "DISPLAYORDER")
SELECT
  ${schemaName}."SELECTLISTOPTION_SEQ".nextval,
  'Installer',
  SYSDATE,
  NULL,
  NULL,
  "ID",
  'memorabledate',
  20
FROM ${schemaName}."SELECTLIST" WHERE "NAME" = 'SecurityQuestions';

INSERT INTO ${schemaName}."SELECTLISTOPTION"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "SELECTLISTID", "VALUE", "DISPLAYORDER")
SELECT
  ${schemaName}."SELECTLISTOPTION_SEQ".nextval,
  'Installer',
  SYSDATE,
  NULL,
  NULL,
  "ID",
  'memorableplace',
  30
FROM ${schemaName}."SELECTLIST" WHERE "NAME" = 'SecurityQuestions';