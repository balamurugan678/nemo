--liquibase formatted sql

--changeset Novacroft:11.0.13
CREATE TABLE ${schemaName}.customer
( "ID" NUMBER(38) NOT NULL CONSTRAINT customer_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "STATUS" VARCHAR2(30)
, "TITLE" VARCHAR2(10)
, "FIRSTNAME" VARCHAR2(50)
, "INITIALS" VARCHAR2(10)
, "LASTNAME" VARCHAR2(50)
, "DECEASED" NUMBER(1) DEFAULT 0 NOT NULL
, "ANONYMISED" NUMBER(1) DEFAULT 0 NOT NULL
, "READONLY" NUMBER(1) DEFAULT 0 NOT NULL
, "ADDRESSID" NUMBER(38)
, "USERNAME" VARCHAR2(50)
, "EMAILADDRESS" VARCHAR2(150)
, "SALT" VARCHAR2(32)
, "PASSWORD" VARCHAR2(128)
, "PHOTOCARDNUMBER" VARCHAR2(20)
, "UNFORMATTEDEMAILADDRESS" VARCHAR2(150)
, "PASSWORDCHANGEREQUIRED" NUMBER(1) DEFAULT 0 NOT NULL
, "DEACTIVATED" NUMBER(1) DEFAULT 0 NOT NULL
, "DEACTIVATIONREASON" VARCHAR2(150)
, CONSTRAINT customer_fk1 FOREIGN KEY ("ADDRESSID") REFERENCES ${schemaName}.address("ID")
)
TABLESPACE ${tableTablespace}
;

CREATE INDEX ${schemaName}.customer_idx1
ON ${schemaName}.customer
( "LASTNAME"
, "FIRSTNAME"
, "INITIALS")
TABLESPACE ${indexTablespace}
;

CREATE INDEX ${schemaName}.customer_idx2
ON ${schemaName}.customer
("FIRSTNAME")
TABLESPACE ${indexTablespace}
;

CREATE INDEX ${schemaName}.customer_idx3
ON ${schemaName}.customer
("LASTNAME")
TABLESPACE ${indexTablespace}
;

CREATE SEQUENCE ${schemaName}.customer_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

COMMENT ON TABLE ${schemaName}.customer IS 'Customer';
COMMENT ON COLUMN ${schemaName}.customer."ID" IS 'Surrogate primary key - generated from CUSTOMER_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.customer."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.customer."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.customer."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.customer."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.customer."STATUS" IS 'TBA';
COMMENT ON COLUMN ${schemaName}.customer."TITLE" IS 'Title';
COMMENT ON COLUMN ${schemaName}.customer."FIRSTNAME" IS 'First Name';
COMMENT ON COLUMN ${schemaName}.customer."INITIALS" IS 'Middle initials';
COMMENT ON COLUMN ${schemaName}.customer."LASTNAME" IS 'Last Name';
COMMENT ON COLUMN ${schemaName}.customer."DECEASED" IS 'Deceased flag: 0 = false; 1 = true';
COMMENT ON COLUMN ${schemaName}.customer."ANONYMISED" IS 'TBA flag: 0 = false; 1 = true';
COMMENT ON COLUMN ${schemaName}.customer."READONLY" IS 'TBA flag: 0 = false; 1 = true';
COMMENT ON COLUMN ${schemaName}.customer."ADDRESSID" IS 'Reference to Address';
COMMENT ON COLUMN ${schemaName}.customer."USERNAME" IS 'Account holder''s TfL web site user name';
COMMENT ON COLUMN ${schemaName}.customer."EMAILADDRESS" IS 'Account holder''s electronic mail address';
COMMENT ON COLUMN ${schemaName}.customer."SALT" IS 'Password SALT';
COMMENT ON COLUMN ${schemaName}.customer."PASSWORD" IS 'hashed password';
COMMENT ON COLUMN ${schemaName}.customer."PHOTOCARDNUMBER" IS 'TBA';
COMMENT ON COLUMN ${schemaName}.customer."UNFORMATTEDEMAILADDRESS" IS 'TBA';
COMMENT ON COLUMN ${schemaName}.customer."ANONYMISED" IS 'TBA flag: 0 = false; 1 = true';
COMMENT ON COLUMN ${schemaName}.customer."READONLY" IS 'TBA flag: 0 = false; 1 = true';
COMMENT ON COLUMN ${schemaName}.customer."PASSWORDCHANGEREQUIRED" IS 'TBA flag: 0 = false; 1 = true';
COMMENT ON COLUMN ${schemaName}.customer."DEACTIVATED" IS 'TBA flag: 0 = false; 1 = true';
COMMENT ON COLUMN ${schemaName}.customer."DEACTIVATIONREASON" IS 'Reason for Deactivating Web Account';


