--liquibase formatted sql

--changeset Novacroft:11.0.84
CREATE TABLE ${schemaName}.payasyougo
( "ID" NUMBER(38) NOT NULL CONSTRAINT payasyougo_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "PAYASYOUGONAME" VARCHAR2(255) NOT NULL
, "TICKETPRICE" NUMBER(9) NOT NULL
, CONSTRAINT "PAYASYOUGO_UK1" UNIQUE ("TICKETPRICE")
)
TABLESPACE ${tableTablespace}
;

CREATE SEQUENCE ${schemaName}.payasyougo_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

COMMENT ON TABLE ${schemaName}.payasyougo IS 'payasyougo';
COMMENT ON COLUMN ${schemaName}.payasyougo."ID" IS 'Surrogate primary key - generated from PAYASYOUGO_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.payasyougo."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.payasyougo."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.payasyougo."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.payasyougo."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.payasyougo."PAYASYOUGONAME" IS 'PayAsYouGo product item Name';
COMMENT ON COLUMN ${schemaName}.payasyougo."TICKETPRICE" IS 'Ticket Price';

ALTER TABLE ${schemaName}.ITEM ADD CONSTRAINT "ITEM_FK6" FOREIGN KEY ("PAYASYOUGOID") REFERENCES ${schemaName}."PAYASYOUGO"("ID")