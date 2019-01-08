
alter table bank_customer DROP CONSTRAINT primary_key;
alter table accounts DROP CONSTRAINT account_pk;
alter table accounts drop CONSTRAINT fk_bankAccountId;
alter table transactions drop CONSTRAINT transactionsID_pk;
alter table transactions drop CONSTRAINT fk_checkingsId;

drop sequence userId;
drop sequence bank_account_id;
drop sequence transaction_id;

drop procedure insertNewUser;
drop procedure insertUserAccount;
drop procedure insertTransactions;

drop table bank_customer;
drop table accounts;
drop table transactions;