CREATE TABLE ${schemaName}.customer_AUD
( "REV" NUMBER NOT NULL
, "REVTYPE" NUMBER NOT NULL
,"ID" NUMBER(38) NOT NULL
, "CREATEDUSERID" VARCHAR2(64)
, "CREATEDDATETIME" DATE
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "STATUS" VARCHAR2(30)
, "TITLE" VARCHAR2(10)
, "FIRSTNAME" VARCHAR2(50)
, "INITIALS" VARCHAR2(10)
, "LASTNAME" VARCHAR2(50)
, "DECEASED" NUMBER(1)
, "ANONYMISED" NUMBER(1)
, "READONLY" NUMBER(1)
, "ADDRESSID" NUMBER(38)
, "USERNAME" VARCHAR2(50)
, "EMAILADDRESS" VARCHAR2(150)
, "SALT" VARCHAR2(32)
, "PASSWORD" VARCHAR2(128)
, "PHOTOCARDNUMBER" VARCHAR2(20)
, "UNFORMATTEDEMAILADDRESS" VARCHAR2(150)
, "PASSWORDCHANGEREQUIRED" NUMBER(1) DEFAULT 0 NOT NULL
, "DEACTIVATED" NUMBER(1) DEFAULT 0 NOT NULL
, "DEACTIVATIONREASON" VARCHAR2(150)
)
TABLESPACE ${tableTablespace}
;


CREATE TABLE ${schemaName}.metaphoneencoding
( "ID" NUMBER(38) NOT NULL CONSTRAINT metaphoneencoding_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "CUSTOMERID" NUMBER NOT NULL
, "FIRSTNAME" VARCHAR2(100)
, "LASTNAME" VARCHAR2(100)
, CONSTRAINT metaphoneencoding_fk1 FOREIGN KEY ("CUSTOMERID") REFERENCES ${schemaName}.customer("ID"))
TABLESPACE ${tableTablespace}
;

CREATE INDEX ${schemaName}.metaphoneencoding_idx1
ON ${schemaName}.metaphoneencoding
("FIRSTNAME")
TABLESPACE ${indexTablespace}
;

CREATE INDEX ${schemaName}.metaphoneencoding_idx2
ON ${schemaName}.metaphoneencoding
("LASTNAME")
TABLESPACE ${indexTablespace}
;

CREATE SEQUENCE ${schemaName}.metaphoneencoding_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

COMMENT ON TABLE ${schemaName}.metaphoneencoding IS 'Metaphone encoding for sound-alike searching';
COMMENT ON COLUMN ${schemaName}.metaphoneencoding."ID" IS 'Surrogate primary key - generated from CUSTOMER_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.metaphoneencoding."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.metaphoneencoding."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.metaphoneencoding."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.metaphoneencoding."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.metaphoneencoding."CUSTOMERID" IS 'Reference to Customer';
COMMENT ON COLUMN ${schemaName}.metaphoneencoding."FIRSTNAME" IS 'Metaphone first name';
COMMENT ON COLUMN ${schemaName}.metaphoneencoding."LASTNAME" IS 'Metaphone last name';
