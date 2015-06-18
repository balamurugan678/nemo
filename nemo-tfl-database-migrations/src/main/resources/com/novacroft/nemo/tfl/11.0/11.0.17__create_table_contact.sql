--liquibase formatted sql

--changeset Novacroft:11.0.17
CREATE TABLE ${schemaName}.contact
( "ID" NUMBER(38) NOT NULL CONSTRAINT contact_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "CUSTOMERID" NUMBER(38) NOT NULL
, "NAME" VARCHAR2(50)
, "VALUE" VARCHAR2(50)
, "TYPE" VARCHAR2(50)
, CONSTRAINT contact_fk1 FOREIGN KEY ("CUSTOMERID") REFERENCES ${schemaName}.customer("ID")
)
TABLESPACE ${tableTablespace}
;

CREATE INDEX ${schemaName}.contact_idx1
ON ${schemaName}.contact
("CUSTOMERID")
TABLESPACE ${indexTablespace}
;

CREATE INDEX ${schemaName}.contact_idx2
ON ${schemaName}.contact
( "NAME" )
TABLESPACE ${indexTablespace}
;

CREATE SEQUENCE ${schemaName}.contact_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

COMMENT ON TABLE ${schemaName}.contact IS 'Contact';
COMMENT ON COLUMN ${schemaName}.contact."ID" IS 'Surrogate primary key - generated from CONTACT_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.contact."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.contact."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.contact."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.contact."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.contact."CUSTOMERID" IS 'Reference to Customer';
COMMENT ON COLUMN ${schemaName}.contact."NAME" IS 'TBA';
COMMENT ON COLUMN ${schemaName}.contact."VALUE" IS 'TBA';
COMMENT ON COLUMN ${schemaName}.contact."TYPE" IS 'TBA';

CREATE TABLE ${schemaName}.contact_aud
( "REV" NUMBER NOT NULL
, "REVTYPE" NUMBER NOT NULL
,"ID" NUMBER(38) NOT NULL
, "CREATEDUSERID" VARCHAR2(64)
, "CREATEDDATETIME" DATE
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "CUSTOMERID" NUMBER(38)
, "NAME" VARCHAR2(50)
, "VALUE" VARCHAR2(50)
, "TYPE" VARCHAR2(50)
)
TABLESPACE ${tableTablespace}
;
