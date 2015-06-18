--liquibase formatted sql

--changeset Novacroft:11.0.14
CREATE TABLE ${schemaName}.customerpreferences
( "ID" NUMBER(38) NOT NULL CONSTRAINT customerpreferences_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "CUSTOMERID" NUMBER(38) NOT NULL
, "DPACANTFLCONTACT" NUMBER(1) DEFAULT 0 NOT NULL
, "DPACANTHIRDPARTYCONTACT" NUMBER(1) DEFAULT 0 NOT NULL
, "STATIONID" NUMBER(38)
, "EMAILFREQUENCY" VARCHAR2(20)
, "ATTACHMENTTYPE" VARCHAR2(20)
, "STATEMENTTERMSACCEPTED" NUMBER(1) DEFAULT 0 NOT NULL
, CONSTRAINT customerpreferences_fk1 FOREIGN KEY ("CUSTOMERID") REFERENCES ${schemaName}.customer("ID")
)
TABLESPACE ${tableTablespace}
;

CREATE INDEX ${schemaName}.customerpreferences_idx1
ON ${schemaName}.customerpreferences
("CUSTOMERID")
TABLESPACE ${indexTablespace}
;

CREATE SEQUENCE ${schemaName}.customerpreferences_seq;

COMMENT ON TABLE ${schemaName}.customerpreferences IS 'Customer';
COMMENT ON COLUMN ${schemaName}.customerpreferences."ID" IS 'Surrogate primary key - generated from customerpreferences_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.customerpreferences."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.customerpreferences."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.customerpreferences."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.customerpreferences."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.customerpreferences."CUSTOMERID" IS 'Reference to customer';
COMMENT ON COLUMN ${schemaName}.customerpreferences."DPACANTFLCONTACT" IS 'TBA flag: 0 = false; 1 = true';
COMMENT ON COLUMN ${schemaName}.customerpreferences."DPACANTHIRDPARTYCONTACT" IS 'TBA flag: 0 = false; 1 = true';
COMMENT ON COLUMN ${schemaName}.customerpreferences."STATIONID" IS 'TfL station reference';
COMMENT ON COLUMN ${schemaName}.customerpreferences."EMAILFREQUENCY" IS 'TBA';
COMMENT ON COLUMN ${schemaName}.customerpreferences."ATTACHMENTTYPE" IS 'TBA';
COMMENT ON COLUMN ${schemaName}.customerpreferences."STATEMENTTERMSACCEPTED" IS 'TBA flag: 0 = false; 1 = true';

CREATE TABLE ${schemaName}.customerpreferences_aud
( "REV" NUMBER NOT NULL
, "REVTYPE" NUMBER NOT NULL
, "ID" NUMBER(38) NOT NULL
, "CREATEDUSERID" VARCHAR2(64)
, "CREATEDDATETIME" DATE
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "CUSTOMERID" NUMBER(38)
, "DPACANTFLCONTACT" NUMBER(1)
, "DPACANTHIRDPARTYCONTACT" NUMBER(1)
, "STATIONID" NUMBER(38)
, "EMAILFREQUENCY" VARCHAR2(20)
, "ATTACHMENTTYPE" VARCHAR2(20)
, "STATEMENTTERMSACCEPTED" NUMBER(1)
)
TABLESPACE ${tableTablespace}
;
