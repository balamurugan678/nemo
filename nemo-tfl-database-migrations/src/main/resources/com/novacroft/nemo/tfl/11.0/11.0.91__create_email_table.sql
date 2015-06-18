--liquibase formatted sql

--changeset Novacroft:11.0.91
CREATE TABLE ${schemaName}.emails
( "ID" NUMBER NOT NULL CONSTRAINT emails_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "REFERENCE" NUMBER(10)
, "REFERENCETYPE" CHAR(1)
, "EMAILID" NUMBER
, "MESSAGE" BLOB NOT NULL
, "ATTEMPTS" NUMBER(4) NOT NULL
, "ERRORMESSAGE" VARCHAR2(4000) NOT NULL
)
TABLESPACE ${tableTablespace}
;

CREATE INDEX ${schemaName}.emails_ix1
ON ${schemaName}.emails
("REFERENCE")
TABLESPACE ${indexTablespace}
;

CREATE SEQUENCE emails_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

COMMENT ON TABLE ${schemaName}.emails IS 'Storage of emails that are waiting to be sent by the system';
COMMENT ON COLUMN ${schemaName}.emails."ID" IS 'Surrogate primary key - generated from EMAILS_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.emails."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.emails."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.emails."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.emails."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.emails."REFERENCE" IS 'Reference to customer';
COMMENT ON COLUMN ${schemaName}.emails."REFERENCETYPE" IS 'Reference type W: Webaccount C: Customer';
COMMENT ON COLUMN ${schemaName}.emails."EMAILID" IS 'Reference to email table';
COMMENT ON COLUMN ${schemaName}.emails."MESSAGE" IS 'Serialized version of the mime message';
COMMENT ON COLUMN ${schemaName}.emails."ATTEMPTS" IS 'Number of attempts that the system has tried to send the emails';
COMMENT ON COLUMN ${schemaName}.emails."ERRORMESSAGE" IS 'Error Message returned by system';

/*
CREATE TABLE ${schemaName}.email
( "ID" NUMBER NOT NULL CONSTRAINT email_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "CODE" VARCHAR2(100)
, "DESCRIPTION" VARCHAR2(1000)
, "DEFAULTTO" VARCHAR2(200) NOT NULL
, "CC" VARCHAR2(200)  NULL
, "BCC" VARCHAR2(200) NULL
, "FROMADDRESS" VARCHAR2(200) NOT NULL
, "SUBJECT" VARCHAR2(1000) NULL
, "CONTENTCODE" VARCHAR2(50) NULL
, "HTML" NUMBER(1) NOT NULL
)
TABLESPACE ${tableTablespace}
;

CREATE INDEX ${schemaName}.email_ix1
ON ${schemaName}.email
("CODE")
TABLESPACE ${indexTablespace}
;

CREATE SEQUENCE email_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

COMMENT ON TABLE ${schemaName}.email IS 'Storage of emails that can be used';
COMMENT ON COLUMN ${schemaName}.email."ID" IS 'Surrogate primary key - generated from EMAIL_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.email."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.email."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.email."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.email."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.email."CODE" IS 'Code to reference email';
COMMENT ON COLUMN ${schemaName}.email."DESCRIPTION" IS 'Description of email';
COMMENT ON COLUMN ${schemaName}.email."DEFAULTTO" IS 'Default to address for email';
COMMENT ON COLUMN ${schemaName}.email."CC" IS 'CC address';
COMMENT ON COLUMN ${schemaName}.email."BCC" IS 'BCC address';
COMMENT ON COLUMN ${schemaName}.email."FROMADDRESS" IS 'The email address the email is from';
COMMENT ON COLUMN ${schemaName}.email."SUBJECT" IS 'Subject of email';
COMMENT ON COLUMN ${schemaName}.email."CONTENTCODE" IS 'Reference to content table for email body ';
COMMENT ON COLUMN ${schemaName}.email."HTML" IS '1: Html message, 0: Plain Text';
*/