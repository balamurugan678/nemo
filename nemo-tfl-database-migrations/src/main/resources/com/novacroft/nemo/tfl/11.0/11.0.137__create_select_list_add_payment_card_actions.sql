--liquibase formatted sql

--changeset Novacroft:11.0.137
INSERT INTO ${schemaName}."SELECTLIST"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "NAME", "DESCRIPTION")
VALUES
(${schemaName}."SELECTLIST_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'AddPaymentCardAction', 'Add Payment Card Actions');

INSERT INTO ${schemaName}."SELECTLISTOPTION"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "SELECTLISTID", "VALUE", "DISPLAYORDER")
SELECT
  ${schemaName}."SELECTLISTOPTION_SEQ".nextval,
  'Installer',
  SYSDATE,
  NULL,
  NULL,
  "ID",
  'add',
  10
FROM ${schemaName}."SELECTLIST" WHERE "NAME" = 'AddPaymentCardAction';

INSERT INTO ${schemaName}."SELECTLISTOPTION"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "SELECTLISTID", "VALUE", "DISPLAYORDER")
SELECT
  ${schemaName}."SELECTLISTOPTION_SEQ".nextval,
  'Installer',
  SYSDATE,
  NULL,
  NULL,
  "ID",
  'addAndSave',
  20
FROM ${schemaName}."SELECTLIST" WHERE "NAME" = 'AddPaymentCardAction';

