-- MIGRATION SCRIPT FROM 2.4 TO 2.5

-- !12 >>
ALTER TABLE KKB_ORDER 
	ADD COLUMN PAID DATETIME AFTER EXTERNAL_ID,
	ADD COLUMN PAYMENT_REFERENCE VARCHAR(255) AFTER PAID;

ALTER TABLE KKB_DOCUMENT ADD COLUMN TEMPCOL VARCHAR(255), ADD COLUMN TEMPCOL2 VARCHAR(255);

UPDATE KKB_DOCUMENT SET 
   TEMPCOL = (SUBSTRING(CONTENT, LOCATE('reference="', CONTENT) + 11, (LOCATE('"', CONTENT, LOCATE('reference="', CONTENT) + 11) - (LOCATE('reference="', CONTENT) + 11)))), 
   TEMPCOL2 = (SUBSTRING(CONTENT, LOCATE('<results timestamp="', CONTENT) + 20, (LOCATE('"', CONTENT, LOCATE('<results timestamp="', CONTENT) + 20) - (LOCATE('<results timestamp="', CONTENT) + 20)))) 
   WHERE TYPE = 'RESPONSE';

UPDATE KKB_ORDER SET PAYMENT_REFERENCE = (SELECT TEMPCOL FROM KKB_DOCUMENT WHERE KKB_ORDER.ID = KKB_DOCUMENT.KKB_ORDER_ID AND KKB_DOCUMENT.TEMPCOL IS NOT NULL);
UPDATE KKB_ORDER SET PAID = (SELECT TEMPCOL2 FROM KKB_DOCUMENT WHERE KKB_ORDER.ID = KKB_DOCUMENT.KKB_ORDER_ID AND KKB_DOCUMENT.TEMPCOL2 IS NOT NULL);
ALTER TABLE KKB_DOCUMENT DROP COLUMN TEMPCOL, DROP COLUMN TEMPCOL2;
-- !12 <<

/*
 * VERSION TABLE
 */
DROP TABLE KKB_VER_2_4;
CREATE TABLE KKB_VER_2_5 (DUMMY INTEGER NOT NULL, PRIMARY KEY (DUMMY));