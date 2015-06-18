--liquibase formatted sql

--changeset Novacroft:11.0.44
CREATE OR REPLACE VIEW ${schemaName}.location_v
("ID"
, "CREATEDUSERID"
, "CREATEDDATETIME"
, "MODIFIEDUSERID"
, "MODIFIEDDATETIME"
,"NAME"
, "STATUS")
AS
SELECT
  nlc,
  'Unknown',
  create_date,
  'Unknown',
  last_modified,
  name,
  status
FROM   ${schemaName}.nlclookup;

COMMENT ON TABLE ${schemaName}.location_v IS 'Location (station) lookup - based on NLCLOOKUP';
COMMENT ON COLUMN ${schemaName}.location_v."ID" IS 'Surrogate primary key - maps to NLC';
COMMENT ON COLUMN ${schemaName}.location_v."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}.location_v."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}.location_v."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}.location_v."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}.location_v.name IS 'Location (station) name';
COMMENT ON COLUMN ${schemaName}.location_v.status IS 'Status';
