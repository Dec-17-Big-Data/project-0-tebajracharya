package com.revature.JdbcBank;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.io.*;
import java.util.Properties;
import com.revature.JdbcBank.Account;
import com.revature.bankservices.JdbcServices;
import com.revature.JdbcBank.User;

public class UserApplication {
	
	public static Scanner scanner = new Scanner(System.in); // Create a Scanner object


	public static void main(String[] args) throws Exception {
		JdbcServices jdbcService = JdbcServices.getService();
		List<User> users = null;

		System.out.println("Welcome to JDBC Bank");

		String username;
		String userPassword;
		
		System.out.println("Enter '1' for sign in");
		System.out.println("Enter '2' to create new user");
		System.out.println("Enter '3' to Exit");
		System.out.print("Enter Choice: ");
		int choice = scanner.nextInt();
		
		if (choice == 1) {		
			Scanner usrname = new Scanner(System.in);
			Scanner pass = new Scanner(System.in);
			System.out.print("Enter your username: ");
			username = usrname.nextLine(); 						// Read user input
			System.out.print("Enter password: ");
			userPassword = pass.nextLine();
			
			Properties props = new Properties();
			FileReader fr = new FileReader(
					"C:\\Users\\Tenzing\\Documents\\JDBCBank\\src\\main\\resources\\connection.properties");
			props.load(fr);

			String supUser = props.getProperty("jdbc.username");
			String suprPass = props.getProperty("jdbc.password");
			
			if (username.equals(supUser) && userPassword.equals(suprPass))
			{
				
					int option1 = 0;
					do {
						System.out.println("Welcome to JDBC Admin");
						System.out.println("1. Create new user");
						System.out.println("2. View All Account");
						System.out.println("3. Update an Account");
						System.out.println("4. Delete an Account");
						System.out.println("5. Log out");
						System.out.print("Enter choice: ");	
						option1 = scanner.nextInt();

						switch (option1) {
						case 1:
							int usrId = jdbcService.createUser().get();
							System.out.println("The customers user id is: " + usrId);
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
							Scanner id = new Scanner(System.in);
							System.out.println("Enter user id: ");
							int usId = scanner.nextInt();
							jdbcService.deleteUser(usId);
							System.out.println("Deleted User!");
							break;
						}
					} while (option1 != 5);						
			}		
			
			else {																								// User is not Super User 
				List<User> userExist = null;
				userExist =	jdbcService.checkUserExist(username, userPassword).get();
				Boolean searchResult = userExist.isEmpty();
				
				while (searchResult != true) {
					System.out.println("1. Create an account");
					System.out.println("2. View Account");
					System.out.println("3. Deposit");
					System.out.println("4. Withdraw");
					System.out.println("5. Log out");
					System.out.print("Enter your choice: ");
					int cho1 = scanner.nextInt();
					
					if (cho1 == 1) {	
						for (User c : userExist) {
							jdbcService.createAccount(c.getUserId());
						}
					}
					
					else if (cho1 ==  2) {
						List<Account> accnts = null;
						System.out.print("Enter user id: ");
						int usrId = scanner.nextInt();
						accnts = jdbcService.viewAccount(usrId).get();
						for (Account c: accnts) {
							 System.out.println(String.format("%10s %20s %20s", "User ID", "Bank Account Id", "Balance"));
							 System.out.println("----------------------------------------------------------------------");
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
						for (Transaction t: trans) {
							System.out.println(String.format("%10s %20s %20s", "User ID", "Bank Account Id", "Balance"));
							System.out.println(String.format("%10s %20s %20s", t.getTransactionId(), t.getBankAccountId(), t.getTransactionTime()));
						}
					}
					
					else if (cho1 == 4) {
						List<Transaction> acct = null;
						System.out.print("Enter Bank Account Id: ");
						int bankAccntId = scanner.nextInt();
						System.out.print("Enter Amount: ");
						double amt = scanner.nextDouble();
						
						acct = jdbcService.transactionWithdraw(bankAccntId, amt).get();
						
						for (Transaction c: acct) {
							 System.out.println(String.format("%10s %20s %20s", "User ID", "Bank Account Id", "Balance"));
							 System.out.println("----------------------------------------------------------------------");
							 System.out.println(String.format("%10s %20s %20s", c.getTransactionId(), c.getTransactionAmt()));
						}
					}
					
					else if (cho1 == 5) {
						break;
					}
					else continue;
				}
			}		
			System.out.println("Thank you for visiting JDBC Bank!");
		    } 
		
		else if (choice == 2) {
			System.out.println("Do you want to create a user? (Y/N)");
			System.out.print("Enter choice: ");
			String option = scanner.nextLine().toLowerCase();

			if (option.equals("y")) {
				System.out.println("User Created. Your user id is: " + jdbcService.createUser().get());
			} else {
				System.out.println("Thank you for visiting JDBC Bank!");
			}
		}
		else {
			System.out.println("Thank you for visiting JDBC Bank!");
		}
	
	}
}
