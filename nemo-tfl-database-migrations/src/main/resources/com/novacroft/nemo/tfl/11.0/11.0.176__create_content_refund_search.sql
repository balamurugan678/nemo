--liquibase formatted sql

--changeset Novacroft:11.0.176
INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'agentFirstName.firstName.characters.error', 'en_GB', 'Agent first name is less than character limit');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'agentFirstName.invalid.pattern.error', 'en_GB', 'Agent first name should be alphabetic');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'agentLastName.lastName.characters.error', 'en_GB', 'Agent last name is less than character limit');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'agentLastName.invalid.pattern.error', 'en_GB', 'Agent last name should be alphabetic');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'customerFirstName.firstName.characters.error', 'en_GB', 'Customer first name is less than character limit');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'customerFirstName.invalid.pattern.error', 'en_GB', 'Customer first name should be alphabetic');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'customerLastName.lastName.characters.error', 'en_GB', 'Customer last name is less than character limit');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'customerLastName.invalid.pattern.error', 'en_GB', 'Customer last name should be alphabetic');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'sapNumber.invalid.pattern.error', 'en_GB', 'SAP Number should be numeric');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'chequeNumber.invalid.pattern.error', 'en_GB', 'Cheque Serial Number should be numeric');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'bacsNumber.invalid.pattern.error', 'en_GB', 'BACS Reference Number should be numeric');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundsearch.caseNumber', 'en_GB', 'Case Number');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundsearch.agentLastName', 'en_GB', 'Agent Last Name');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundsearch.agentFirstName', 'en_GB', 'Agent First Name');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundsearch.customerLastName', 'en_GB', 'Customer Last Name');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundsearch.customerFirstName', 'en_GB', 'Customer First Name');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundsearch.sapNumber', 'en_GB', 'SAP Number');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundsearch.cardNumber', 'en_GB', 'Card Number');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundsearch.bacsNumber', 'en_GB', 'BACS Reference Number');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundsearch.chequeNumber', 'en_GB', 'Cheque Serial Number');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundsearch.exact', 'en_GB', 'Exact');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundsearch.refund.header', 'en_GB', 'Refunds');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundsearch.customerName', 'en_GB', 'Customer');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundsearch.agent', 'en_GB', 'Agent');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundsearch.paymentMethod', 'en_GB', 'Payment Method');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundsearch.status', 'en_GB', 'Status');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundsearch.dateCreated', 'en_GB', 'Date Created');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundsearch.clearCriteria.button.tip', 'en_GB', 'Clear');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'refundsearch.search.button.tip', 'en_GB', 'Search');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.RefundDetails.header', 'en_GB', 'Refund Details');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.CustomerDetails.header', 'en_GB', 'Customer Details');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.PaymentDetails.header', 'en_GB', 'Payment Details');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.CaseNotes.header', 'en_GB', 'Case Notes');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.refundCase.label', 'en_GB', 'Case Number');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.totalRefund.label', 'en_GB', 'Total Refund');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.createdDate.label', 'en_GB', 'Created Date');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.effectiveDate.label', 'en_GB', 'Effective Date');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.paidDate.label', 'en_GB', 'Paid Date');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.refundStatus.label', 'en_GB', 'Status');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.sapNumber.label', 'en_GB', 'SAP Number');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.customerName.label', 'en_GB', 'Name');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.customerUsername.label', 'en_GB', 'Username');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.customerAddress.label', 'en_GB', 'Address');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.customerCardNumber.label', 'en_GB', 'Card Number');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.caseHistoryDate.label', 'en_GB', 'Date');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.caseHistoryActionMessage.label', 'en_GB', 'Message');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.caseHistoryAgent.label', 'en_GB', 'Agent');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'RefundCase.caseHistoryStatus.label', 'en_GB', 'Status');