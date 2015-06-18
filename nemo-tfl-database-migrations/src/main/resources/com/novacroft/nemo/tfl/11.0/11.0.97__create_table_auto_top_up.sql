--liquibase formatted sql

--changeset Novacroft:11.0.97
CREATE TABLE ${schemaName}."AUTOTOPUP"
("ID" NUMBER(38) NOT NULL CONSTRAINT "AUTOTOPUP_PK" PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "AUTOTOPUPAMOUNT" NUMBER(9) NOT NULL
, CONSTRAINT "AUTOTOPUP_UK1" UNIQUE ("AUTOTOPUPAMOUNT")
)
TABLESPACE ${tableTablespace}
;

CREATE SEQUENCE ${schemaName}.AUTOTOPUP_SEQ START WITH 1 INCREMENT BY 1 NOMAXVALUE;

COMMENT ON TABLE ${schemaName}."AUTOTOPUP" IS 'Base table for auto top-up amounts';
COMMENT ON COLUMN ${schemaName}."AUTOTOPUP"."ID" IS 'Surrogate primary key - generated from AUTOTOPUP_SEQ sequence';
COMMENT ON COLUMN ${schemaName}."AUTOTOPUP"."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}."AUTOTOPUP"."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}."AUTOTOPUP"."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}."AUTOTOPUP"."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}."AUTOTOPUP"."AUTOTOPUPAMOUNT" IS 'Auto top-up amount';

ALTER TABLE ${schemaName}.ITEM ADD CONSTRAINT "ITEM_FK7" FOREIGN KEY ("AUTOTOPUPID") REFERENCES ${schemaName}."AUTOTOPUP"("ID")