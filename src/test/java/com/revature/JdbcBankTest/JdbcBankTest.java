/**
 * 
 */
package com.revature.JdbcBankTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.revature.JdbcBank.Account;
import com.revature.JdbcBank.Transaction;
/**
 * @author Tenzing Bajracharya
 *
 */
import com.revature.JdbcBank.User;
import com.revature.JdbcBank.UserOracle;

public class JdbcBankTest {
	
	private static UserOracle userOracle;
	
	private static List<User> users;
	private Transaction t;


	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@BeforeClass
	public static void setup() {
		userOracle = UserOracle.getDao();
	}
	
	@Test
	public void userExist() {
		Optional<User> user1 = userOracle.checkUserExist("millyp","partygirl");
		assertTrue(user1.isPresent());
	}
	
	@Test
	public void createUser() {
		Optional<User> user1 = userOracle.createUser("tanner","dyson","mj","rochkjkerl");
		assertTrue(user1.isPresent());
	}
	
	@Test
	public void balanceCheck() {
		List<Account> users = userOracle.accountBalance(65).get();
		for (Account c: users) {
			assertEquals(Double.valueOf(0), c.getBalance());
		}
	}
	
	@Test
	public void deleteUser() {
		boolean user = userOracle.deleteUser(75);
		assertEquals(true,user);
	}
	
}
