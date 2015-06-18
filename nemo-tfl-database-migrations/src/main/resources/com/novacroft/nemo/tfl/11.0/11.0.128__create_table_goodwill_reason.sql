--liquibase formatted sql

--changeset Novacroft:11.0.128
CREATE TABLE ${schemaName}.goodwillreason
( "ID" NUMBER(38) NOT NULL CONSTRAINT goodwillreason_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "REASONID" NUMBER CONSTRAINT goodwillreason_uk1 UNIQUE
, "DESCRIPTION" VARCHAR2(200) NOT NULL
, "EXTRAVALIDATIONCODE" VARCHAR2(200)
, "TYPE" VARCHAR2(50)
)
TABLESPACE ${tableTablespace}
;

CREATE SEQUENCE ${schemaName}.goodwillreason_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

COMMENT ON TABLE ${schemaName}.goodwillreason IS 'Reason descriptions for goodwill codes';
COMMENT ON COLUMN ${schemaName}.goodwillreason."ID" IS 'Surrogate primary key - generated from GOODWILLREASON_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.goodwillreason."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.goodwillreason."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.goodwillreason."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.goodwillreason."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.goodwillreason."REASONID" IS 'The goodwill reason id';
COMMENT ON COLUMN ${schemaName}.goodwillreason."DESCRIPTION" IS 'The goodwill reason description';
COMMENT ON COLUMN ${schemaName}.goodwillreason."EXTRAVALIDATIONCODE" IS 'The code to use for content for displaying extra validation messages for goodwill reason';
COMMENT ON COLUMN ${schemaName}.goodwillreason."TYPE" IS 'Type of Goodwill Reason (For Standalone Goodwill Refunds its ContactlessPaymentCard, for other goodwill refunds its Oyster';

ALTER TABLE ${schemaName}.ITEM ADD CONSTRAINT item_fk8 FOREIGN KEY ("GOODWILLPAYMENTID") REFERENCES ${schemaName}.goodwillreason("REASONID");