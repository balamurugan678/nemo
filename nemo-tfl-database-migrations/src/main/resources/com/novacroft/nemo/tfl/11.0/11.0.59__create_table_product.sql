--liquibase formatted sql

--changeset Novacroft:11.0.57
CREATE TABLE ${schemaName}.product
( "ID" NUMBER(38) NOT NULL CONSTRAINT product_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "RATE" VARCHAR2(50) NOT NULL
, "PRODUCTCODE" VARCHAR2(40) NOT NULL
, "PRODUCTNAME" VARCHAR2(255) NOT NULL
, "TICKETPRICE" NUMBER(9) NOT NULL
, "STARTZONE" NUMBER(9)
, "ENDZONE" NUMBER(9)
, "DURATION" VARCHAR2(12)
, "TYPE" VARCHAR2(12)
, CONSTRAINT "PRODUCT_UK1" UNIQUE ("PRODUCTCODE")
)
TABLESPACE ${tableTablespace}
;

CREATE INDEX ${schemaName}."PRODUCT_IDX1"
ON ${schemaName}."PRODUCT"
(LOWER("PRODUCTNAME"))
TABLESPACE ${indexTablespace}
;

CREATE SEQUENCE ${schemaName}.product_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

COMMENT ON TABLE ${schemaName}.product IS 'Product';
COMMENT ON COLUMN ${schemaName}.product."ID" IS 'Surrogate primary key - generated from PRODUCT_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.product."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.product."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.product."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.product."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.product."RATE" IS 'TFL Scheme Name';
COMMENT ON COLUMN ${schemaName}.product."PRODUCTCODE" IS 'Product Code';
COMMENT ON COLUMN ${schemaName}.product."PRODUCTNAME" IS 'Product Name';
COMMENT ON COLUMN ${schemaName}.product."TICKETPRICE" IS 'Ticket Price';
COMMENT ON COLUMN ${schemaName}.product."STARTZONE" IS 'Start zone for the ticket';
COMMENT ON COLUMN ${schemaName}.product."ENDZONE" IS 'End zone for the ticket';
COMMENT ON COLUMN ${schemaName}.product."DURATION" IS 'Ticket duration 7Day 1Month 3Month 6Month Annual';
COMMENT ON COLUMN ${schemaName}.product."TYPE" IS 'Ticket Type: Bus, Travelcard, Tram, Other';