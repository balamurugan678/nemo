--liquibase formatted sql

--changeset Novacroft:11.0.98
INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'TopUpTicket.title', 'en_GB',
'Oyster online - Transport for London - Ticket Renewals - select ticket');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'TopUpTicket.heading', 'en_GB', 'Add/renew/top-up ticket');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'UserCart.title', 'en_GB',
'Oyster online - Transport for London - Add/renew/top-up ticket - shopping basket');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'UserCart.shoppingBasket.heading', 'en_GB', 'Shopping Basket');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'TopUpTicket.upper.text', 'en_GB',
'You are about to add, renew or top-up a ticket for the card listed above. If this is not what you want to do, please <a href="OrderOysterCard.htm">buy a new Oyster card.</a>
 <p class="bold">Please note: If your Oyster card has stopped working, please do not add/renew/top-up with new tickets. 
Take the card to any Tube station, with proof of your address, so it can be replaced.</p>');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'TopUpTicket.selectTicketType.heading', 'en_GB',
'Select ticket type');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'TopUpTicket.payasyougo.label', 'en_GB',
'Pay as you go');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'TopUpTicket.payasyougoAutoTopUp.label', 'en_GB',
'Pay as you go with Auto top-up');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'TopUpTicket.travelcard.label', 'en_GB',
'Travelcard');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'TopUpTicket.creditBalance.label', 'en_GB',
'Top up my pay as you go balance with');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'TopUpTicket.autoTopUpCreditBalance.label', 'en_GB',
'Top up my pay as you go balance with');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'TopUpTicket.autoTopUpInfo.text', 'en_GB',
'In order to set up Auto top-up you need to make an initial purchase of at least £10 pay as you go credit.');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'TopUpTicket.travelCardInfo.text', 'en_GB',
'You can''t buy discounted or child-rate Travelcards from Oyster online.');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'TopUpTicket.startDate.label', 'en_GB', 'Start date');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'TopUpTicket.endDate.label', 'en_GB', 'End date');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'TopUpTicket.endDate.placeholder', 'en_GB', 'dd/mm/yyyy');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'TopUpTicket.startZone.label', 'en_GB', 'From zone');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'TopUpTicket.endZone.label', 'en_GB', 'To zone');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'emailReminder.label', 'en_GB', 'I would like an email reminder');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'ticketType.mandatoryFieldEmpty.error', 'en_GB',
'You have not selected ticket type. Please check your entry and try again.');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'autoTopUpCreditBalance.mandatoryFieldEmpty.error', 'en_GB',
'You have not selected an amount of pay as you go credit. Please check your entry and try again.');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'cartItemCmd.autoTopUpAmt.invalid.amount.error', 'en_GB',
'You have not specified an Auto top-up amount. Please check your entry and try again.');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'autoTopUpCreditBalance.invalid.amount.error', 'en_GB',
'In order to set up Auto top-up you need to make purchase of at least £10 pay as you go credit.');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'creditBalance.payAsYouGoCreditGreaterThanLimit.error', 'en_GB',
'Pay As You Go Credit is greater than &pound;90.');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
( ${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'autoTopUpCreditBalance.payAsYouGoCreditGreaterThanLimit.error', 'en_GB',
'Pay As You Go Credit is greater than &pound;90.');

