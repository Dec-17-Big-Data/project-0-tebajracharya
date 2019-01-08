package com.revature.JdbcBank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import com.revature.JdbcBank.Account;
import com.revature.JdbcBank.User;
import java.sql.CallableStatement;

public class UserOracle implements JdbcDao {

	private Scanner scanner;

	private static UserOracle userData;

	private UserOracle() {

	}

	public static JdbcDao getDao() {
		if (userData == null) {
			userData = new UserOracle();
		}
		return userData;
	}

	private static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}

	public Optional<List<User>> checkUserExist(String username, String password) {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
			return Optional.empty();
		}

		try {
			String sql = "Select * from bank_customer where username = ? and userpassword = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			List<User> listOfUsers = new ArrayList<User>();

			while (rs.next()) {
				listOfUsers.add(new User(rs.getInt("userid"), rs.getString("username"), rs.getString("userfirstname"),
						rs.getString("userlastname"), rs.getString("userpassword")));
			}
			return Optional.of(listOfUsers);
		} 
		catch (SQLException e) {
		}
		return null;
	}

	public Optional<Integer> createUser(String firstname, String lastname, String usrname, String usrpassword) {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
			return Optional.empty();
		}

		try {
			CallableStatement cs = con.prepareCall("{call insertNewUser(?,?,?,?,?)}");
			cs.setString(1, firstname);
			cs.setString(2, lastname);
			cs.setString(3, usrname);
			cs.setString(4, usrpassword);
			cs.registerOutParameter(5, java.sql.Types.INTEGER);
			cs.executeUpdate();
			int id = cs.getInt(5);
			return Optional.of(id);
		} catch (SQLException e) {
		}
		return null;
	}

	public Optional<List<User>> getAllUsers() {
		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			return Optional.empty();
		}

		try {
			String sql = "select * from bank_customer";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			List<User> listOfUsers = new ArrayList<User>();

			while (rs.next()) {
				listOfUsers.add(new User(rs.getInt("userid"), rs.getString("username"), rs.getString("userfirstname"),
						rs.getString("userlastname"), rs.getString("userpassword")));
			}

			return Optional.of(listOfUsers);
		} catch (SQLException e) {

		}
		return null;
	}
	
	public boolean updateUser(int userId) {
		System.out.println("Enter update field");
		System.out.println("1. Username");
		System.out.println("2. Password");
		System.out.println("3. Firstname");
		System.out.println("4. Lastname");
		int choice = scanner.nextInt();
		
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
			return false;
		}
		try {
			System.out.println("Enter upated value");
			String updatedWord = scanner.nextLine();
			
			if (choice == 1) {
				CallableStatement cs = con.prepareCall("{call updateUsername(?,?)");
				cs.setInt(1, userId);
				cs.setString(2, updatedWord);
				boolean rs = cs.execute();
				return rs;
			}
			else if (choice == 2) {
				CallableStatement cs = con.prepareCall("{call updateUserPassword(?,?)");
				cs.setInt(1, userId);
				cs.setString(2, updatedWord);
				boolean rs = cs.execute();
				return rs;
			}
			else if (choice == 3) {
				CallableStatement cs = con.prepareCall("{call updateUserFirstname(?,?)");
				cs.setInt(1, userId);
				cs.setString(2, updatedWord);
				boolean rs = cs.execute();
				return rs;
			}
			else if (choice == 4) {
				CallableStatement cs = con.prepareCall("{call updateUserLastname(?,?)");
				cs.setInt(1, userId);
				cs.setString(2, updatedWord);
				boolean rs = cs.execute();
				return rs;
			}
		} catch (SQLException e) {

		}
		return false;
	}
	
	
	public boolean deleteUser(int userId) {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
			return false;
		}
		try {
			CallableStatement cs = con.prepareCall("{call deleteUser(?)");
			cs.setInt(1, userId);
			boolean rs = cs.execute();
			return rs;
		} catch (SQLException e) {

		}
		return false;
	}
	
	public Optional<Account> createAccount(int userId) {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
			return null;
		}
		try {
			CallableStatement cs = con.prepareCall("{call createAccount(?,?)");
			cs.setInt(1, userId);
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			
			cs.executeQuery();
			
			Account accnt = null;
			accnt = new Account(userId, cs.getInt(2), 0.0);
			return Optional.of(accnt);
		} catch (SQLException e) {

		}
		return null;
	}

	public Optional<List<Account>> viewAccount(int userId) {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
			return null;
		}
		try {
			String sql = "select * from accounts where accounts.userid = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			
			List<Account> listOfAccnts =  new ArrayList<Account>();
			
			while (rs.next()) {
				listOfAccnts.add(new Account(rs.getInt("userId"), rs.getInt("bankAccountId"), rs.getDouble("accountbalance")));		
			}
			
			return Optional.of(listOfAccnts);
		} catch (SQLException e) {

		}
		return null;
	}
	
	public Optional<List<Transaction>> transactionDeposit(int bankAccntId, double deposit) {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
			return null;
		}
		try {	
			String sql = "select accountbalance from accounts where accounts.bankAccountId = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, bankAccntId);
			ResultSet rs = ps.executeQuery();
			double newBalance = 0;
			
			while(rs.next()) {
				newBalance = rs.getDouble("accountbalance") + deposit;
			}
			String sql2 = "Update accounts set accountbalance = ? where accounts.bankAccountId = ?";
			PreparedStatement ps2 = con.prepareStatement(sql2);
			ps2.setDouble(1, newBalance);
			ps2.setInt(2, bankAccntId);
			ResultSet rs2 = ps2.executeQuery();
			
			List<Transaction> listOfTransaction =  new ArrayList<Transaction>();
			while(rs2.next()) {
				listOfTransaction.add(new Transaction(rs2.getInt("transactionid"), rs2.getInt("bankaccountid"), rs2.getString("transaction_type"), rs2.getDouble("trasactionamt"), rs2.getDate("transactiondate")));
			}
			
			CallableStatement cs = con.prepareCall("{call insertTransaction(?,?,?,?,?)");
			cs.setInt(1, bankAccntId);
			cs.setString(2, "Deposit");
			cs.setDouble(3, deposit);
			cs.setTimestamp(4, java.sql.Timestamp.from(java.time.Instant.now()));
			cs.executeQuery();
			
			return Optional.of(listOfTransaction);
		} catch (SQLException e) {
		}
		return null;
	}
	
	public Optional<List<Account>> accountBalance(int bankAccntId) {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
			return null;
		}
		try {	
			String sql = "select accountbalance from accounts where accounts.bankAccountId = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, bankAccntId);
			ResultSet rs = ps.executeQuery();
			
			List<Account> listOfAccountBal =  new ArrayList<Account>();
			while(rs.next()) {
				listOfAccountBal.add(new Account(rs.getInt("userid"), rs.getInt("bankaccountid"), rs.getDouble("accountbalance")));
			}
			return Optional.of(listOfAccountBal);
		}
		catch (SQLException e) {
		}
		return null;
	}
	
	public Optional<List<Transaction>> transactionWithdraw(int bankAccntId, double withdraw) {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
			return null;
		}
		try {	
			String sql = "select accountbalance from accounts where accounts.bankAccountId = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, bankAccntId);
			ResultSet rs = ps.executeQuery();
			double newBalance = 0;
			
			while(rs.next()) {
				newBalance = rs.getDouble("accountbalance") - withdraw;
			}
			
			/*String sql1 = "Update accounts set accountbalance = ? where accounts.bankAccountId = ?";
			PreparedStatement ps1 = con.prepareStatement(sql1);
			ps1.setDouble(1, newBalance);
			ps1.setInt(2, bankAccntId);
			ResultSet rs2 = ps1.executeQuery();
			
			CallableStatement cs = con.prepareCall("{call insertTransaction(?,?,?,?,?)");
			cs.setInt(1, bankAccntId);
			cs.setString(2, "Withdraw");
			cs.setDouble(3, withdraw);
			//cs. setDate
			cs.executeQuery();
			
			Transaction tr = null;
			tr = new Transaction(cs.getInt(1), bankAccntId, "Withdraw", withdraw, date);
			
			
			List<Transaction> listOfTransaction =  new ArrayList<Transaction>();
			while(rs2.next()) {
				listOfTransaction.add(new Transaction(rs2.getInt("transactionid"), rs2.getInt("bankaccountid"), rs2.getString("transaction_type"), rs2.getDouble("trasactionamt"), rs2.getDate("transactiondate")));
			}
			return Optional.of(listOfTransaction);*/
			return null;
		} catch (SQLException e) {
		}
		return null;
	}
	
	
	
}

