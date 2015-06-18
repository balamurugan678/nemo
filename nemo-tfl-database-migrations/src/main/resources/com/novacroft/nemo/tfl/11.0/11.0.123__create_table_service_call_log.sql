--liquibase formatted sql

--changeset Novacroft:11.0.123
CREATE TABLE ${schemaName}."SERVICECALLLOG"
("ID" NUMBER(38) NOT NULL CONSTRAINT "SERVICECALLLOG_PK" PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "SERVICENAME" VARCHAR2(64) NOT NULL
, "USERID" VARCHAR2(64) NOT NULL
, "CUSTOMERID" NUMBER(38)
, "REQUESTEDAT" TIMESTAMP NOT NULL
, "RESPONDEDAT" TIMESTAMP NOT NULL
, "REQUEST" CLOB
, "RESPONSE" CLOB
)
TABLESPACE ${tableTablespace}
;

CREATE INDEX ${schemaName}."SERVICECALLLOG_IDX1"
ON ${schemaName}."SERVICECALLLOG"
("SERVICENAME", "USERID", "CUSTOMERID")
TABLESPACE ${indexTablespace}
;

CREATE SEQUENCE ${schemaName}."SERVICECALLLOG_SEQ";

COMMENT ON TABLE ${schemaName}."SERVICECALLLOG" IS 'Log/audit service calls';
COMMENT ON COLUMN ${schemaName}."SERVICECALLLOG"."ID" IS 'Surrogate primary key - generated from SERVICECALLLOG_SEQ sequence';
COMMENT ON COLUMN ${schemaName}."SERVICECALLLOG"."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}."SERVICECALLLOG"."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}."SERVICECALLLOG"."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}."SERVICECALLLOG"."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}."SERVICECALLLOG"."SERVICENAME" IS 'Name of called service';
COMMENT ON COLUMN ${schemaName}."SERVICECALLLOG"."USERID" IS 'ID of user calling service';
COMMENT ON COLUMN ${schemaName}."SERVICECALLLOG"."CUSTOMERID" IS 'Reference to customer who the service was called for';
COMMENT ON COLUMN ${schemaName}."SERVICECALLLOG"."REQUESTEDAT" IS 'When the request was sent';
COMMENT ON COLUMN ${schemaName}."SERVICECALLLOG"."RESPONDEDAT" IS 'When the response was received';
COMMENT ON COLUMN ${schemaName}."SERVICECALLLOG"."REQUEST" IS 'Request';
COMMENT ON COLUMN ${schemaName}."SERVICECALLLOG"."RESPONSE" IS 'Response';
