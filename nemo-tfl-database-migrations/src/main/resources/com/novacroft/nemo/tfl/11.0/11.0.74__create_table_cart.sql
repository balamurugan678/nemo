--liquibase formatted sql

--changeset Novacroft:11.0.74
CREATE TABLE ${schemaName}."CART"
("ID" NUMBER(38) NOT NULL CONSTRAINT "CART_PK" PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "WEBACCOUNTID" NUMBER(38)
, "CUSTOMERID" NUMBER(38)
, "CARDID" NUMBER(38)
, "CARTTYPE" VARCHAR2(40) NOT NULL
, "DATEOFREFUND" DATE
, "APPROVALID" NUMBER(38)
, CONSTRAINT "CART_FK1" FOREIGN KEY ("CUSTOMERID") REFERENCES ${schemaName}."CUSTOMER"("ID")
, CONSTRAINT "CART_FK2" FOREIGN KEY ("CARDID") REFERENCES ${schemaName}."CARD"("ID")
, CONSTRAINT "CART_UK1" UNIQUE ("APPROVALID")
)
TABLESPACE ${tableTablespace}
;

CREATE SEQUENCE ${schemaName}.CART_SEQ START WITH 1 INCREMENT BY 1 NOMAXVALUE;
CREATE SEQUENCE ${schemaName}."APPROVAL_ID_SEQ" START WITH 1 MINVALUE 1 NOCACHE;

COMMENT ON TABLE ${schemaName}."CART" IS 'Shopping Cart';
COMMENT ON COLUMN ${schemaName}."CART"."ID" IS 'Surrogate primary key - generated from CART_SEQ sequence';
COMMENT ON COLUMN ${schemaName}."CART"."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}."CART"."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}."CART"."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}."CART"."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}."CART"."WEBACCOUNTID" IS 'Reference to web account';
COMMENT ON COLUMN ${schemaName}."CART"."CUSTOMERID" IS 'Reference to customer';
COMMENT ON COLUMN ${schemaName}."CART"."CARDID" IS 'Reference to card';
COMMENT ON COLUMN ${schemaName}."CART"."CARTTYPE" IS 'Type of cart (Purchase or Refund)';
COMMENT ON COLUMN ${schemaName}."CART"."DATEOFREFUND" IS 'Effective Date of Refund';
COMMENT ON COLUMN ${schemaName}."CART"."APPROVALID" IS 'Reference number for workflow - generated from APPROVAL_ID_SEQ sequence';

CREATE TABLE ${schemaName}."CART_AUD"
("REV" NUMBER NOT NULL
, "REVTYPE" NUMBER NOT NULL
, "ID" NUMBER(38) NOT NULL
, "CREATEDUSERID" VARCHAR2(64)
, "CREATEDDATETIME" DATE
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "WEBACCOUNTID" NUMBER(38)
, "CUSTOMERID" NUMBER(38)
, "CARDID" NUMBER(38)
, "CARTTYPE" VARCHAR2(40)
, "DATEOFREFUND" DATE
, "APPROVALID" NUMBER(38)
)
TABLESPACE ${tableTablespace}
;

COMMENT ON TABLE ${schemaName}."CART_AUD" IS 'Audit records for CART table';
