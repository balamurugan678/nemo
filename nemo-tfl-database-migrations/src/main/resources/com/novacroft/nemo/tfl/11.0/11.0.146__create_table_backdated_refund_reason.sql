--liquibase formatted sql

--changeset Novacroft:11.0.146
CREATE TABLE ${schemaName}.backdatedrefundreason
( "ID" NUMBER(38) NOT NULL CONSTRAINT backdatedrefundreason_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "REASONID" NUMBER CONSTRAINT backdatedrefundreason_uk1 UNIQUE
, "DESCRIPTION" VARCHAR2(200) NOT NULL
, "EXTRAVALIDATIONCODE" VARCHAR2(200)
)
TABLESPACE ${tableTablespace}
;

CREATE SEQUENCE ${schemaName}.backdatedrefundreason_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

COMMENT ON TABLE ${schemaName}.backdatedrefundreason IS 'Reason descriptions for backdated refund reason codes';
COMMENT ON COLUMN ${schemaName}.backdatedrefundreason."ID" IS 'Surrogate primary key - generated from BACKDATEDREFUNDREASON_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.backdatedrefundreason."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.backdatedrefundreason."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.backdatedrefundreason."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.backdatedrefundreason."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.backdatedrefundreason."REASONID" IS 'The backdated refund reason id';
COMMENT ON COLUMN ${schemaName}.backdatedrefundreason."DESCRIPTION" IS 'The goodwill reason description';
COMMENT ON COLUMN ${schemaName}.backdatedrefundreason."EXTRAVALIDATIONCODE" IS 'The code to use for content for displaying extra validation messages for goodwill reason';

ALTER TABLE ${schemaName}.ITEM ADD CONSTRAINT item_fk2 FOREIGN KEY ("BACKDATEDREFUNDREASONID") REFERENCES ${schemaName}.backdatedrefundreason("REASONID");
