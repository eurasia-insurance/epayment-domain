-- MIGRATION SCRIPT FROM 4.0.3 TO 4.0.4

/*
 * opt lock
 */
ALTER TABLE INVOICE ADD COLUMN UPDATED DATETIME NOT NULL AFTER NUMBER;
UPDATE INVOICE SET UPDATED = NOW();

ALTER TABLE ITEM ADD COLUMN UPDATED DATETIME NOT NULL AFTER QUANTITY;
UPDATE ITEM SET UPDATED = NOW();

ALTER TABLE PAYMENT ADD COLUMN UPDATED DATETIME NOT NULL AFTER REFERENCE;
UPDATE PAYMENT SET UPDATED = NOW();

ALTER TABLE QAZKOM_ERROR ADD COLUMN UPDATED DATETIME NOT NULL AFTER ORDER_NUMBER;
UPDATE QAZKOM_ERROR SET UPDATED = NOW();

ALTER TABLE QAZKOM_ORDER ADD COLUMN UPDATED DATETIME NOT NULL AFTER ORDER_NUMBER;
UPDATE QAZKOM_ORDER SET UPDATED = NOW();

ALTER TABLE QAZKOM_XML_DOCUMENT ADD COLUMN UPDATED DATETIME NOT NULL AFTER TYPE;
UPDATE QAZKOM_XML_DOCUMENT SET UPDATED = NOW();

/*
 * VERSION TABLE
 */
DROP TABLE EPAYMENT_VER_4_0_3;
CREATE TABLE EPAYMENT_VER_4_0_4 (DUMMY INTEGER NOT NULL, PRIMARY KEY (DUMMY));
