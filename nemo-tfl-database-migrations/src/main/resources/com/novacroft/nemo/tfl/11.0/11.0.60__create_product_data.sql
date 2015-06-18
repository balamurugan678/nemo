--liquibase formatted sql

--changeset Novacroft:11.0.58
INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '150', 'Annual Bus Pass - All London', '78400', '0', '0', 'Annual','Bus');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164083', 'Weekly Bus Pass All London', '1960', '0', '0', '7Day','Bus');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '140796', 'Monthly Bus Pass All London', '7530', '0', '0', '1Month','Bus');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '1', 'Other Travelcard', '0', '0', '0', 'Unknown','Other');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '508', '7 Day Travelcard Zones 1 to 2', '3040', '1', '2', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '509', '7 Day Travelcard Zones 1 to 3', '3560', '1', '3', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '510', '7 Day Travelcard Zones 1 to 4', '4360', '1', '4', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '511', '7 Day Travelcard Zones 1 to 5', '5180', '1', '5', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '512', '7 Day Travelcard Zones 1 to 6', '5560', '1', '6', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '513', '7 Day Travelcard Zones 1 to 7', '6040', '1', '7', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '514', '7 Day Travelcard Zones 1 to 8', '7120', '1', '8', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '515', '7 Day Travelcard Zones 1 to 9', '7900', '1', '9', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '516', '7 Day Travelcard Zones 1 to 10', '7920', '1', '10', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164086', '7 Day Travelcard Zones 1 to 11', '8250', '1', '11', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164091', '7 Day Travelcard Zones 1 to 12', '9380', '1', '12', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '518', '7 Day Travelcard Zones 2 to 3', '2300', '2', '3', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '519', '7 Day Travelcard Zones 2 to 4', '2520', '2', '4', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '520', '7 Day Travelcard Zones 2 to 5', '3020', '2', '5', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '521', '7 Day Travelcard Zones 2 to 6', '3800', '2', '6', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '522', '7 Day Travelcard Zones 2 to 7', '3940', '2', '7', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '524', '7 Day Travelcard Zones 2 to 9', '5360', '2', '9', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '525', '7 Day Travelcard Zones 2 to 10', '5360', '2', '10', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164099', '7 Day Travelcard Zones 2 to 11', '6460', '2', '11', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164104', '7 Day Travelcard Zones 2 to 12', '7110', '2', '12', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '527', '7 Day Travelcard Zones 3 to 4', '2300', '3', '4', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '528', '7 Day Travelcard Zones 3 to 5', '2520', '3', '5', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '529', '7 Day Travelcard Zones 3 to 6', '3020', '3', '6', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '535', '7 Day Travelcard Zones 4 to 5', '2300', '4', '5', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '536', '7 Day Travelcard Zones 4 to 6', '2520', '4', '6', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '537', '7 Day Travelcard Zones 4 to 7', '2860', '4', '7', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '539', '7 Day Travelcard Zones 4 to 9', '4800', '4', '9', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '540', '7 Day Travelcard Zones 4 to 10', '4800', '4', '10', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '542', '7 Day Travelcard Zones 5 to 6', '2300', '5', '6', '7Day','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '152', 'Monthly Travelcard Zones 1 to 2', '11680', '1', '2', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '153', 'Monthly Travelcard Zones 1 to 3', '13680', '1', '3', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '154', 'Monthly Travelcard Zones 1 to 4', '16750', '1', '4', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '155', 'Monthly Travelcard Zones 1 to 5', '19900', '1', '5', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '156', 'Monthly Travelcard Zones 1 to 6', '21360', '1', '6', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '157', 'Monthly Travelcard Zones 1 to 7', '23200', '1', '7', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '158', 'Monthly Travelcard Zones 1 to 8', '27350', '1', '8', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '159', 'Monthly Travelcard Zones 1 to 9', '30340', '1', '9', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '160', 'Monthly Travelcard Zones 1 to 10', '30420', '1', '10', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164087', 'Monthly Travelcard Zones 1 to 11', '31680', '1', '11', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164095', 'Monthly Travelcard Zones 1 to 12', '36020', '1', '12', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '162', 'Monthly Travelcard Zones 2 to 3', '8840', '2', '3', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '163', 'Monthly Travelcard Zones 2 to 4', '9680', '2', '4', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164', 'Monthly Travelcard Zones 2 to 5', '11600', '2', '5', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '165', 'Monthly Travelcard Zones 2 to 6', '14600', '2', '6', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '166', 'Monthly Travelcard Zones 2 to 7', '15130', '2', '7', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '168', 'Monthly Travelcard Zones 2 to 9', '20590', '2', '9', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '169', 'Monthly Travelcard Zones 2 to 10', '20590', '2', '10', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164100', 'Monthly Travelcard Zones 2 to 11', '24810', '2', '11', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164105', 'Monthly Travelcard Zones 2 to 12', '27310', '2', '12', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '171', 'Monthly Travelcard Zones 3 to 4', '8840', '3', '4', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '172', 'Monthly Travelcard Zones 3 to 5', '9680', '3', '5', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '173', 'Monthly Travelcard Zones 3 to 6', '11600', '3', '6', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '179', 'Monthly Travelcard Zones 4 to 5', '8840', '4', '5', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '180', 'Monthly Travelcard Zones 4 to 6', '9680', '4', '6', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '181', 'Monthly Travelcard Zones 4 to 7', '10990', '4', '7', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '183', 'Monthly Travelcard Zones 4 to 9', '18440', '4', '9', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '184', 'Monthly Travelcard Zones 4 to 10', '18440', '4', '10', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '186', 'Monthly Travelcard Zones 5 to 6', '8840', '5', '6', '1Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33678', '3 Month Travelcard Zones 1 to 2', '35030', '1', '2', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33679', '3 Month Travelcard Zones 1 to 3', '41020', '1', '3', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33681', '3 Month Travelcard Zones 1 to 4', '50230', '1', '4', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33682', '3 Month Travelcard Zones 1 to 5', '59680', '1', '5', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33683', '3 Month Travelcard Zones 1 to 6', '64060', '1', '6', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33685', '3 Month Travelcard Zones 1 to 7', '69590', '1', '7', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33686', '3 Month Travelcard Zones 1 to 8', '82030', '1', '8', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33688', '3 Month Travelcard Zones 1 to 9', '91010', '1', '9', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33690', '3 Month Travelcard Zones 1 to 10', '91240', '1', '10', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164088', '3 Month Travelcard Zones 1 to 11', '95040', '1', '11', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164096', '3 Month Travelcard Zones 1 to 12', '108060', '1', '12', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33704', '3 Month Travelcard Zones 2 to 3', '26500', '2', '3', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33705', '3 Month Travelcard Zones 2 to 4', '29040', '2', '4', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33707', '3 Month Travelcard Zones 2 to 5', '34820', '2', '5', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33709', '3 Month Travelcard Zones 2 to 6', '43780', '2', '6', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33710', '3 Month Travelcard Zones 2 to 7', '45390', '2', '7', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '36766', '3 Month Travelcard Zones 2 to 9', '61750', '2', '9', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33716', '3 Month Travelcard Zones 2 to 10', '61750', '2', '10', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164109', '3 Month Travelcard Zones 2 to 11', '74420', '2', '11', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164106', '3 Month Travelcard Zones 2 to 12', '81910', '2', '12', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33721', '3 Month Travelcard Zones 3 to 4', '26500', '3', '4', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33722', '3 Month Travelcard Zones 3 to 5', '29040', '3', '5', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33724', '3 Month Travelcard Zones 3 to 6', '34800', '3', '6', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33737', '3 Month Travelcard Zones 4 to 5', '26500', '4', '5', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33742', '3 Month Travelcard Zones 4 to 6', '29040', '4', '6', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33746', '3 Month Travelcard Zones 4 to 7', '32950', '4', '7', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33755', '3 Month Travelcard Zones 4 to 9', '55300', '4', '9', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33756', '3 Month Travelcard Zones 4 to 10', '55300', '4', '10', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33759', '3 Month Travelcard Zones 5 to 6', '26500', '5', '6', '3Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33781', '6 Month Travelcard Zones 1 to 2', '70050', '1', '2', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33783', '6 Month Travelcard Zones 1 to 3', '82030', '1', '3', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33785', '6 Month Travelcard Zones 1 to 4', '100460', '1', '4', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33790', '6 Month Travelcard Zones 1 to 5', '119350', '1', '5', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33794', '6 Month Travelcard Zones 1 to 6', '128110', '1', '6', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33795', '6 Month Travelcard Zones 1 to 7', '139170', '1', '7', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33796', '6 Month Travelcard Zones 1 to 8', '164050', '1', '8', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33798', '6 Month Travelcard Zones 1 to 9', '182020', '1', '9', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33799', '6 Month Travelcard Zones 1 to 10', '182480', '1', '10', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164089', '6 Month Travelcard Zones 1 to 11', '190080', '1', '11', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164097', '6 Month Travelcard Zones 1 to 12', '216120', '1', '12', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33802', '6 Month Travelcard Zones 2 to 3', '53000', '2', '3', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33805', '6 Month Travelcard Zones 2 to 4', '58070', '2', '4', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33806', '6 Month Travelcard Zones 2 to 5', '69590', '2', '5', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33807', '6 Month Travelcard Zones 2 to 6', '87560', '2', '6', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33809', '6 Month Travelcard Zones 2 to 7', '90780', '2', '7', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33814', '6 Month Travelcard Zones 2 to 9', '123500', '2', '8', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33816', '6 Month Travelcard Zones 2 to 10', '123500', '2', '10', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164102', '6 Month Travelcard Zones 2 to 11', '148840', '2', '11', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164107', '6 Month Travelcard Zones 2 to 12', '163820', '2', '12', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33819', '6 Month Travelcard Zones 3 to 4', '53000', '3', '4', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33822', '6 Month Travelcard Zones 3 to 5', '58070', '3', '5', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33823', '6 Month Travelcard Zones 3 to 6', '69590', '3', '6', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33833', '6 Month Travelcard Zones 4 to 5', '53000', '4', '5', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33834', '6 Month Travelcard Zones 4 to 6', '58070', '4', '6', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33835', '6 Month Travelcard Zones 4 to 7', '65900', '4', '7', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33838', '6 Month Travelcard Zones 4 to 9', '110600', '4', '9', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33839', '6 Month Travelcard Zones 4 to 10', '110600', '4', '10', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '33843', '6 Month Travelcard Zones 5 to 6', '53000', '5', '6', '6Month','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '102', 'Annual Travelcard Zones 1 to 2', '121600', '1', '2', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '103', 'Annual Travelcard Zones 1 to 3', '142400', '1', '3', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '104', 'Annual Travelcard Zones 1 to 4', '174400', '1', '4', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '105', 'Annual Travelcard Zones 1 to 5', '207200', '1', '5', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '106', 'Annual Travelcard Zones 1 to 6', '222400', '1', '6', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '107', 'Annual Travelcard Zones 1 to 7', '241600', '1', '7', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '108', 'Annual Travelcard Zones 1 to 8', '284800', '1', '8', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '109', 'Annual Travelcard Zones 1 to 9', '316000', '1', '9', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '110', 'Annual Travelcard Zones 1 to 10', '316800', '1', '10', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164090', 'Annual Travelcard Zones 1 to 11', '330000', '1', '11', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164098', 'Annual Travelcard Zones 1 to 12', '375200', '1', '12', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '112', 'Annual Travelcard Zones 2 to 3', '92000', '2', '3', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '113', 'Annual Travelcard Zones 2 to 4', '100800', '2', '4', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '114', 'Annual Travelcard Zones 2 to 5', '120800', '2', '5', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '115', 'Annual Travelcard Zones 2 to 6', '152000', '2', '6', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '116', 'Annual Travelcard Zones 2 to 7', '157600', '2', '7', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '118', 'Annual Travelcard Zones 2 to 9', '214400', '2', '9', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '119', 'Annual Travelcard Zones 2 to 10', '214400', '2', '10', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164103', 'Annual Travelcard Zones 2 to 11', '258400', '2', '11', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '164108', 'Annual Travelcard Zones 2 to 12', '284400', '2', '12', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '121', 'Annual Travelcard Zones 3 to 4', '92000', '3', '4', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '122', 'Annual Travelcard Zones 3 to 5', '100800', '3', '5', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '123', 'Annual Travelcard Zones 3 to 6', '120800', '3', '6', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '129', 'Annual Travelcard Zones 4 to 5', '92000', '4', '5', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '130', 'Annual Travelcard Zones 4 to 6', '100800', '4', '6', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '131', 'Annual Travelcard Zones 4 to 7', '114400', '4', '7', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '133', 'Annual Travelcard Zones 4 to 9', '192000', '4', '9', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '134', 'Annual Travelcard Zones 4 to 10', '192000', '4', '10', 'Annual','Travelcard');

INSERT INTO ${schemaName}.product
( "ID", "CREATEDUSERID", "CREATEDDATETIME", "MODIFIEDUSERID", "MODIFIEDDATETIME", "RATE", "PRODUCTCODE", "PRODUCTNAME", "TICKETPRICE", "STARTZONE", "ENDZONE", "DURATION", "TYPE")
VALUES
(${schemaName}.product_seq.nextval, 'Installer', SYSDATE, NULL, NULL, 'Adult', '136', 'Annual Travelcard Zones 5 to 6', '92000', '5', '6', 'Annual','Travelcard');