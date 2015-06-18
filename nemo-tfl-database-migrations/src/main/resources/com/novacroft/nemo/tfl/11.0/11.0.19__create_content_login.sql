--liquibase formatted sql

--changeset Novacroft:11.0.19
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
, 'login.heading'
, 'en_GB'
, 'My account sign in');

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
, 'username.label'
, 'en_GB'
, 'Email/Oyster online username');

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
, 'username.tip'
, 'en_GB'
, '');

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
, 'username.placeholder'
, 'en_GB'
, 'Email/Oyster online username');

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
, 'password.label'
, 'en_GB'
, 'Password');

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
, 'password.tip'
, 'en_GB'
, '');

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
, 'password.placeholder'
, 'en_GB'
, 'Password');

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
, 'signIn.button.label'
, 'en_GB'
, 'Sign in');

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
, 'signIn.button.tip'
, 'en_GB'
, '');

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
, 'forgottenPassword.link'
, 'en_GB'
, 'Forgotten password');

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
, 'forgottenPassword.url'
, 'en_GB'
, 'RequestResetPassword.htm');

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
, 'forgottenPassword.tip'
, 'en_GB'
, 'Click to recover your password');

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
, 'signUp.heading'
, 'en_GB'
, 'Sign up');

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
, 'signUp.link'
, 'en_GB'
, 'Sign up for an account');

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
, 'signUp.url'
, 'en_GB'
, 'OpenAccount.htm');

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
, 'signUp.tip'
, 'en_GB'
, ' ');
