--liquibase formatted sql

--changeset Novacroft:11.0.111
INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'CardAdmin.viewJourneyHistory.button.label', 'en_GB', 'View Journey History');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'CardAdmin.viewIncomplete.button.label', 'en_GB', 'Incomplete Journey History');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'CardAdmin.editCardPreferences.button.label', 'en_GB', 'Edit Card Preferences');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'FailedCardProducts.failedCardProducts.header', 'en_GB', 'Failed Card Products');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'FailedCardProducts.products.label', 'en_GB', 'Products');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'FailedCardProducts.payAsYouGoBalance', 'en_GB', 'Pay As You Go (PayG) Balance');


INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'FailedCardProductsSummary.failedCardProductsSummary.header', 'en_GB', 'Failed Cards Products Summary');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'FailedCardProductsSummary.products.label', 'en_GB', 'Products Selected');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'FailedCardProductsSummary.payAsYouGoBalance.label', 'en_GB', 'Pay As You Go (PayG) Balance Selected');


INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'FailedCardProducts.startdate.label', 'en_GB', 'Start Date');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'FailedCardProducts.enddate.label', 'en_GB', 'End Date');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'FailedCardProducts.discount.label', 'en_GB', 'Discount');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'FailedCardProducts.passengertype.label', 'en_GB', 'Passenger Type');