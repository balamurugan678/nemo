--liquibase formatted sql

--changeset Novacroft:11.0.102
CREATE TABLE ${schemaName}."JOBLOG"
("ID" NUMBER(38) NOT NULL CONSTRAINT "JOBLOG_PK" PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "JOBNAME" VARCHAR2(64) NOT NULL
, "FILENAME" VARCHAR2(1024)
, "STARTEDAT" DATE NOT NULL
, "ENDEDAT" DATE NOT NULL
, "STATUS" VARCHAR2(32) NOT NULL
, "LOG" CLOB
)
TABLESPACE ${tableTablespace}
;

CREATE INDEX ${schemaName}."JOBLOG_IDX1"
ON ${schemaName}."JOBLOG"
(LOWER("JOBNAME"), LOWER("FILENAME"))
TABLESPACE ${indexTablespace}
;

CREATE SEQUENCE ${schemaName}."JOBLOG_SEQ";

COMMENT ON TABLE ${schemaName}."JOBLOG" IS 'Keeps a log ';
COMMENT ON COLUMN ${schemaName}."JOBLOG"."ID" IS 'Surrogate primary key - generated from JOBLOG_SEQ sequence';
COMMENT ON COLUMN ${schemaName}."JOBLOG"."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}."JOBLOG"."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}."JOBLOG"."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}."JOBLOG"."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}."JOBLOG"."JOBNAME" IS 'Name of job';
COMMENT ON COLUMN ${schemaName}."JOBLOG"."FILENAME" IS 'Name of the file the job processed';
COMMENT ON COLUMN ${schemaName}."JOBLOG"."STARTEDAT" IS 'When the job started at';
COMMENT ON COLUMN ${schemaName}."JOBLOG"."ENDEDAT" IS 'When the job ended at';
COMMENT ON COLUMN ${schemaName}."JOBLOG"."STATUS" IS 'Outcome of the job';
COMMENT ON COLUMN ${schemaName}."JOBLOG"."LOG" IS 'Job log output';

CREATE TABLE ${schemaName}."JOBLOG_AUD"
("REV" NUMBER NOT NULL
, "REVTYPE" NUMBER NOT NULL
, "ID" NUMBER(38) NOT NULL
, "CREATEDUSERID" VARCHAR2(64)
, "CREATEDDATETIME" DATE
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "JOBNAME" VARCHAR2(64)
, "FILENAME" VARCHAR2(1024)
, "STARTEDAT" DATE
, "ENDEDAT" DATE
, "STATUS" VARCHAR2(32)
, "LOG" CLOB
)
TABLESPACE ${tableTablespace}
;

COMMENT ON TABLE ${schemaName}."JOBLOG_AUD" IS 'Audit records for JOBLOG table';
