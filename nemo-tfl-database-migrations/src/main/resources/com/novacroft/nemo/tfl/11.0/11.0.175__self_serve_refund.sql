--liquibase formatted sql

--changeset Novacroft:11.0.175
INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'SelfServeRefund.submitted', 'en_GB',
'Your have successfully submitted your journey details and refund processing has been initiated');


INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'IncompleteJourney.description', 'en_GB',
'On  {0},   you {1} at  {2}.');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'SSR.failed.description', 'en_GB',
'Refund processing was not successful.');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'AutofillNotified.Commenced.description', 'en_GB',
'Refund processing has been initiated.');


INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'InCompleteJourneys.journeys.heading', 'en_GB',
'Incomplete Journeys.');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'AutofillNotified.Completion.description', 'en_GB',
'Refund processing successfully completed.');


INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'UnFinishedJourneyNotAvailableForCompletion.description', 'en_GB',
'This journey has some data problems and is not available for completion');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'selectCard.button.text', 'en_GB',
'Select a card');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'CompleteJourneyDetails.breadcrumb.link', 'en_GB', 'Complete Journey');


INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'ContentCode.COMPLETED_JOURNEY_PROCESS_DATE.headingCode', 'en_GB', 'Processing Date');


INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'ContentCode.COMPLETED_JOURNEY_DATE.headingCode', 'en_GB', 'Journey Date');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'ContentCode.COMPLETED_JOURNEY_STATION.headingCode', 'en_GB', 'Start/Exit Station');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'ContentCode.COMPLETED_JOURNEY_PROVIDED_STATION.headingCode', 'en_GB', 'Provided Station');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'ContentCode.COMPLETED_JOURNEY_PICKUP_STATION.headingCode', 'en_GB', 'Pick-up Station');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'ContentCode.COMPLETED_JOURNEY_REFUND_AMOUNT.headingCode', 'en_GB', 'Refund Amount');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'ContentCode.COMPLETED_JOURNEY_USER_ID.headingCode', 'en_GB', 'User Id');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'ContentCode.COMPLETED_JOURNEY_ERROR.headingCode', 'en_GB', 'Error Description');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'ContentCode.COMPLETED_JOURNEY_REFUND_REASON.headingCode', 'en_GB', 'Refund Reason');


INSERT INTO ${schemaName}.content
( "ID"
, "CREATEDUSERID"
, "CREATEDDATETIME"
, "MODIFIEDUSERID"
, "MODIFIEDDATETIME"
, "CODE"
, "LOCALE"
, "CONTENT")
VALUES
(${schemaName}.content_seq.nextval
, 'Installer'
, SYSDATE
, NULL
, NULL
, 'completeJourneyTouchInStation.label'
, 'en_GB'
, 'Where did you forget to touch in ? ');

INSERT INTO ${schemaName}.content
( "ID"
, "CREATEDUSERID"
, "CREATEDDATETIME"
, "MODIFIEDUSERID"
, "MODIFIEDDATETIME"
, "CODE"
, "LOCALE"
, "CONTENT")
VALUES
(${schemaName}.content_seq.nextval
, 'Installer'
, SYSDATE
, NULL
, NULL
, 'completeJourneyTouchOutStation.label'
, 'en_GB'
, 'Where did you forget to touch out? ');

INSERT INTO ${schemaName}.content
( "ID"
, "CREATEDUSERID"
, "CREATEDDATETIME"
, "MODIFIEDUSERID"
, "MODIFIEDDATETIME"
, "CODE"
, "LOCALE"
, "CONTENT")
VALUES
(${schemaName}.content_seq.nextval
, 'Installer'
, SYSDATE
, NULL
, NULL
, 'completeJourneyPickUpStation.label'
, 'en_GB'
, 'Select a station to pick up');

INSERT INTO ${schemaName}.content
( "ID"
, "CREATEDUSERID"
, "CREATEDDATETIME"
, "MODIFIEDUSERID"
, "MODIFIEDDATETIME"
, "CODE"
, "LOCALE"
, "CONTENT")
VALUES
(${schemaName}.content_seq.nextval
, 'Installer'
, SYSDATE
, NULL
, NULL
, 'completeJourneyMissingTapReason.label'
, 'en_GB'
, 'Select a reason for missing');


INSERT INTO ${schemaName}."SYSTEMPARAMETER"
("ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "VALUE", "PURPOSE")
VALUES
(${schemaName}."SYSTEMPARAMETER_SEQ".nextval, 'Installer', SYSDATE, NULL, NULL, 'noOfDaysBackForIncompleteJourney', '56', 'Days back for finding unFinished jouneys');

