--liquibase formatted sql

--changeset Novacroft:11.0.150
CREATE TABLE ${schemaName}."COUNTRY"
("ID" NUMBER(38) NOT NULL CONSTRAINT "COUNTRY_PK" PRIMARY KEY
, "CREATEDUSERID" VARCHAR2(64) NOT NULL
, "CREATEDDATETIME" DATE NOT NULL
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "NAME" VARCHAR2(256) NOT NULL CONSTRAINT "COUNTRY_UK1" UNIQUE
, "CODE" VARCHAR2(2) NOT NULL CONSTRAINT "COUNTRY_UK2" UNIQUE
)
TABLESPACE ${tableTablespace}
;

CREATE INDEX ${schemaName}."COUNTRY_IDX1"
ON ${schemaName}."COUNTRY"
("NAME", "CODE")
TABLESPACE ${indexTablespace}
;

CREATE SEQUENCE ${schemaName}."COUNTRY_SEQ";

COMMENT ON TABLE ${schemaName}."COUNTRY" IS 'ISO 3166-1 country names and codes';
COMMENT ON COLUMN ${schemaName}."COUNTRY"."ID" IS 'Surrogate primary key - generated from COUNTRY_SEQ sequence';
COMMENT ON COLUMN ${schemaName}."COUNTRY"."CREATEDUSERID" IS 'Who created the record';
COMMENT ON COLUMN ${schemaName}."COUNTRY"."CREATEDDATETIME" IS 'When (date and time) the record was created';
COMMENT ON COLUMN ${schemaName}."COUNTRY"."MODIFIEDUSERID" IS 'Who last modified the record';
COMMENT ON COLUMN ${schemaName}."COUNTRY"."MODIFIEDDATETIME" IS 'When (date and time) the record was last modified';
COMMENT ON COLUMN ${schemaName}."COUNTRY"."NAME" IS 'ISO country name';
COMMENT ON COLUMN ${schemaName}."COUNTRY"."CODE" IS 'ISO two letter country code';

CREATE TABLE ${schemaName}."COUNTRY_AUD"
("REV" NUMBER NOT NULL
, "REVTYPE" NUMBER NOT NULL
, "ID" NUMBER(38) NOT NULL
, "CREATEDUSERID" VARCHAR2(64)
, "CREATEDDATETIME" DATE
, "MODIFIEDUSERID" VARCHAR2(64)
, "MODIFIEDDATETIME" DATE
, "NAME" VARCHAR2(256)
, "CODE" VARCHAR2(2)
)
TABLESPACE ${tableTablespace}
;

COMMENT ON TABLE ${schemaName}."COUNTRY_AUD" IS 'Audit records for COUNTRY table';

INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Afghanistan', 'AF');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Aland Islands', 'AX');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Albania', 'AL');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Algeria', 'DZ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'American Samoa (US)', 'AS');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Andorra', 'AD');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Angola', 'AO');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Anguilla (UK)', 'AI');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Antarctica', 'AQ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Antigua and Barbuda', 'AG');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Argentina', 'AR');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Armenia', 'AM');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Aruba', 'AW');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Australia', 'AU');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Austria', 'AT');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Azerbaijan', 'AZ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Bahamas', 'BS');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Bahrain', 'BH');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Bangladesh', 'BD');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Barbados', 'BB');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Belarus', 'BY');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Belgium', 'BE');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Belize', 'BZ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Benin', 'BJ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Bermuda (UK)', 'BM');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Bhutan', 'BT');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Bolivia', 'BO');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Bonaire, Sint Eustatius and Saba', 'BQ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Bosnia and Herzegovina', 'BA');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Botswana', 'BW');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Brazil', 'BR');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'British Indian Ocean Territory', 'IO');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'British Virgin Islands (UK)', 'VG');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Brunei Darussalam', 'BN');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Bulgaria', 'BG');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Burkina Faso', 'BF');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Burundi', 'BI');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Cambodia', 'KH');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Cameroon', 'CM');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Canada', 'CA');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Cape Verde', 'CV');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Cayman Islands (UK)', 'KY');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Central African Republic', 'CF');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Chad', 'TD');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Chile', 'CL');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'China', 'CN');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Christmas Island (AU)', 'CX');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Cocos (Keeling) Islands (AU)', 'CC');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Colombia', 'CO');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Comoros', 'KM');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Congo, Democratic Republic of the', 'CD');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Congo, Republic of the', 'CG');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Cook Islands (NZ)', 'CK');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Costa Rica', 'CR');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Côte D''Ivoire', 'CI');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Croatia', 'HR');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Cuba', 'CU');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Curaçao', 'CW');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Cyprus', 'CY');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Czech Republic', 'CZ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Denmark', 'DK');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Djibouti', 'DJ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Dominica', 'DM');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Dominican Republic', 'DO');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Ecuador', 'EC');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Egypt', 'EG');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'El Salvador', 'SV');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Equatorial Guinea', 'GQ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Eritrea', 'ER');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Estonia', 'EE');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Ethiopia', 'ET');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Falkland Islands (UK)', 'FK');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Faroe Islands (DK)', 'FO');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Fiji', 'FJ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Finland', 'FI');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'France', 'FR');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'French Guiana (FR)', 'GF');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'French Polynesia (FR)', 'PF');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'French Southern Territories', 'TF');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Gabon', 'GA');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Gambia', 'GM');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Georgia', 'GE');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Germany', 'DE');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Ghana', 'GH');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Gibraltar (UK)', 'GI');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Greece', 'GR');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Greenland (DK)', 'GL');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Grenada', 'GD');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Guadeloupe (FR)', 'GP');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Guam (US)', 'GU');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Guatemala', 'GT');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Guernsey', 'GG');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Guinea', 'GN');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Guinea-Bissau', 'GW');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Guyana', 'GY');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Haiti', 'HT');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Heard Island and McDonald Islands', 'HM');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Holy See (Vatican City)', 'VA');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Honduras', 'HN');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Hong Kong (CN)', 'HK');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Hungary', 'HU');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Iceland', 'IS');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'India', 'IN');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Indonesia', 'ID');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Iran', 'IR');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Iraq', 'IQ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Ireland', 'IE');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Isle of Man', 'IM');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Israel', 'IL');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Italy', 'IT');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Jamaica', 'JM');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Japan', 'JP');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Jersey', 'JE');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Jordan', 'JO');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Kazakhstan', 'KZ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Kenya', 'KE');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Kiribati', 'KI');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Korea, Democratic People''s Republic (North)', 'KP');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Korea, Republic of (South)', 'KR');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Kuwait', 'KW');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Kyrgyzstan', 'KG');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Laos', 'LA');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Latvia', 'LV');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Lebanon', 'LB');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Lesotho', 'LS');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Liberia', 'LR');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Libya', 'LY');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Liechtenstein', 'LI');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Lithuania', 'LT');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Luxembourg', 'LU');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Macau (CN)', 'MO');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Macedonia', 'MK');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Madagascar', 'MG');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Malawi', 'MW');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Malaysia', 'MY');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Maldives', 'MV');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Mali', 'ML');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Malta', 'MT');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Marshall Islands', 'MH');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Martinique (FR)', 'MQ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Mauritania', 'MR');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Mauritius', 'MU');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Mayotte (FR)', 'YT');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Mexico', 'MX');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Micronesia, Federated States of', 'FM');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Moldova Republic of', 'MD');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Monaco', 'MC');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Mongolia', 'MN');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Montenegro', 'ME');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Montserrat (UK)', 'MS');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Morocco', 'MA');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Mozambique', 'MZ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Myanmar', 'MM');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Namibia', 'NA');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Nauru', 'NR');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Nepal', 'NP');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Netherlands', 'NL');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Netherlands Antilles (NL)', 'AN');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'New Caledonia (FR)', 'NC');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'New Zealand', 'NZ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Nicaragua', 'NI');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Niger', 'NE');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Nigeria', 'NG');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Niue', 'NU');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Norfolk Island (AU)', 'NF');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Northern Mariana Islands (US)', 'MP');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Norway', 'NO');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Oman', 'OM');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Pakistan', 'PK');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Palau', 'PW');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Palestinian Territories', 'PS');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Panama', 'PA');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Papua New Guinea', 'PG');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Paraguay', 'PY');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Peru', 'PE');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Philippines', 'PH');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Pitcairn Islands (UK)', 'PN');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Poland', 'PL');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Portugal', 'PT');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Puerto Rico (US)', 'PR');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Qatar', 'QA');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Reunion (FR)', 'RE');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Romania', 'RO');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Russia', 'RU');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Rwanda', 'RW');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Saint Barthelemy', 'BL');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Saint Helena (UK)', 'SH');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Saint Kitts and Nevis', 'KN');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Saint Lucia', 'LC');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Saint Martin', 'MF');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Saint Pierre & Miquelon (FR)', 'PM');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Saint Vincent and the Grenadines', 'VC');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Samoa', 'WS');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'San Marino', 'SM');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Sao Tome and Principe', 'ST');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Saudi Arabia', 'SA');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Senegal', 'SN');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Serbia', 'RS');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Seychelles', 'SC');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Sierra Leone', 'SL');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Singapore', 'SG');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Sint Maarten (Dutch Part)', 'SX');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Slovakia', 'SK');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Slovenia', 'SI');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Solomon Islands', 'SB');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Somalia', 'SO');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'South Africa', 'ZA');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'South Georgia & South Sandwich Islands (UK)', 'GS');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'South Sudan', 'SS');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Spain', 'ES');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Sri Lanka', 'LK');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Sudan', 'SD');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Suriname', 'SR');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Svalbard and Jan Mayen', 'SJ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Swaziland', 'SZ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Sweden', 'SE');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Switzerland', 'CH');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Syria', 'SY');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Taiwan', 'TW');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Tajikistan', 'TJ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Tanzania', 'TZ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Thailand', 'TH');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Timor-Leste', 'TL');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Togo', 'TG');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Tokelau', 'TK');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Tonga', 'TO');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Trinidad and Tobago', 'TT');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Tunisia', 'TN');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Turkey', 'TR');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Turkmenistan', 'TM');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Turks and Caicos Islands (UK)', 'TC');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Tuvalu', 'TV');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Uganda', 'UG');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Ukraine', 'UA');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'United Arab Emirates', 'AE');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'United Kingdom', 'GB');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'United States', 'US');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'United States Minor Outlying Islands', 'UM');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Uruguay', 'UY');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Uzbekistan', 'UZ');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Vanuatu', 'VU');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Venezuela', 'VE');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Vietnam', 'VN');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Virgin Islands (US)', 'VI');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Wallis and Futuna (FR)', 'WF');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Western Sahara', 'EH');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Yemen', 'YE');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Zambia', 'ZM');
INSERT INTO ${schemaName}."COUNTRY"("ID", "CREATEDUSERID", "CREATEDDATETIME", "NAME", "CODE") VALUES (${schemaName}."COUNTRY_SEQ".nextval, 'Installer', SYSDATE, 'Zimbabwe', 'ZW');
