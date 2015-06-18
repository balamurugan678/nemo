--liquibase formatted sql

--changeset Novacroft:11.0.31
CREATE TABLE ${schemaName}.cardpreferences
( "ID" NUMBER(38) NOT NULL CONSTRAINT cardpreferences_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "CARDID" NUMBER(38) NOT NULL
, "STATIONID" NUMBER(38)
, "EMAILFREQUENCY" VARCHAR2(20)
, "ATTACHMENTTYPE" VARCHAR2(20)
, "STATEMENTTERMSACCEPTED" NUMBER(1) DEFAULT 0 NOT NULL
, CONSTRAINT cardpreferences_fk1 FOREIGN KEY ("CARDID") REFERENCES ${schemaName}.card("ID")
)
TABLESPACE ${tableTablespace}
;

CREATE INDEX ${schemaName}.cardpreferences_idx1
ON ${schemaName}.cardpreferences
("CARDID")
TABLESPACE ${indexTablespace}
;

CREATE SEQUENCE ${schemaName}.cardpreferences_seq;

COMMENT ON TABLE ${schemaName}.cardpreferences IS 'Card Preferences';
COMMENT ON COLUMN ${schemaName}.cardpreferences."ID" IS 'Surrogate primary key - generated from cardpreferences_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.cardpreferences."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.cardpreferences."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.cardpreferences."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.cardpreferences."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.cardpreferences."CARDID" IS 'Reference to card';
COMMENT ON COLUMN ${schemaName}.cardpreferences."STATIONID" IS 'TfL station reference';
COMMENT ON COLUMN ${schemaName}.cardpreferences."EMAILFREQUENCY" IS 'Journey history statement email frequency, eg Weekly, Monthly, etc';
COMMENT ON COLUMN ${schemaName}.cardpreferences."ATTACHMENTTYPE" IS 'Journey history statement email attachment type, eg PDF, CVS, etc';
COMMENT ON COLUMN ${schemaName}.cardpreferences."STATEMENTTERMSACCEPTED" IS 'TBA flag: 0 = false; 1 = true';

