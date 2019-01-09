package com.revature.JdbcBank;

import java.util.List;
import java.util.Optional;

public interface JdbcDao {
	
	Optional<User> checkUserExist(String username, String password);

	Optional<User> createUser(String firstname, String lastname, String usrname, String usrPassword);

	Optional<Account> createAccount(int userId);
	
	Optional<List<Account>> viewAccount(int userId);
	
	Boolean updateUser(int userId);
	
	Boolean deleteUser(int userId);

	Optional<List<User>> getAllUsers();

	Optional<List<Transaction>> transactionDeposit(int bankAccountId, double amt);

	Optional<List<Account>> accountBalance(int bankAccountId);

	Optional<List<Transaction>> transactionWithdraw(int bankAccountId, double amt);

	Optional<List<Transaction>> viewTransactions(int bankAccId);


}
