--liquibase formatted sql

--changeset Novacroft:11.0.141
CREATE TABLE ${schemaName}.administrationfee
( "ID" NUMBER(38) NOT NULL CONSTRAINT administrationfee_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "TYPE" VARCHAR2(255) NOT NULL
, "PRICE" NUMBER(9) NOT NULL
)
TABLESPACE ${tableTablespace}
;

CREATE SEQUENCE ${schemaName}.administrationfee_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

COMMENT ON TABLE ${schemaName}.administrationfee IS 'administrationfee';
COMMENT ON COLUMN ${schemaName}.administrationfee."ID" IS 'Surrogate primary key - generated from ADMINISTRATIONFEE_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.administrationfee."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.administrationfee."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.administrationfee."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.administrationfee."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.administrationfee."TYPE" IS 'Administration Fee type (e.g. Failed Card, Lost Stole, Cancel Surrender';
COMMENT ON COLUMN ${schemaName}.administrationfee."PRICE" IS 'Price';

ALTER TABLE ${schemaName}.ITEM ADD CONSTRAINT "ITEM_FK11" FOREIGN KEY ("ADMINISTRATIONFEEID") REFERENCES ${schemaName}."ADMINISTRATIONFEE"("ID");