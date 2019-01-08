/*******************************************************************************
   JDBCBank Database
   Script: JDBCBank.sql
   Description: Creates and populates the JDBCBank database.
   DB Server: Oracle
   Author: Tenzing Bajracharya
********************************************************************************/

/*******************************************************************************
   Drop database if it exists
********************************************************************************/
DROP USER jdbcBank CASCADE;
/*******************************************************************************
   Create database
********************************************************************************/
CREATE USER jdbcBank
IDENTIFIED BY revature2018
DEFAULT TABLESPACE users
TEMPORARY TABLESPACE temp
QUOTA 10M ON users;

GRANT ALL PRIVILEGES ON revature1217 TO jdbcBank WITH GRANT OPTION;

conn jdbcBank/revature2018



/*******************************************************************************
   Create Tables
********************************************************************************/

create table bank_customer (
userId NUMBER                       PRIMARY KEY,
username varchar(255)               NOT NULL,
userFirstName varchar(255)          NOT NULL,
userLastName varchar(255)           NOT NULL,
userPassword varchar(255)           UNIQUE NOT NULL
);

create table accounts (
userId NUMBER                       UNIQUE NOT NULL,
bankAccountId NUMBER                PRIMARY KEY,
accountBalance Numeric(10,2)        DEFAULT 0
);

create table transactions (
transactionId NUMBER               PRIMARY KEY,
bankAccountId NUMBER               UNIQUE NOT NULL,
transactionType Numeric(10,2)      NOT NULL,
transactionAmt NUMBER              NOT NULL,
transactionDate TIMESTAMP          DEFAULT CURRENT_TIMESTAMP
);


/*******************************************************************************
   Create Foreign Keys
********************************************************************************/

ALTER TABLE accounts ADD CONSTRAINT fk_userId FOREIGN KEY (userId) REFERENCES bank_customer(userId);
ALTER TABLE transactions ADD CONSTRAINT fk_bankAccountId FOREIGN KEY (bankAccountId) REFERENCES accounts(bankAccountId);

/*******************************************************************************
   Create Sequences
********************************************************************************/

CREATE SEQUENCE userIdSeq
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

CREATE SEQUENCE bankAccountSeq
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;  

CREATE SEQUENCE transactionIdSeq
  MINVALUE 1
  MAXVALUE 999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;
  
/*******************************************************************************
   Create Stored Procedures
********************************************************************************/

CREATE OR REPLACE PROCEDURE insertNewUser ( 
fName   IN varchar2,
lName   IN varchar2,
uname   IN varchar2,
pword   IN varchar2,
uid     OUT NUMBER)
IS
BEGIN
    INSERT INTO bank_customer
    (userfirstname, userlastname, username, userpassword, userId)
	VALUES (fName, lName, uname, pword, userIdSeq.nextval);
    uid := userIdSeq.currval;
    COMMIT;
END insertNewUser;

CREATE OR REPLACE PROCEDURE insertTransaction ( 
bAccId  IN varchar2,
tType   IN varchar2,
tranAmt IN NUMBER,
tranDt  IN DATE,
tranId  OUT NUMBER)
IS
BEGIN
    INSERT INTO transactions
    (transactionId, bankAccountId, transactionType, transactionAmt, transactionDate)
	VALUES (transactionIdSeq.nextval, bAccId, tType, tranAmt, tranDt);
    tranId := transactionIdSeq.currval;
    COMMIT;
END insertTransaction;

CREATE OR REPLACE PROCEDURE updateUserFirstname (
usrID    IN NUMBER,
newFname IN varchar2)
IS
BEGIN
    UPDATE bank_customer
    SET userfirstname = newFname
    WHERE userId = usrID;
    COMMIT;
END updateUserFirstname;

CREATE OR REPLACE PROCEDURE updateUserLastname (
usrID    IN NUMBER,
newLname IN varchar2)
IS
BEGIN
    UPDATE bank_customer
    SET userlastname = newLname
    WHERE  userId = usrID;
    COMMIT;
END updateUserLastname;

CREATE OR REPLACE PROCEDURE updateUsername (
usrID    IN NUMBER,
newUname IN varchar2)
IS
BEGIN
    UPDATE bank_customer
    SET username = newUname
    WHERE  userId = usrID;
    COMMIT;
END updateUsername;

CREATE OR REPLACE PROCEDURE updateUserPassword (
usrID     IN NUMBER,
newUsPass IN varchar2)
IS
BEGIN
    UPDATE bank_customer
    SET userpassword = newUsPass
    WHERE  userId = usrID;
    COMMIT;
END updateUserPassword;

CREATE OR REPLACE PROCEDURE createAccount (
usid             IN NUMBER,
bankAccId       OUT NUMBER)
IS
BEGIN
    INSERT INTO accounts
    (userId, bankaccountid)
	VALUES (usid, bankAccountSeq.nextval);
    bankAccId := BANKACCOUNTSEQ.currval;
    COMMIT;
END createAccount;    

CREATE OR REPLACE PROCEDURE deleteUser(
usrid             IN NUMBER)
IS
Begin 
    DELETE FROM bank_customer where usrid = userid;
    COMMIT;
END deleteUser;

variable id number;
exec viewAccount(1, :id) ;


select * from accounts where userid = 23;

select accountbalance from accounts where accounts.bankAccountId = 2