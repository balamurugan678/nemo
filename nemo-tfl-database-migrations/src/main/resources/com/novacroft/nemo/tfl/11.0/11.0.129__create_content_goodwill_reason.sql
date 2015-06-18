--liquibase formatted sql

--changeset Novacroft:11.0.129
INSERT INTO ${schemaName}.goodwillreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE", "TYPE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, ${schemaName}.goodwillreason_seq.currval, 'Entry/Exit Pay As You Go Overcharge', 'Goodwillreason.entryExitPayAsYouGoOvercharge.label', 'Oyster');

INSERT INTO ${schemaName}.goodwillreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE", "TYPE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, ${schemaName}.goodwillreason_seq.currval, 'Daily Cap Pay As You Go Overcharge', 'Goodwillreason.dailyCapPayAsYouGoOvercharge.label', 'Oyster');

INSERT INTO ${schemaName}.goodwillreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE", "TYPE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, ${schemaName}.goodwillreason_seq.currval, 'Intermediate Period of non-use', 'Goodwillreason.intermediatePeriodOfNonUse.label', 'Oyster');

INSERT INTO ${schemaName}.goodwillreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE", "TYPE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, ${schemaName}.goodwillreason_seq.currval, 'Complaint', NULL, 'Oyster');

INSERT INTO ${schemaName}.goodwillreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE", "TYPE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, ${schemaName}.goodwillreason_seq.currval, 'Delayed Refund', NULL, 'Oyster');

INSERT INTO ${schemaName}.goodwillreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE", "TYPE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, ${schemaName}.goodwillreason_seq.currval, 'OHL error/misinformation', NULL, 'Oyster');

INSERT INTO ${schemaName}.goodwillreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE", "TYPE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, ${schemaName}.goodwillreason_seq.currval, 'Station error/misinformation', NULL, 'Oyster');

INSERT INTO ${schemaName}.goodwillreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE", "TYPE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, ${schemaName}.goodwillreason_seq.currval, 'Service Delay', 'Goodwillreason.serviceDelay.label', 'Oyster');

INSERT INTO ${schemaName}.goodwillreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE", "TYPE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, ${schemaName}.goodwillreason_seq.currval, 'Other', NULL, 'Oyster');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Goodwillreason.entryExitPayAsYouGoOvercharge.label', 'en_GB', 'Have you checked and confirmed that Entry/Exit Pay As You Go Overcharge occurred?');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Goodwillreason.dailyCapPayAsYouGoOvercharge.label', 'en_GB', 'Have you checked and confirmed that Daily Cap Pay As You Go Overcharge occurred?');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Goodwillreason.intermediatePeriodOfNonUse.label', 'en_GB', 'Can you confirm that Medical Certificate has been provided for Intermediate Period of Non Use?');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Goodwillreason.serviceDelay.label', 'en_GB', 'Can you confirm that Journey was potentially impacted by a dispruption?');

INSERT INTO ${schemaName}.goodwillreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE", "TYPE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, ${schemaName}.goodwillreason_seq.currval, 'Complaint', NULL, 'ContactlessPaymentCard');

INSERT INTO ${schemaName}.goodwillreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE", "TYPE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, ${schemaName}.goodwillreason_seq.currval, 'Delayed Refund', NULL, 'ContactlessPaymentCard');

INSERT INTO ${schemaName}.goodwillreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE", "TYPE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, ${schemaName}.goodwillreason_seq.currval, 'Station Error', NULL, 'ContactlessPaymentCard');

INSERT INTO ${schemaName}.goodwillreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE", "TYPE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, ${schemaName}.goodwillreason_seq.currval, 'Misinformation', NULL, 'ContactlessPaymentCard');

INSERT INTO ${schemaName}.goodwillreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE", "TYPE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, ${schemaName}.goodwillreason_seq.currval, 'Service Delay', 'Goodwillreason.serviceDelay.label', 'ContactlessPaymentCard');

INSERT INTO ${schemaName}.goodwillreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE", "TYPE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, ${schemaName}.goodwillreason_seq.currval, 'Accidental Tap', NULL, 'ContactlessPaymentCard');

INSERT INTO ${schemaName}.goodwillreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE", "TYPE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, ${schemaName}.goodwillreason_seq.currval, 'Mixed CPC Journey', NULL, 'ContactlessPaymentCard');

INSERT INTO ${schemaName}.goodwillreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE", "TYPE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, ${schemaName}.goodwillreason_seq.currval, 'Did not Travel', NULL, 'ContactlessPaymentCard');

INSERT INTO ${schemaName}.goodwillreason
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "REASONID", "DESCRIPTION", "EXTRAVALIDATIONCODE", "TYPE")
VALUES
(${schemaName}.goodwillreason_seq.nextval, 'Installer', SYSDATE, ${schemaName}.goodwillreason_seq.currval, 'Other', NULL, 'ContactlessPaymentCard');