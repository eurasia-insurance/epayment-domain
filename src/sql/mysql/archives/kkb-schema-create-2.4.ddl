CREATE TABLE KKB_ORDER_ITEM (ID INTEGER AUTO_INCREMENT NOT NULL, COST DOUBLE, NAME VARCHAR(255), QUANTITY INTEGER, KKB_ORDER_ID VARCHAR(50), PRIMARY KEY (ID))
CREATE TABLE KKB_DOCUMENT (ID INTEGER AUTO_INCREMENT NOT NULL, TYPE VARCHAR(31), CONTENT LONGTEXT, CREATED DATETIME, KKB_ORDER_ID VARCHAR(50), PRIMARY KEY (ID))
CREATE TABLE KKB_VER_2_4 (DUMMY INTEGER NOT NULL, PRIMARY KEY (DUMMY))
CREATE TABLE KKB_ORDER (ID VARCHAR(50) NOT NULL, AMOUNT DOUBLE, CLOSED DATETIME, CONSUMER_EMAIL VARCHAR(255), CONSUMER_LANGUAGE VARCHAR(255), CONSUMER_NAME VARCHAR(255), CREATED DATETIME, CURRENCY VARCHAR(255), EXTERNAL_ID VARCHAR(100), STATUS VARCHAR(100), UPDATED DATETIME, CART_DOCUMENT_ID INTEGER, REQUEST_DOCUMENT_ID INTEGER, RESPONSE_DOCUMENT_ID INTEGER, PRIMARY KEY (ID))
CREATE INDEX KKB_ORDER_IDX01 ON KKB_ORDER (STATUS)
CREATE INDEX KKB_ORDER_IDX02 ON KKB_ORDER (STATUS, EXTERNAL_ID)
ALTER TABLE KKB_ORDER_ITEM ADD CONSTRAINT FK_KKB_ORDER_ITEM_KKB_ORDER_ID FOREIGN KEY (KKB_ORDER_ID) REFERENCES KKB_ORDER (ID)
ALTER TABLE KKB_DOCUMENT ADD CONSTRAINT FK_KKB_DOCUMENT_KKB_ORDER_ID FOREIGN KEY (KKB_ORDER_ID) REFERENCES KKB_ORDER (ID)
ALTER TABLE KKB_ORDER ADD CONSTRAINT FK_KKB_ORDER_REQUEST_DOCUMENT_ID FOREIGN KEY (REQUEST_DOCUMENT_ID) REFERENCES KKB_DOCUMENT (ID)
ALTER TABLE KKB_ORDER ADD CONSTRAINT FK_KKB_ORDER_CART_DOCUMENT_ID FOREIGN KEY (CART_DOCUMENT_ID) REFERENCES KKB_DOCUMENT (ID)
ALTER TABLE KKB_ORDER ADD CONSTRAINT FK_KKB_ORDER_RESPONSE_DOCUMENT_ID FOREIGN KEY (RESPONSE_DOCUMENT_ID) REFERENCES KKB_DOCUMENT (ID)