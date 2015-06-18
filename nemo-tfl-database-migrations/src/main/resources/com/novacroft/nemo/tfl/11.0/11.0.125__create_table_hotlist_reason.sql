--liquibase formatted sql

--changeset Novacroft:11.0.125
CREATE TABLE ${schemaName}."HOTLISTREASON"
("ID" NUMBER(38) NOT NULL CONSTRAINT "HOTLISTREASON_PK" PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "DESCRIPTION" VARCHAR2(200) NOT NULL
)
TABLESPACE ${tableTablespace}
;

ALTER TABLE ${schemaName}.CARD ADD CONSTRAINT "CARD_FK2" FOREIGN KEY ("HOTLISTREASONID") REFERENCES ${schemaName}."HOTLISTREASON"("ID");


COMMENT ON TABLE ${schemaName}."HOTLISTREASON" IS 'Reason descriptions for hotlisted card codes';
COMMENT ON COLUMN ${schemaName}."HOTLISTREASON"."ID" IS 'Surrogate primary key';
COMMENT ON COLUMN ${schemaName}."HOTLISTREASON"."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}."HOTLISTREASON"."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}."HOTLISTREASON"."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}."HOTLISTREASON"."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}."HOTLISTREASON"."DESCRIPTION" IS 'The hotlist reason description';

