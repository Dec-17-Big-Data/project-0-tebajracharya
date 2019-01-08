package com.revature.JdbcBank;

public class Account {
	
	private int userId;
	private int bankAccountId;
	private double balance;
	
	public Account() {
	}

	public Account(int userId, int bankAccountId, double balance) {
		super();
		this.bankAccountId = bankAccountId;
		this.balance = balance;
	}

	public int getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(int bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}


	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + balance);
		result = prime * result + bankAccountId;
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (balance != other.balance)
			return false;
		if (bankAccountId != other.bankAccountId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [userId=" + userId + ", bankAccountId=" + bankAccountId + ", balance=" + balance + "]";
	}
	
}
