--liquibase formatted sql

--changeset Novacroft:11.0.18
CREATE TABLE ${schemaName}.PAYMENTCARD
( "ID" NUMBER NOT NULL CONSTRAINT PAYMENTCARD_PK PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "CUSTOMERID" NUMBER(38) NOT NULL
, "ADDRESSID" NUMBER(38) NULL
, "OBFUSCATEDPRIMARYACCOUNTNUMBER" VARCHAR2(20) NOT NULL
, "EXPIRYDATE" VARCHAR2(8)
, "FIRSTNAME" VARCHAR2(60)
, "LASTNAME" VARCHAR2(60)
, "TOKEN" VARCHAR2(50) NULL
, "STATUS" VARCHAR2(50) NULL
, "NICKNAME" VARCHAR2(50) NULL
, "REFERENCECODE" VARCHAR2(50) NULL
, CONSTRAINT PAYMENTCARD_FK1 FOREIGN KEY ("CUSTOMERID") REFERENCES ${schemaName}.CUSTOMER("ID")
, CONSTRAINT PAYMENTCARD_FK3 FOREIGN KEY ("ADDRESSID") REFERENCES ${schemaName}.ADDRESS("ID")
)
TABLESPACE ${tableTablespace}
;

CREATE INDEX ${schemaName}.PAYMENTCARD_IX1
ON ${schemaName}.PAYMENTCARD
("CUSTOMERID")
TABLESPACE ${indexTablespace}
;

CREATE SEQUENCE PAYMENTCARD_SEQ START WITH 1 INCREMENT BY 1 NOMAXVALUE;

COMMENT ON TABLE ${schemaName}.paymentcard IS 'Tokenised payment card, eg credit card, debit card, etc';
COMMENT ON COLUMN ${schemaName}.paymentcard."ID" IS 'Surrogate primary key - generated from PAYMENTCARD_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.paymentcard."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.paymentcard."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.paymentcard."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.paymentcard."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.paymentcard."CUSTOMERID" IS 'Reference to customer';
COMMENT ON COLUMN ${schemaName}.paymentcard."ADDRESSID" IS 'Reference to address for payment card';
COMMENT ON COLUMN ${schemaName}.paymentcard."OBFUSCATEDPRIMARYACCOUNTNUMBER" IS 'Obfuscated primary account number (PAN)';
COMMENT ON COLUMN ${schemaName}.paymentcard."EXPIRYDATE" IS 'Card expiry date MM/YYYY';
COMMENT ON COLUMN ${schemaName}.paymentcard."FIRSTNAME" IS 'Card holder first name';
COMMENT ON COLUMN ${schemaName}.paymentcard."LASTNAME" IS 'Card holder last name';
COMMENT ON COLUMN ${schemaName}.paymentcard."TOKEN" IS 'Token representation of the card';
COMMENT ON COLUMN ${schemaName}.paymentcard."STATUS" IS 'Payment card status';
COMMENT ON COLUMN ${schemaName}.paymentcard."NICKNAME" IS 'Nick name used by customer for this card';
COMMENT ON COLUMN ${schemaName}.paymentcard."REFERENCECODE" IS 'Payment gateway reference code for this card';

CREATE TABLE ${schemaName}.PAYMENTCARD_AUD
( "REV" NUMBER NOT NULL
, "REVTYPE" NUMBER NOT NULL
, "ID" NUMBER(38) NOT NULL
, "CREATEDUSERID" VARCHAR2(64)
, "CREATEDDATETIME" DATE
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "CUSTOMERID" NUMBER(38)
, "ADDRESSID" NUMBER(38)
, "OBFUSCATEDPRIMARYACCOUNTNUMBER" VARCHAR2(50)
, "EXPIRYDATE" VARCHAR2(8)
, "FIRSTNAME" VARCHAR2(60)
, "LASTNAME" VARCHAR2(60)
, "TOKEN" VARCHAR2(50)
, "STATUS" VARCHAR2(50)
, "NICKNAME" VARCHAR2(50)
, "REFERENCECODE" VARCHAR2(50) NULL
)
TABLESPACE ${tableTablespace}
;

COMMENT ON TABLE ${schemaName}.PAYMENTCARD_AUD IS 'Audit trail for PAYMENTCARD table.';

ALTER TABLE ${schemaName}.CARD
ADD CONSTRAINT card_fk3 FOREIGN KEY ("PAYMENTCARDID") REFERENCES ${schemaName}.PAYMENTCARD("ID");
