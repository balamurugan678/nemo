--liquibase formatted sql

--changeset Novacroft:11.0.1
CREATE TABLE ${schemaName}.content
( "ID" NUMBER NOT NULL CONSTRAINT content_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "CODE" VARCHAR2(128) NOT NULL
, "LOCALE" VARCHAR2(32) NOT NULL
, "CONTENT" VARCHAR2(4000)
, CONSTRAINT content_uk1 UNIQUE ("CODE", "LOCALE")
)
TABLESPACE ${tableTablespace}
;

CREATE SEQUENCE ${schemaName}.content_seq;

COMMENT ON TABLE ${schemaName}.content IS 'Managed page content';
COMMENT ON COLUMN ${schemaName}.content."ID" IS 'Surrogate primary key - generated from CONTENT_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.content."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.content."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.content."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.content."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.content."CODE" IS 'Code - how content is referenced in application';
COMMENT ON COLUMN ${schemaName}.content."LOCALE" IS 'ISO locale';
COMMENT ON COLUMN ${schemaName}.content."CONTENT" IS 'Content to display - may include parameters';
