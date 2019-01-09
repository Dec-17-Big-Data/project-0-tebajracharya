package com.revature.JdbcBank;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.*;
import java.util.Properties;
import com.revature.JdbcBank.Account;
import com.revature.bankservices.JdbcServices;
import com.revature.JdbcBank.User;

public class UserApplication {
	
	public static Scanner scanner = new Scanner(System.in); // Create a Scanner object
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		JdbcServices jdbcService = JdbcServices.getService();
		List<User> users = null;

		System.out.println("Welcome to JDBC Bank");

		String username;
		String userPassword;
		int choice = 0;
		
		
		while (choice != 3) {
			System.out.println("Enter '1' for sign in");
			System.out.println("Enter '2' to create new user");
			System.out.println("Enter '3' to Exit");
			System.out.print("Enter Choice: ");
			choice = scanner.nextInt();
			if (choice == 1) {		
				Scanner usrname = new Scanner(System.in);
				Scanner pass = new Scanner(System.in);
				System.out.print("Enter your username: ");
				username = usrname.nextLine(); 						// Read user input
				System.out.print("Enter password: ");
				userPassword = pass.nextLine();
				
				Properties props = new Properties();
				FileReader fr = new FileReader("C:\\Users\\Tenzing\\Documents\\JDBCBank\\src\\main\\resources\\connection.properties");
				props.load(fr);

				String supUser = props.getProperty("jdbc.username");
				String suprPass = props.getProperty("jdbc.password");
				
				int signInAttempt = 0;
		loop1:	while (signInAttempt <= 4) {
					if (username.equals(supUser) && userPassword.equals(suprPass))
					{
						int opt1 = 0;
						do {
							System.out.println("Welcome to JDBC Admin");
							System.out.println("1. Create new user");
							System.out.println("2. View All Account");
							System.out.println("3. Update an Account");
							System.out.println("4. Delete an Account");
							System.out.println("5. Log out");
							System.out.print("Enter choice: ");	
							opt1 = scanner.nextInt();
							
							switch (opt1) {
							case 1:
								User u = jdbcService.createUser().get();
								System.out.println("The customers user id is: " + u.getUserId());
								System.out.println("");
								break;
							case 2:
								users = jdbcService.getAllUsers().get();
								System.out.println(String.format("%10s %20s %20s %20s %20s", "User ID", "Username", "Password", "First Name", "Last Name"));
								System.out.println("---------------------------------------------------------------------------------------------------------");
								for (User c : users) {
									System.out.println(String.format("%10s %20s %20s %20s %20s", c.getUserId(), c.getUsername(), c.getUserPassword(), c.getUserFirstName(), c.getUserLastName()));
								}
								System.out.println("");
								break;
							case 3:
								System.out.println("Enter user id: ");
								int usrsId = scanner.nextInt();
								System.out.println("");
								jdbcService.updateUser(usrsId);
								System.out.println("Updated User!");
								break;
							case 4:
								System.out.println("Enter user id: ");
								int usId = scanner.nextInt();
								jdbcService.deleteUser(usId);
								System.out.println("Deleted User!");
								break;
							case 5:
								break loop1;
							}
						}while (opt1 != 5);	
					}
					else {
						int cho1 = 0;
				loop2:	while (cho1 != 6) {	
							System.out.println("");
							try {
								User u = jdbcService.checkUserExist(username, userPassword).get();
								System.out.println("1. Create an account");
								System.out.println("2. View Accounts");
								System.out.println("3. Deposit");
								System.out.println("4. Withdraw");
								System.out.println("5. View all Transactions");
								System.out.println("6. Log out");
								System.out.print("Enter your choice: ");
								cho1 = scanner.nextInt();
							
								if (cho1 == 1) {
									Account a = jdbcService.createAccount(u.getUserId()).get();
									System.out.print("User Created Account Id: " + a.getBankAccountId());
								}
							
								else if (cho1 ==  2) {
									List<Account> accnts = null;
									System.out.print("Enter user id: ");
									int usrId = scanner.nextInt();		
									
									accnts = jdbcService.viewAccount(usrId).get();
									System.out.println(String.format("%10s %20s %20s", "User ID", "Bank Account Id", "Balance"));
									System.out.println("----------------------------------------------------------------------");
									for (Account c: accnts) {
										System.out.println(String.format("%10s %20s %20s", usrId, c.getBankAccountId(), c.getBalance()));
									}
								}
							
								else if (cho1 == 3) {
									System.out.print("Enter Bank Account Id: ");
									int bankAccntId = scanner.nextInt();
									System.out.print("Enter Amount: ");
									double amt = scanner.nextDouble();
									List<Transaction> trans = null;
									trans = jdbcService.transactionDeposit(bankAccntId, amt).get();
									System.out.println(String.format("%10s %30s %30s", "Transaction ID", "Bank Account Id", "Transaction Time"));
									System.out.println("----------------------------------------------------------------------");
									for (Transaction t: trans) {
										System.out.println(String.format("%10s %30s %30s", t.getTransactionId(), t.getBankAccountId(), t.getTransactionTime()));
									}
								}

								else if (cho1 == 4) {
									List<Transaction> acct = null;
									System.out.print("Enter Bank Account Id: ");
									int bankAccntId = scanner.nextInt();
									System.out.print("Enter Amount: ");
									double amt = scanner.nextDouble();
								
									acct = jdbcService.transactionWithdraw(bankAccntId, amt).get();
								
									System.out.println(String.format("%10s %20s %20s", "User ID", "Bank Account Id", "Balance"));
									System.out.println("----------------------------------------------------------------------");
									for (Transaction c: acct) { 
										System.out.println(String.format("%10s %20s %20s", c.getTransactionId(), c.getTransactionAmt()));
									}
								}
							
								else if (cho1 == 5) {
									List<Transaction> accnts = null;
									System.out.print("Enter Bank Account Id: ");
									int bankAccId = scanner.nextInt();
									accnts = jdbcService.viewTransactions(bankAccId).get();
									System.out.println(String.format("%10s %15s %15s %15s %20s", "Transaction ID", "Account Id", "Transaction", "Amount", "Time"));
								 	System.out.println("----------------------------------------------------------------------------------------------------------");
								 	for (Transaction c: accnts) {
								 		System.out.println(String.format("%10s %15s %15s %20s %30s", c.getTransactionId(), c.getBankAccountId(), c.getTransactionType(), c.getTransactionAmt(), c.getTransactionTime()));
								 	}
								}
								
								else if (cho1 == 6) {
									break loop1;
								}
							}
							catch(NoSuchElementException e) {
								System.out.println("Invalid entry.");
								break loop1;
							}
							continue;
						}
					}
				}
			}
			
			else if (choice == 2) {
				User b = jdbcService.createUser().get();
				System.out.println(String.format("%10s %15s %15s %15s %15s", "User Id", "Firstname", "Lastname", "Username", "Password"));
				System.out.println("----------------------------------------------------------------------------------------------------------");
				System.out.println(String.format("%10s %15s %15s %15s %15s", b.getUserId(), b.getUserFirstName(),b.getUserLastName(),b.getUsername(),b.getUserPassword()));
			}
			else if (choice == 3){
				System.out.println("Thank you for visiting JDBC Bank!");
			}
			else {
				System.out.println("Invalid. Try again.");
				continue;
			}
		}
	}
}
