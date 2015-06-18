--liquibase formatted sql

--changeset Novacroft:11.0.73
INSERT INTO ${schemaName}.SHIPPINGMETHOD
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "NAME", "PRICE")
VALUES
(${schemaName}.shippingmethod_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'First class', '000');

INSERT INTO ${schemaName}.SHIPPINGMETHOD
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "NAME", "PRICE")
VALUES
(${schemaName}.shippingmethod_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Recorded Delivery', '565');