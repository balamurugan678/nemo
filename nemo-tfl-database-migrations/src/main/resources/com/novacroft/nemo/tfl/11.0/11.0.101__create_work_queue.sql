--liquibase formatted sql

--changeset Novacroft:11.0.101
CREATE TABLE ${schemaName}.workqueue
( "ID" NUMBER NOT NULL CONSTRAINT workqueue_pk PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "STAGE" NUMBER(10)
, "STATUS" VARCHAR2(1)
, "DESCRIPTION" VARCHAR2(64)
)
TABLESPACE ${tableTablespace}
;


CREATE SEQUENCE workqueue_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE;

COMMENT ON TABLE ${schemaName}.workqueue IS 'workqueue refunds';
COMMENT ON COLUMN ${schemaName}.workqueue."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.workqueue."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.workqueue."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.workqueue."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';


INSERT INTO workqueue (id, stage, status, createduserid, createdDateTime, description)
VALUES (1, 1, 'O', 'Installer', (TO_DATE('2013/11/29 21:02:44', 'yyyy/mm/dd hh24:mi:ss')), 'WorkQueueItem 1');
INSERT INTO workqueue (id, stage, status, createduserid, createdDateTime, description)
VALUES (2, 1, 'O', 'Installer', (TO_DATE('2013/11/29 21:01:44', 'yyyy/mm/dd hh24:mi:ss')), 'WorkQueueItem 2');
INSERT INTO workqueue (id, stage, status, createduserid, createdDateTime, description)
VALUES (3, 1, 'O', 'Installer', (TO_DATE('2013/11/29 18:02:44', 'yyyy/mm/dd hh24:mi:ss')), 'WorkQueueItem 3');
INSERT INTO workqueue (id, stage, status, createduserid, createdDateTime, description)
VALUES (4, 1, 'O', 'Installer', (TO_DATE('2013/11/29 17:02:44', 'yyyy/mm/dd hh24:mi:ss')), 'WorkQueueItem 4');
INSERT INTO workqueue (id, stage, status, createduserid, createdDateTime, description)
VALUES (5, 1, 'O', 'Installer', (TO_DATE('2013/11/29 12:02:44', 'yyyy/mm/dd hh24:mi:ss')), 'WorkQueueItem 5');

INSERT INTO workqueue (id, stage, status, createduserid, createdDateTime, description)
VALUES (6, 1, 'H', 'Installer', (TO_DATE('2013/11/29 21:02:44', 'yyyy/mm/dd hh24:mi:ss')), 'WorkQueueItem 6');
INSERT INTO workqueue (id, stage, status, createduserid, createdDateTime, description)
VALUES (7, 1, 'C', 'Installer', (TO_DATE('2013/11/29 21:01:44', 'yyyy/mm/dd hh24:mi:ss')), 'WorkQueueItem 7');
INSERT INTO workqueue (id, stage, status, createduserid, createdDateTime, description)
VALUES (8, 1, 'C', 'Installer', (TO_DATE('2013/11/29 18:02:44', 'yyyy/mm/dd hh24:mi:ss')), 'WorkQueueItem 8');

INSERT INTO workqueue (id, stage, status, createduserid, createdDateTime, description)
VALUES (9, 2, 'O', 'Installer', (TO_DATE('2013/11/30 17:02:44', 'yyyy/mm/dd hh24:mi:ss')), 'WorkQueueItem 9');
INSERT INTO workqueue (id, stage, status, createduserid, createdDateTime, description)
VALUES (10, 2, 'C', 'Installer', (TO_DATE('2013/11/30 12:02:44', 'yyyy/mm/dd hh24:mi:ss')), 'WorkQueueItem 10');
INSERT INTO workqueue (id, stage, status, createduserid, createdDateTime, description)
VALUES (11, 2, 'H', 'Installer', (TO_DATE('2013/11/30 12:02:44', 'yyyy/mm/dd hh24:mi:ss')), 'WorkQueueItem 11');

