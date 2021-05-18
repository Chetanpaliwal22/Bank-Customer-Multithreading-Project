package com.concordia.multithreading;

public class Request {
	String customerName;
	String bankName;
	int loanAmount;
	int disbursedAmount;
	boolean requestFromBank;
	boolean requestFromCust;

	public Request(String customerName, String bankName, int amount, int disbursedAmount, boolean requestFromBank,
			boolean requestFromCust) {
		this.customerName = customerName;
		this.bankName = bankName;
		this.loanAmount = amount;
		this.disbursedAmount = disbursedAmount;
		this.requestFromBank = requestFromBank;
		this.requestFromCust = requestFromCust;
	}

}