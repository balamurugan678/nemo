--liquibase formatted sql

--changeset Novacroft:11.0.78
INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'PasswordEmailSent', 'Password Email Sent', 99, 1);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'Call', 'Call added', 99, 0);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'IssueRaised', 'Issue Raised', 99, 0);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'UnsuccessfulCardQuery', 'Unsuccessful Oyster central system product/balance query', 99, 0);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'SuccessfulCardQuery', 'Successful Oyster central system product/balance query', 99, 1);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'PaymentResolved', 'Electronic payment authorised', 99, 1);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'CardPrinted', 'Travel card printed at workstation', 3, 0);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'LetterPrinted', 'Letter printed at workstation', 3, 0);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'PaymentSuccessEmail', 'Confirmation of successful payment email sent', 2, 1);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'PaymentFailedEmail', 'Failed payment email sent', 2, 1);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'FirstIssueCardDespatched', 'First Oyster card despatched', 99, 1);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'FirstIssueRequested', 'First issue requested', 99, 0);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'SubsequentIssueCardDespatched', 'Subsequent issue Oyster card despatched', 99, 1);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'SubsequentIssueRequested', 'Subsequent issue requested', 99, 0);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'LetterDespatched', 'Letter despatched', 99, 1);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'WhitemailReceived', 'Customer mail received by TfL and scanned', 99, 1);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'RefundRequired', 'Refund entry created on Hotlisting prior to Reissue', 99, 0);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'EmailSend', 'Automated Email Send', 9, 1);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'WebAccountCreated', 'Web account created', 1, 1);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'PaymentCancelled', 'Electronic Payment Cancelled', 99, 0);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'PaymentIncomplete', 'Electronic Payment Not Completed', 99, 0);

INSERT INTO ${schemaName}.event
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "DESCRIPTION", "DISPLAYORDER", "DISPLAYONLINE")
VALUES
(${schemaName}.event_seq.nextval, 'Installer', SYSDATE, 'AutoLoadChange', 'Oyster Card auto load change', 99, 0);
