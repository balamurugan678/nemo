--liquibase formatted sql

--changeset Novacroft:11.0.94
INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundReadyForCollectionEmail.subject.text', 'en_GB', 'TfL Oyster Online Refund Confirmation');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundReadyForCollectionEmail.body.text', 'en_GB', '<p>Your application for a refund of &pound;{0} has been successful.</p><p>This refund is available to be picked up at {1} using Oyster card with number {2} and will be available until {3}.</p><p>Refund calculations take account of factors, including capping, so the refund amount will not always be the difference between the maximum fare and the advertised fare for your journey.  If you still have a query relating to this refund, please call the Oyster helpline on 0845 330 9876 (8 am to 8 pm daily).</p><p>Please note that you must pick up your refund as part of a normal journey: touch your Oyster card on the yellow reader at your selected station and also touch out on the yellow reader at the end of your journey.  If you do not make a normal journey (usual fares and ticketing rates apply), you will be charged a maximum Oyster fare.</p>');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'invalidCubicFileField.error', 'en_GB', 'Field [{0}] contains an invalid value [{1}].');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundPickUpWindowExpiredEmail.subject.text', 'en_GB', 'TfL Oyster Online Refund No Longer Available');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundPickUpWindowExpiredEmail.body.text', 'en_GB', '<p>Your refund of &pound;{0} for Oyster card with number {2} is no longer available at {1} as the last pick-up date was {3}.</p><p>Please contact the Oyster helpline on 0845 330 9876 (8 am to 8 pm daily) to have this refund re-processed.</p>');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundErrorEmail.subject.text', 'en_GB', 'TfL Oyster Online Refund Problem');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundErrorEmail.body.text', 'en_GB', '<p>We have been unable to process your refund request of &pound;{0} for Oyster card with number {2} due to a technical error.  We apologise for the inconvenience.</p><p>Please contact the Oyster helpline on 0845 330 9876 (8 am to 8 pm daily) to have this refund re-processed.</p>');
