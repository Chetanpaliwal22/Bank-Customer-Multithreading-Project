package com.concordia.multithreading;

import java.util.concurrent.BlockingQueue;

public class Bank extends Thread {

	private BlockingQueue<Request> requestQue;
	private String name;
	private int balance;

	Bank(String bankName, int bankBalance, BlockingQueue<Request> requestQue) {
		this.name = bankName;
		this.balance = bankBalance;
		this.requestQue = requestQue;
	}

	public String getBankName() {
		return name;
	}

	@Override
	public void run() {
		Money money = new Money();
		try {
			Thread.sleep(100);
			resolveLoanRequest();
			Thread.sleep(100);
			money.printBankBalance(name, balance);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void resolveLoanRequest() throws InterruptedException {
		Money money = new Money();
		try {

			while (!requestQue.isEmpty()) {
				for (Request request : requestQue) {
					if (request != null && request.bankName.equalsIgnoreCase(Thread.currentThread().getName())
							&& request.requestFromCust) {
						int amountRequested = request.loanAmount;
						if (balance >= amountRequested) {
							this.balance = this.balance - amountRequested;
							money.printLoanRequestApproved(request.bankName, amountRequested, request.customerName);
							Request newRequest = new Request(request.customerName, request.bankName, -1,
									amountRequested, true, false);
							requestQue.put(newRequest);
							requestQue.remove(request);
						} else {
							money.printLoanRequestDenied(request.bankName, request.loanAmount, request.customerName);
							Request newloanRequest = new Request(request.customerName, request.bankName, -1, -1, true,
									false);
							requestQue.put(newloanRequest);
							requestQue.remove(request);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}