package com.revature.JdbcBank;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface JdbcDao {
	Optional<List<User>> checkUserExist(String username, String password);

	Optional<Integer> createUser(String firstname, String lastname, String usrname, String usrPassword);

	Optional<Account> createAccount(int userId);
	
	Optional<List<Account>> viewAccount(int userId);
	
	boolean updateUser(int userId);
	
	boolean deleteUser(int userId);

	Optional<List<User>> getAllUsers();

	Optional<List<Transaction>> transactionDeposit(int bankAccountId, double amt);

	Optional<List<Account>> accountBalance(int bankAccountId);

	Optional<List<Transaction>> transactionWithdraw(int bankAccountId, double amt);


}
