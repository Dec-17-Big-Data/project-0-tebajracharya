
alter table bank_customer DROP CONSTRAINT primary_key;
alter table bank_customer DROP CONSTRAINT FK_USERID;
alter table accounts DROP CONSTRAINT account_pk;
alter table accounts drop CONSTRAINT fk_bankAccountId;
alter table transactions drop CONSTRAINT transactionsID_pk;
alter table transactions drop CONSTRAINT FK_BANKACCOUNTID;


drop sequence userIdseq;
drop sequence bankaccountidseq;
drop sequence transactionidseq;

drop procedure createaccount;
drop procedure deleteuser;
drop procedure inserttransaction;
drop procedure updateuserfirstname;
drop procedure updateuserlastname;
drop procedure updateusername;
drop procedure updateuserpassword;
drop procedure insertNewUser;
drop procedure insertUserAccount;
drop procedure insertTransactions;

drop table bank_customer;
drop table accounts;
drop table transactions;