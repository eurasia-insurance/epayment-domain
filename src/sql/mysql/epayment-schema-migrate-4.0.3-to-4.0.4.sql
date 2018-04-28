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
 * bank and bins
 */
CREATE TABLE BANK (ID INTEGER AUTO_INCREMENT NOT NULL, CODE VARCHAR(255) UNIQUE, NAME VARCHAR(255), UPDATED DATETIME NOT NULL, PRIMARY KEY (ID));
CREATE TABLE BANK_2_BIN (BANK_ID INTEGER, BIN VARCHAR(255) UNIQUE);
ALTER TABLE BANK_2_BIN ADD CONSTRAINT FK_BANK_2_BIN_BANK_ID FOREIGN KEY (BANK_ID) REFERENCES BANK (ID);

ALTER TABLE QAZKOM_PAYMENT ADD COLUMN CARD_BANK_ID INTEGER AFTER PAYER_PHONE_NUMBER; 
ALTER TABLE QAZKOM_PAYMENT ADD CONSTRAINT FK_QAZKOM_PAYMENT_CARD_BANK_ID FOREIGN KEY (CARD_BANK_ID) REFERENCES BANK (ID);

/*
 * VERSION TABLE
 */
DROP TABLE EPAYMENT_VER_4_0_3;
CREATE TABLE EPAYMENT_VER_4_0_4 (DUMMY INTEGER NOT NULL, PRIMARY KEY (DUMMY));