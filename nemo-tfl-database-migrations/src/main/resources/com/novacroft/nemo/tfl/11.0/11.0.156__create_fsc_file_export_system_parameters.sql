--liquibase formatted sql

--changeset Novacroft:11.0.156
INSERT INTO ${schemaName}."SYSTEMPARAMETER"
("ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "VALUE", "PURPOSE")
VALUES
(${schemaName}."SYSTEMPARAMETER_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'fsc.exportFile.suffix', '.csv', 'Financial Services Centre export file suffix');

INSERT INTO ${schemaName}."SYSTEMPARAMETER"
("ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "VALUE", "PURPOSE")
VALUES
(${schemaName}."SYSTEMPARAMETER_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'fsc.exportFile.cheque.request.prefix', 'DO', 'Financial Services Centre cheque request export file prefix');

INSERT INTO ${schemaName}."SYSTEMPARAMETER"
("ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "VALUE", "PURPOSE")
VALUES
(${schemaName}."SYSTEMPARAMETER_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'fsc.exportFile.cheque.stop.prefix', 'OCS', 'Financial Services Centre cheques to be cancelled, stopped or reissued export file prefix');

INSERT INTO ${schemaName}."SYSTEMPARAMETER"
("ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "VALUE", "PURPOSE")
VALUES
(${schemaName}."SYSTEMPARAMETER_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'fsc.exportFile.bacs.request.prefix', 'BO', 'Financial Services Centre BACS request export file prefix');

INSERT INTO ${schemaName}."SYSTEMPARAMETER"
("ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "VALUE", "PURPOSE")
VALUES
(${schemaName}."SYSTEMPARAMETER_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'fsc.exportFile.companyCode', '1004', 'Financial Services Centre export file company code field value');

INSERT INTO ${schemaName}."SYSTEMPARAMETER"
("ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "VALUE", "PURPOSE")
VALUES
(${schemaName}."SYSTEMPARAMETER_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'fsc.exportFile.currency', 'GBP', 'Financial Services Centre export file currency field value');

INSERT INTO ${schemaName}."SYSTEMPARAMETER"
("ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "VALUE", "PURPOSE")
VALUES
(${schemaName}."SYSTEMPARAMETER_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'fsc.exportFile.cheque.request.documentType', 'KR', 'Financial Services Centre cheque request export file document type field value');

INSERT INTO ${schemaName}."SYSTEMPARAMETER"
("ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "VALUE", "PURPOSE")
VALUES
(${schemaName}."SYSTEMPARAMETER_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'fsc.exportFile.cheque.request.accountNumber', '9999', 'Financial Services Centre cheque request export file account number field value');

INSERT INTO ${schemaName}."SYSTEMPARAMETER"
("ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "VALUE", "PURPOSE")
VALUES
(${schemaName}."SYSTEMPARAMETER_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'fsc.exportFile.cheque.request.taxCode', 'V3', 'Financial Services Centre cheque request export file tax code field value');


INSERT INTO ${schemaName}."SYSTEMPARAMETER"
("ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "VALUE", "PURPOSE")
VALUES
(${schemaName}."SYSTEMPARAMETER_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'fsc.exportFile.bacs.request.documentType', 'KR', 'Financial Services Centre bacs request export file document type field value');

INSERT INTO ${schemaName}."SYSTEMPARAMETER"
("ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "VALUE", "PURPOSE")
VALUES
(${schemaName}."SYSTEMPARAMETER_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'fsc.exportFile.bacs.request.companyCode', '1004', 'Financial Services Centre bacs request export file company code field value');

INSERT INTO ${schemaName}."SYSTEMPARAMETER"
("ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "VALUE", "PURPOSE")
VALUES
(${schemaName}."SYSTEMPARAMETER_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'fsc.exportFile.bacs.request.currency', 'GBP', 'Financial Services Centre bacs request export file currency field value');

INSERT INTO ${schemaName}."SYSTEMPARAMETER"
("ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "VALUE", "PURPOSE")
VALUES
(${schemaName}."SYSTEMPARAMETER_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'fsc.exportFile.bacs.request.accountNumber', '888888', 'Financial Services Centre bacs request export file account number field value');

INSERT INTO ${schemaName}."SYSTEMPARAMETER"
("ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "VALUE", "PURPOSE")
VALUES
(${schemaName}."SYSTEMPARAMETER_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'fsc.exportFile.bacs.request.vendor.accountNumber', '88888888', 'Financial Services Centre bacs request export file account number field value');

INSERT INTO ${schemaName}."SYSTEMPARAMETER"
("ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "VALUE", "PURPOSE")
VALUES
(${schemaName}."SYSTEMPARAMETER_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'fsc.exportFile.bacs.request.gl.accountNumber', '99999999', 'Financial Services Centre bacs request export file account number field value');


INSERT INTO ${schemaName}."SYSTEMPARAMETER"
("ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "VALUE", "PURPOSE")
VALUES
(${schemaName}."SYSTEMPARAMETER_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'fsc.exportFile.bacs.request.taxCode', 'V3', 'Financial Services Centre bacs request export file tax code field value');

INSERT INTO ${schemaName}."SYSTEMPARAMETER"
("ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "VALUE", "PURPOSE")
VALUES
(${schemaName}."SYSTEMPARAMETER_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'fsc.exportFile.bacs.payee.ref.prefix', 'TOCHECK', 'Financial Services Centre bacs request export file OTV reference prefix field value');
