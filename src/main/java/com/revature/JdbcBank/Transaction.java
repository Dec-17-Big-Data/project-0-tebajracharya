package com.revature.JdbcBank;

import java.util.Date;

public class Transaction {

	private Integer transactionId;
	private Integer bankAccountId;
	private String transactionType;
	private Double transactionAmt;
	private Date transactionTime; 
	
	public Transaction() {
	}

	public int getTransactionId() {
		return transactionId;
	}
	
	public Transaction(Integer transactionId, Integer bankAccountId, String transactionType, Double transactionAmt,
			Date currentTime) {
		super();
		this.transactionId = transactionId;
		this.bankAccountId = bankAccountId;
		this.transactionType = transactionType;
		this.transactionAmt = transactionAmt;
		this.transactionTime = currentTime;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public int getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(Integer bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public double getTransactionAmt() {
		return transactionAmt;
	}

	public void setTransactionAmt(Double transactionAmt) {
		this.transactionAmt = transactionAmt;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", bankAccountId=" + bankAccountId + ", transactionType="
				+ transactionType + ", transactionAmt=" + transactionAmt + ", transactionTime=" + transactionTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bankAccountId;
		long temp;
		temp = Double.doubleToLongBits(transactionAmt);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + transactionId;
		result = prime * result + ((transactionTime == null) ? 0 : transactionTime.hashCode());
		result = prime * result + ((transactionType == null) ? 0 : transactionType.hashCode());
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
		Transaction other = (Transaction) obj;
		if (bankAccountId != other.bankAccountId)
			return false;
		if (Double.doubleToLongBits(transactionAmt) != Double.doubleToLongBits(other.transactionAmt))
			return false;
		if (transactionId != other.transactionId)
			return false;
		if (transactionTime == null) {
			if (other.transactionTime != null)
				return false;
		} else if (!transactionTime.equals(other.transactionTime))
			return false;
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType))
			return false;
		return true;
	}

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}
	
	
}
