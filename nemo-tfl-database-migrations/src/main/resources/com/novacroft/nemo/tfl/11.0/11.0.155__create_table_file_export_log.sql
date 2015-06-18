--liquibase formatted sql

--changeset Novacroft:11.0.155
CREATE TABLE ${schemaName}."FILEEXPORTLOG"
("ID" NUMBER(38) NOT NULL CONSTRAINT "FILEEXPORTLOG_PK" PRIMARY KEY
, "VERSION" NUMBER(38) DEFAULT 0 NOT NULL
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "FILENAME" VARCHAR2(128) NOT NULL
, "USERID" VARCHAR2(64) NOT NULL
, "EXPORTEDAT" TIMESTAMP NOT NULL
)
TABLESPACE ${tableTablespace}
;

CREATE INDEX ${schemaName}."FILEEXPORTLOG_IDX1"
ON ${schemaName}."FILEEXPORTLOG"
("FILENAME", "EXPORTEDAT")
TABLESPACE ${indexTablespace}
;

CREATE INDEX ${schemaName}."FILEEXPORTLOG_IDX2"
ON ${schemaName}."FILEEXPORTLOG"
("USERID", "EXPORTEDAT")
TABLESPACE ${indexTablespace}
;

CREATE SEQUENCE ${schemaName}."FILEEXPORTLOG_SEQ";

CREATE SEQUENCE ${schemaName}."FIN_SERV_CEN_EXPORT_FILE_SEQ";

COMMENT ON TABLE ${schemaName}."FILEEXPORTLOG" IS 'Log/audit file exports';
COMMENT ON COLUMN ${schemaName}."FILEEXPORTLOG"."ID" IS 'Surrogate primary key - generated from FILEEXPORTLOG_SEQ sequence';
COMMENT ON COLUMN ${schemaName}."FILEEXPORTLOG"."VERSION" IS 'Record version for optimistic locking';
COMMENT ON COLUMN ${schemaName}."FILEEXPORTLOG"."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}."FILEEXPORTLOG"."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}."FILEEXPORTLOG"."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}."FILEEXPORTLOG"."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}."FILEEXPORTLOG"."FILENAME" IS 'Name of export file';
COMMENT ON COLUMN ${schemaName}."FILEEXPORTLOG"."USERID" IS 'ID of user performing export';
COMMENT ON COLUMN ${schemaName}."FILEEXPORTLOG"."EXPORTEDAT" IS 'When the export was performed';

ALTER TABLE ${schemaName}."SETTLEMENT"
ADD CONSTRAINT "SETTLEMENT_FK4" FOREIGN KEY ("FILEEXPORTLOGID") REFERENCES ${schemaName}."FILEEXPORTLOG"("ID");
