-- MIGRATION SCRIPT FROM 4.0.4 TO 5


/*
 * VERSION TABLE
 */
DROP TABLE EPAYMENT_VER_4_0_4;
CREATE TABLE EPAYMENT_VER_5 (DUMMY INTEGER NOT NULL, PRIMARY KEY (DUMMY));
