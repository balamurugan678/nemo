--liquibase formatted sql

--changeset Novacroft:11.0.15
CREATE TABLE ${schemaName}.webaccount
( "ID" NUMBER(38) NOT NULL CONSTRAINT webaccount_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "CUSTOMERID" NUMBER(38)
, "USERNAME" VARCHAR2(50 )
, "EMAILADDRESS" VARCHAR2(150)
, "SALT" VARCHAR2(32)
, "PASSWORD" VARCHAR2(128)
, "PHOTOCARDNUMBER" VARCHAR2(20)
, "UNFORMATTEDEMAILADDRESS" VARCHAR2(150)
, "ANONYMISED" NUMBER(1) DEFAULT 0 NOT NULL
, "READONLY" NUMBER(1) DEFAULT 0 NOT NULL
, "PASSWORDCHANGEREQUIRED" NUMBER(1) DEFAULT 0 NOT NULL
, "DEACTIVATED" NUMBER(1) DEFAULT 0 NOT NULL
, "DEACTIVATIONREASON" VARCHAR2(150)
, CONSTRAINT webaccount_fk1 FOREIGN KEY ("CUSTOMERID") REFERENCES ${schemaName}.customer("ID")
)
TABLESPACE ${indexTablespace}
;

CREATE INDEX ${schemaName}.webaccount_idx1
ON ${schemaName}.webaccount
("CUSTOMERID")
TABLESPACE ${indexTablespace}
;

CREATE INDEX ${schemaName}.webaccount_idx2
ON ${schemaName}.webaccount
("USERNAME")
TABLESPACE ${indexTablespace}
;

CREATE INDEX ${schemaName}.webaccount_idx3
ON ${schemaName}.webaccount
("EMAILADDRESS")
TABLESPACE ${indexTablespace}
;

CREATE INDEX ${schemaName}.webaccount_idx4
ON ${schemaName}.webaccount
("UNFORMATTEDEMAILADDRESS")
TABLESPACE ${indexTablespace}
;

CREATE INDEX ${schemaName}.webaccount_idx5
ON ${schemaName}.webaccount
(LOWER("EMAILADDRESS"))
TABLESPACE ${indexTablespace}
;

CREATE INDEX ${schemaName}.webaccount_idx6
ON ${schemaName}.webaccount
(LOWER("USERNAME"))
TABLESPACE ${indexTablespace}
;

CREATE SEQUENCE ${schemaName}.webaccount_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

COMMENT ON TABLE ${schemaName}.webaccount IS 'Web Account';
COMMENT ON COLUMN ${schemaName}.webaccount."ID" IS 'Surrogate primary key - generated from webaccount_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.webaccount."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.webaccount."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.webaccount."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.webaccount."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.webaccount."CUSTOMERID" IS 'Reference to customer';
COMMENT ON COLUMN ${schemaName}.webaccount."USERNAME" IS 'Account holder''s TfL web site user name';
COMMENT ON COLUMN ${schemaName}.webaccount."EMAILADDRESS" IS 'Account holder''s electronic mail address';
COMMENT ON COLUMN ${schemaName}.webaccount."SALT" IS 'Password SALT';
COMMENT ON COLUMN ${schemaName}.webaccount."PASSWORD" IS 'hashed password';
COMMENT ON COLUMN ${schemaName}.webaccount."PHOTOCARDNUMBER" IS 'TBA';
COMMENT ON COLUMN ${schemaName}.webaccount."UNFORMATTEDEMAILADDRESS" IS 'TBA';
COMMENT ON COLUMN ${schemaName}.webaccount."ANONYMISED" IS 'TBA flag: 0 = false; 1 = true';
COMMENT ON COLUMN ${schemaName}.webaccount."READONLY" IS 'TBA flag: 0 = false; 1 = true';
COMMENT ON COLUMN ${schemaName}.webaccount."PASSWORDCHANGEREQUIRED" IS 'TBA flag: 0 = false; 1 = true';
COMMENT ON COLUMN ${schemaName}.webaccount."DEACTIVATED" IS 'TBA flag: 0 = false; 1 = true';
COMMENT ON COLUMN ${schemaName}.webaccount."DEACTIVATIONREASON" IS 'Reason for Deactivating Web Account';

CREATE TABLE ${schemaName}.webaccount_aud
(  "REV" NUMBER NOT NULL
, "REVTYPE" NUMBER NOT NULL
,"ID" NUMBER(38) NOT NULL
, "CREATEDUSERID" VARCHAR2(64)
, "CREATEDDATETIME" DATE
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "CUSTOMERID" NUMBER(38)
, "USERNAME" VARCHAR2(50 )
, "EMAILADDRESS" VARCHAR2(150)
, "SALT" VARCHAR2(32)
, "PASSWORD" VARCHAR2(128)
, "PHOTOCARDNUMBER" VARCHAR2(20)
, "UNFORMATTEDEMAILADDRESS" VARCHAR2(150)
, "ANONYMISED" NUMBER(1) DEFAULT 0
, "READONLY" NUMBER(1) DEFAULT 0
, "PASSWORDCHANGEREQUIRED" NUMBER(1) DEFAULT 0
, "DEACTIVATED" NUMBER(1) DEFAULT 0
, "DEACTIVATIONREASON" VARCHAR2(150)
)
TABLESPACE ${indexTablespace}
;
