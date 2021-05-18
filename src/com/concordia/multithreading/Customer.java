package com.concordia.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Customer extends Thread {

	private BlockingQueue<Request> requestQue;
	private String name;
	private int loanRequirement;
	private int disbursedAmt;

	List<String> bankList = new ArrayList<String>();;
	Money money = new Money();

	Customer(String name, Integer loanRequirement, int disbursedAmount, BlockingQueue<Request> requestQue,
			List<String> bankList) {
		this.name = name;
		this.loanRequirement = loanRequirement;
		this.disbursedAmt = disbursedAmount;
		this.requestQue = requestQue;

		for (int i = 0; i < bankList.size(); i++) {
			this.bankList.add(bankList.get(i));
		}
	}

	public String getCustomerName() {
		return name;
	}

	@Override
	public void run() {
		try {

			if (bankList.size() > 0) {
				Random rand = new Random();
				int loanAmount = rand.nextInt(50) + 1;

				String bankName = bankList.get(rand.nextInt(bankList.size()));
				if (loanAmount <= loanRequirement) {
					Request loanRequest = new Request(name, bankName, loanAmount, -1, false, true);
					money.printLoanRequest(this.name, loanAmount, bankName);
					requestQue.put(loanRequest);
				} else if (this.loanRequirement > 0) {
					Request loanRequest = new Request(name, bankName, loanRequirement, -1, false, true);
					money.printLoanRequest(this.name, this.loanRequirement, bankName);
					requestQue.put(loanRequest);
				}
			}

			generateLoanRequest(this, requestQue);

			if (loanRequirement == 0) {
				money.printObjectiveReachedCustomer(this.name, this.disbursedAmt);
			} else {
				money.printObjectiveNotReachedCustomer(this.name, this.disbursedAmt);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public synchronized void generateLoanRequest(Customer cust, BlockingQueue<Request> requestQue) {
		try {

			while (!requestQue.isEmpty()) {

				Random rand = new Random();
				Thread.sleep(rand.nextInt(100));
				boolean needLoanFlag = false;

				for (Request request : requestQue) {
					if (request.customerName.equalsIgnoreCase(Thread.currentThread().getName())
							&& request.requestFromBank) {

						if (request.disbursedAmount > 0) {
							this.loanRequirement = this.loanRequirement - request.disbursedAmount;
							this.disbursedAmt = this.disbursedAmt + request.disbursedAmount;
							needLoanFlag = true;
						} else if (request.disbursedAmount == -1) {
							cust.bankList.remove(request.bankName);
							needLoanFlag = true;
						} else if (request.loanAmount == request.disbursedAmount) {
							needLoanFlag = false;
						}
						requestQue.remove(request);
					}
				}

				if (!needLoanFlag) {
					continue;
				}

				if (cust.bankList.size() > 0) {
					int amount = rand.nextInt(50);
					String bankName = cust.bankList.get(rand.nextInt(cust.bankList.size()));

					if (amount <= this.loanRequirement) {
						Request loanRequest = new Request(name, bankName, amount, 0, false, true);
						money.printLoanRequest(this.name, amount, bankName);
						requestQue.put(loanRequest);
					} else if (this.loanRequirement > 0) {
						Request loanRequest = new Request(name, bankName, this.loanRequirement, 0, false, true);
						money.printLoanRequest(this.name, this.loanRequirement, bankName);
						requestQue.put(loanRequest);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}