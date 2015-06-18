--liquibase formatted sql

--changeset Novacroft:11.0.77
CREATE TABLE ${schemaName}.event
( "ID" NUMBER(38) NOT NULL CONSTRAINT event_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "NAME" VARCHAR2(32) CONSTRAINT event_uk1 UNIQUE
, "DESCRIPTION" VARCHAR2(200)
, "DISPLAYORDER" NUMBER(2)
, "DISPLAYONLINE" NUMBER(1)
)
TABLESPACE ${tableTablespace}
;

CREATE SEQUENCE ${schemaName}.event_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

COMMENT ON TABLE ${schemaName}.event IS 'Events';
COMMENT ON COLUMN ${schemaName}.event."ID" IS 'Surrogate primary key - generated from EVENT_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.event."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.event."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.event."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.event."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.event."NAME" IS 'Display name of event ';
COMMENT ON COLUMN ${schemaName}.event."DESCRIPTION" IS 'Description of event';
COMMENT ON COLUMN ${schemaName}.event."DISPLAYORDER" IS 'Order events are displayed';
COMMENT ON COLUMN ${schemaName}.event."DISPLAYONLINE" IS 'Display event on the online website (not InNovator) - 1 is Display online - 0 is InNovator only';


CREATE TABLE ${schemaName}.applicationevent
( "ID" NUMBER(38) NOT NULL CONSTRAINT applicationevent_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "EVENTID" NUMBER
, "CUSTOMERID" NUMBER
, "WEBACCOUNTID" NUMBER
, "ADDITIONALINFORMATION" VARCHAR2(4000)
, CONSTRAINT applicationevent_fk1 FOREIGN KEY ("EVENTID") REFERENCES ${schemaName}.event("ID")
)
TABLESPACE ${tableTablespace}
;

CREATE INDEX ${schemaName}.applicationevent_ix1
ON ${schemaName}.applicationevent
("CUSTOMERID")
TABLESPACE ${tableTablespace};

CREATE INDEX ${schemaName}.applicationevent_ix2
ON ${schemaName}.applicationevent
("WEBACCOUNTID")
TABLESPACE ${tableTablespace};


CREATE SEQUENCE ${schemaName}.applicationevent_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

COMMENT ON TABLE ${schemaName}.applicationevent IS 'Application Events';
COMMENT ON COLUMN ${schemaName}.applicationevent."ID" IS 'Surrogate primary key - generated from APPLICATIONEVENT_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.applicationevent."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.applicationevent."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.applicationevent."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.applicationevent."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.applicationevent."EVENTID" IS 'Event reference ';
COMMENT ON COLUMN ${schemaName}.applicationevent."CUSTOMERID" IS 'Customer reference, this is not foreign keys as they wont always be linked';
COMMENT ON COLUMN ${schemaName}.applicationevent."WEBACCOUNTID" IS 'Webaccount reference, this is not foreign keys as they wont always be linked';
COMMENT ON COLUMN ${schemaName}.applicationevent."ADDITIONALINFORMATION" IS 'Free text field for additional information';

CREATE TABLE ${schemaName}."APPLICATIONEVENT_AUD"
("REV" NUMBER NOT NULL
, "REVTYPE" NUMBER NOT NULL
, "ID" NUMBER(38) NOT NULL
, "CREATEDUSERID" VARCHAR2(64)
, "CREATEDDATETIME" DATE
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "EVENTID" NUMBER
, "CUSTOMERID" NUMBER
, "WEBACCOUNTID" NUMBER
, "ADDITIONALINFORMATION" VARCHAR2(4000)
)
TABLESPACE ${tableTablespace}
;

COMMENT ON TABLE ${schemaName}."APPLICATIONEVENT_AUD" IS 'Audit records for APPLICATIONEVENT table';
