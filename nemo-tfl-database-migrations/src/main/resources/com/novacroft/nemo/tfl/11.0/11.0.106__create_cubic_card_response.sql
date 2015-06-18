--liquibase formatted sql

--changeset Novacroft:11.0.106 context:nemo_development
CREATE TABLE ${schemaName}.mock_cubic_card_response
( "ID" NUMBER NOT NULL CONSTRAINT cubic_card_response_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "PRESTIGEID" VARCHAR2(20)
, "ACTION" VARCHAR2(64)
, "RESPONSE" CLOB
, CONSTRAINT "CUBIC_CARD_RESPONSE_UK1" UNIQUE ("PRESTIGEID", "ACTION")
)
TABLESPACE ${tableTablespace}
;

CREATE SEQUENCE ${schemaName}.mock_cubic_card_response_seq;
CREATE SEQUENCE ${schemaName}.mock_cubic_request_seq_num_seq;

COMMENT ON TABLE ${schemaName}.mock_cubic_card_response IS 'Mock Managed page cubic_card_response';
COMMENT ON COLUMN ${schemaName}.mock_cubic_card_response."ID" IS 'Surrogate primary key - generated from mock_cubic_card_response_SEQ sequence';
COMMENT ON COLUMN ${schemaName}.mock_cubic_card_response."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.mock_cubic_card_response."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.mock_cubic_card_response."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.mock_cubic_card_response."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.mock_cubic_card_response."PRESTIGEID" IS 'Oyster Card Number';
COMMENT ON COLUMN ${schemaName}.mock_cubic_card_response."ACTION" IS 'Action carried out - [GetCard]';
COMMENT ON COLUMN ${schemaName}.mock_cubic_card_response."RESPONSE" IS 'XML response';
