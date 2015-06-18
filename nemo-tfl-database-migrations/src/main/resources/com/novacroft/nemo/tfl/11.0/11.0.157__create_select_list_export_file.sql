--liquibase formatted sql

--changeset Novacroft:11.0.157
INSERT INTO ${schemaName}."SELECTLIST"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "NAME", "DESCRIPTION")
VALUES
(${schemaName}."SELECTLIST_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'ExportFileType', 'Export File Type');

INSERT INTO ${schemaName}."SELECTLISTOPTION"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "SELECTLISTID", "VALUE", "DISPLAYORDER")
SELECT
  ${schemaName}."SELECTLISTOPTION_SEQ".nextval,
  'Installer',
  SYSDATE,
  NULL,
  NULL,
  "ID",
  'fscChequeRequestFile',
  10
FROM ${schemaName}."SELECTLIST" WHERE "NAME" = 'ExportFileType';


INSERT INTO ${schemaName}."SELECTLISTOPTION"
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "SELECTLISTID", "VALUE", "DISPLAYORDER")
SELECT
  ${schemaName}."SELECTLISTOPTION_SEQ".nextval,
  'Installer',
  SYSDATE,
  NULL,
  NULL,
  "ID",
  'fscBacsRequestFile',
  20
FROM ${schemaName}."SELECTLIST" WHERE "NAME" = 'ExportFileType';
