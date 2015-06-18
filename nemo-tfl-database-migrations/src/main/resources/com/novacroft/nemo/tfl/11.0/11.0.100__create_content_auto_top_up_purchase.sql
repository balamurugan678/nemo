--liquibase formatted sql

--changeset Novacroft:11.0.100
INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'AutoTopUpPurchase.title', 'en_GB',
'Oyster online - Transport for London - Auto top-up - select station');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'AutoTopUpPurchase.heading', 'en_GB', 'Auto top-up');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'AutoTopUpPurchase.upper.text', 'en_GB',
'Please note that you must activate Auto top-up as part of a normal journey. To activate you must touch your Oyster card on the yellow reader at a your nominated station, 
and also touch out on the yellow reader at the end of your journey.
<p>Once activated, top up will occur automatically as you travel - there is no need to return to your nominated station. If you fail to make a normal 
journey (usual fares '||chr(38)||' ticketing rates apply), you will be charged the maximum cash fare.</p>');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'AutoTopUpPurchase.productInfo.text', 'en_GB',
'<span class="bold">It will be available from tomorrow for the next 8 days.
If you are placing your order after 23:00 it will be available from the day after tomorrow.
If you do not collect it within this period your order will be cancelled and your money refunded.</span>');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'AutoTopUpPurchase.lower.text', 'en_GB',
'Once Auto top-up has been activated, your pay as you go balance will automatically be topped up whenever it falls below £10 
and you touch it on any card reader (Tube, bus, DLR or tram) at the start of your journey.');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'AutoTopUpPurchase.stationId.label', 'en_GB',
'Choose location');


INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'auto.top.up.top.up.amount.2.text', 'en_GB',
'Auto top up set to 20');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'auto.top.up.top.up.amount.3.text', 'en_GB',
'Auto top up set to 40');

INSERT INTO ${schemaName}.content
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "CODE", "LOCALE", "CONTENT")
VALUES
(${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'auto.top.up.no.top.up.text', 'en_GB',
'Auto top up disabled'); 









