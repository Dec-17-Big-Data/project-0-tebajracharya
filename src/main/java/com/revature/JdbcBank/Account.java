package com.revature.JdbcBank;

public class Account {
	
	private Integer userId;
	private Integer bankAccountId;
	private Double balance;
	
	public Account() {
	}

	public Account(Integer userId, Integer bankAccountId, Double balance) {
		super();
		this.bankAccountId = bankAccountId;
		this.balance = balance;
	}

	public Integer getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(Integer bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final Integer prime = 31;
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
