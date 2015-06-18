--liquibase formatted sql

--changeset Novacroft:11.0.152
INSERT INTO ${schemaName}."SELECTLIST"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "NAME", "DESCRIPTION")
VALUES
(${schemaName}."SELECTLIST_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'CustomerDeactivationReasons', 'Customer Deactivation Reasons');

INSERT INTO ${schemaName}."SELECTLISTOPTION"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "SELECTLISTID", "VALUE", "DISPLAYORDER")
SELECT
  ${schemaName}."SELECTLISTOPTION_SEQ".nextval,
  'Installer',
  SYSDATE,
  NULL,
  NULL,
  "ID",
  'Customer Deactivation Reason 1',
  10
FROM ${schemaName}."SELECTLIST" WHERE "NAME" = 'CustomerDeactivationReasons';

INSERT INTO ${schemaName}."SELECTLISTOPTION"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "SELECTLISTID", "VALUE", "DISPLAYORDER")
SELECT
  ${schemaName}."SELECTLISTOPTION_SEQ".nextval,
  'Installer',
  SYSDATE,
  NULL,
  NULL,
  "ID",
  'Customer Deactivation Reason 2',
  20
FROM ${schemaName}."SELECTLIST" WHERE "NAME" = 'CustomerDeactivationReasons';

INSERT INTO ${schemaName}."SELECTLISTOPTION"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "SELECTLISTID", "VALUE", "DISPLAYORDER")
SELECT
  ${schemaName}."SELECTLISTOPTION_SEQ".nextval,
  'Installer',
  SYSDATE,
  NULL,
  NULL,
  "ID",
  'Customer Deactivation Reason 3',
  30
FROM ${schemaName}."SELECTLIST" WHERE "NAME" = 'CustomerDeactivationReasons';

INSERT INTO ${schemaName}."SELECTLISTOPTION"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "SELECTLISTID", "VALUE", "DISPLAYORDER")
SELECT
  ${schemaName}."SELECTLISTOPTION_SEQ".nextval,
  'Installer',
  SYSDATE,
  NULL,
  NULL,
  "ID",
  'Other',
  40
FROM ${schemaName}."SELECTLIST" WHERE "NAME" = 'CustomerDeactivationReasons';