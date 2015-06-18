--liquibase formatted sql

--changeset Novacroft:11.0.16
CREATE TABLE ${schemaName}.card
( "ID" NUMBER(38) NOT NULL CONSTRAINT card_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "CARDNUMBER" VARCHAR2(32) CONSTRAINT card_uk1 UNIQUE
, "WEBACCOUNTID" NUMBER(38)
, "CUSTOMERID" NUMBER(38)
, "PAYMENTCARDID" NUMBER(38)
, "NAME" VARCHAR2(50)
, "SECURITYQUESTION" VARCHAR2(30)
, "SECURITYANSWER" VARCHAR2(18)
, "MIFARENUMBER" VARCHAR2(100)
, "STATUS" CHAR
, "HOTLISTREASONID" NUMBER
, "HOTLISTDATETIME" DATE
, "HOTLISTSTATUS" VARCHAR2(15)
, CONSTRAINT card_fk1_B FOREIGN KEY ("CUSTOMERID") REFERENCES ${schemaName}.customer("ID")
)
TABLESPACE ${tableTablespace}
;

CREATE INDEX ${schemaName}.card_ix2
ON ${schemaName}.card
("CUSTOMERID")
TABLESPACE ${tableTablespace};

CREATE SEQUENCE ${schemaName}.card_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

COMMENT ON TABLE ${schemaName}.card IS 'Oyster Card';
COMMENT ON COLUMN ${schemaName}.card."ID" IS 'Surrogate primary key - generated from CARD_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.card."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.card."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.card."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.card."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.card."CARDNUMBER" IS 'Number on Card';
COMMENT ON COLUMN ${schemaName}.card."WEBACCOUNTID" IS 'Reference to web account';
COMMENT ON COLUMN ${schemaName}.card."CUSTOMERID" IS 'Reference to customer';
COMMENT ON COLUMN ${schemaName}.card."PAYMENTCARDID" IS 'Reference to payment card - the payment card to use for auto payments';
COMMENT ON COLUMN ${schemaName}.card."NAME" IS 'TBA';
COMMENT ON COLUMN ${schemaName}.card."SECURITYQUESTION" IS 'Identity verification question';
COMMENT ON COLUMN ${schemaName}.card."SECURITYANSWER" IS 'Answer to identity verification question';
COMMENT ON COLUMN ${schemaName}.card."MIFARENUMBER" IS 'MiFare number';
COMMENT ON COLUMN ${schemaName}.card."STATUS" IS 'Current status of the card';
COMMENT ON COLUMN ${schemaName}.card."HOTLISTREASONID" IS 'Hot list reason linked to hotlist reason table';
COMMENT ON COLUMN ${schemaName}.card."HOTLISTDATETIME" IS 'The date the card was flagged as hotlisted';
COMMENT ON COLUMN ${schemaName}.card."HOTLISTSTATUS" IS 'The status of hotlisted card i.e. readytoexport or exported';

CREATE TABLE ${schemaName}.card_aud
( "REV" NUMBER NOT NULL
, "REVTYPE" NUMBER NOT NULL
,"ID" NUMBER(38) NOT NULL
, "CREATEDUSERID" VARCHAR2(64)
, "CREATEDDATETIME" DATE
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "CARDNUMBER" NUMBER(38)
, "WEBACCOUNTID" NUMBER(38)
, "CUSTOMERID" NUMBER(38)
, "PAYMENTCARDID" NUMBER(38)
, "NAME" VARCHAR2(50)
, "SECURITYQUESTION" VARCHAR2(30)
, "SECURITYANSWER" VARCHAR2(18)
, "MIFARENUMBER" VARCHAR2(100)
, "STATUS" CHAR
, "HOTLISTREASONID" NUMBER
, "HOTLISTDATETIME" DATE
, "HOTLISTSTATUS" VARCHAR2(20)
)
TABLESPACE ${tableTablespace}
;
