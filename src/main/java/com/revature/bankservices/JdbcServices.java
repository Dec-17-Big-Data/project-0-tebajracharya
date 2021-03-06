package com.revature.bankservices;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

import com.revature.JdbcBank.JdbcDao;
import com.revature.JdbcBank.Transaction;
import com.revature.JdbcBank.User;
import com.revature.JdbcBank.UserApplication;
import com.revature.JdbcBank.UserOracle;
import com.revature.JdbcBank.Account;

public class JdbcServices {

	private static JdbcServices jdbcService;
	
	private Scanner scanner;

	final static JdbcDao jdbcDao = UserOracle.getDao();
	
	
	private JdbcServices() {

	}

	public static JdbcServices getService() {
		if (jdbcService == null) {
			jdbcService = new JdbcServices();
		}
		return jdbcService;
	}

	public Optional<List<User>> getAllUsers() {
		return jdbcDao.getAllUsers();
	}

	public Optional<List<User>> checkUserExist(String username, String password) {
		return jdbcDao.checkUserExist(username, password);
	}

	public Optional<Integer> createUser() {
		System.out.println("Enter Firstname:");
		String firstname = scanner.nextLine(); // Read user input

		System.out.println("Enter Lastname:");
		String lastname = scanner.nextLine(); // Read user input

		System.out.println("Enter a username: ");
		String usrname = scanner.nextLine(); // Read user input

		System.out.println("Enter password: ");
		String usrPassword = scanner.nextLine();

		return jdbcDao.createUser(firstname, lastname, usrname, usrPassword);

	}

	public boolean updateUser(int userId) {
		return jdbcDao.updateUser(userId);
	}
	
	public boolean deleteUser(int userId) {
		return jdbcDao.deleteUser(userId);
	}
	
	public Optional<Account> createAccount (int userId) {
		return jdbcDao.createAccount(userId);
	}
	
	public Optional<List<Account>> viewAccount (int userId) {
		return jdbcDao.viewAccount(userId);
	}

	public Optional<List<Transaction>> transactionDeposit(int bankAccountId, double amt) {
		return jdbcDao.transactionDeposit(bankAccountId, amt);
	}
	
	public Optional<List<Transaction>> transactionWithdraw(int bankAccountId, double amt) {
		return jdbcDao.transactionWithdraw(bankAccountId, amt);
	}

	public Optional<List<Account>> accountBalance(int bankAccountId) {
		return jdbcDao.accountBalance(bankAccountId);
	}
}
