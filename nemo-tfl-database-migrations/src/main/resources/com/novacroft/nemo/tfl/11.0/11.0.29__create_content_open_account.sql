--liquibase formatted sql

--changeset Novacroft:11.0.29
INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'openAccount.breadcrumb.url', 'en_GB', 'OpenAccount.htm');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'openAccount.breadcrumb.tip', 'en_GB', 'Open Account');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'openAccount.breadcrumb.link', 'en_GB', 'Open Account');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'openaccountexist.title', 'en_GB',
'Oyster online - Transport for London - Create account - enter card');


INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'openaccount.title', 'en_GB',
'Oyster online - Transport for London - Create account - enter card');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'openaccount.heading', 'en_GB', 'Open an account');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'openaccount.upper.text', 'en_GB',
'<ul>
  <li>A quick and easy way to top up your Oyster card and/or renew your Travelcard</li>
  <li>Once you set up your account you don''t need to complete an online form with every purchase</li>
  <li>Your Oyster card will be protected against loss or theft</li>
  <li>You can view your journey history for up to the past eight weeks</li>
  <li>You will receive email updates on planned disruptions to your regular route</li>
</ul>
<p class="bold"><strong>Please note that you cannot buy discounted or child-rate Travelcards from Oyster online.</strong></p>');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'cardNumber.alreadyUsed.error', 'en_GB',
'The card number you entered is already attached to an online account.');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'openaccountexist.accountexist.text', 'en_GB',
'The card number you entered is already attached to an online account.
Please <a href="OysterHome.htm"> sign in </a> to continue the purchase,
or <a href="OpenAccount.htm"> re-enter your card number.</a>');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'openaccount.carddetails.heading', 'en_GB', 'Card Details');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'cardNumber.mandatoryFieldEmpty.error', 'en_GB',
'You did not enter an Oyster card number. Please enter an Oyster card number and try again.');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'cardNumber.invalid.pattern.error', 'en_GB',
'The Oyster card number you entered was incorrect. Please check it and try again. This number can be found on the back of your Oyster card.');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'cardNumber.invalid.error', 'en_GB',
'The Oyster card number you entered was incorrect. Please check it and try again. This number can be found on the back of your Oyster card.');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'cardNumber.notAvailable.error', 'en_GB',
'The Oyster card is not issued.');
