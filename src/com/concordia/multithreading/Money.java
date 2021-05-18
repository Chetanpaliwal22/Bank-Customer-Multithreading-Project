package com.concordia.multithreading;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Money {

	static BlockingQueue<Request> requestBQ = new LinkedBlockingQueue<>();
	static List<String> bankList = new ArrayList<>();

	public static void main(String[] args) {
		Money money = new Money();

		String bankFilePath = "banks.txt";
		String customerFilePath = "customers.txt";

		try {
			// Fetching Bank Data
			money.processBankData(bankFilePath);

			// Fetching Customer Data
			money.processCustomerData(customerFilePath);
		} catch (FileNotFoundException fileNotFoundException) {
			System.out.println("File not found exception, Message: " + fileNotFoundException.getMessage());
		} catch (NumberFormatException numberFormatException) {
			System.out.println("Number Format exception, Message: " + numberFormatException.getMessage());
		} catch (IOException iOException) {
			System.out.println("Input Output exception, Message: " + iOException.getMessage());
		}
	}

	private void processCustomerData(String customerFilePath) throws NumberFormatException, IOException {

		File file = new File(customerFilePath);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

		String inputLine = "";
		System.out.println("** Customers and loan objectives **");
		while ((inputLine = bufferedReader.readLine()) != null) {
			String custName = (String) inputLine.subSequence(1, inputLine.indexOf(','));
			int loanReq = Integer
					.parseInt((String) inputLine.subSequence(inputLine.indexOf(',') + 1, inputLine.indexOf('}')));
			Customer cust = new Customer(custName, loanReq, 0, requestBQ, bankList);
			System.out.println(custName + ": " + loanReq);
			Thread thread = new Thread(cust);
			thread.setName(cust.getCustomerName());
			thread.start();
		}
		bufferedReader.close();
	}

	private void processBankData(String bankFilePath) throws NumberFormatException, IOException {

		File file = new File(bankFilePath);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

		String inputLine = "";
		System.out.println("** Banks and financial resources **");
		while ((inputLine = bufferedReader.readLine()) != null) {
			String bankName = (String) inputLine.subSequence(1, inputLine.indexOf(','));
			int balance = Integer
					.parseInt((String) inputLine.subSequence(inputLine.indexOf(',') + 1, inputLine.indexOf('}')));
			Bank bank = new Bank(bankName, balance, requestBQ);
			System.out.println(bankName + ": " + balance);
			Thread thread = new Thread(bank);
			thread.start();
			thread.setName(bank.getBankName());
			bankList.add(bank.getBankName());
		}
		bufferedReader.close();
	}

	public void printBankBalance(String bankName, int balance) {
		System.out.println(bankName + " has " + balance + " dollar(s) remaining.");
	}

	public void printLoanRequestApproved(String bank, int amount, String cust) {
		System.out.println(bank + " approves a loan of " + amount + " dollars from " + cust);
	}

	public void printLoanRequestDenied(String bank, int amount, String cust) {
		System.out.println(bank + " denies a loan of " + amount + " dollars from " + cust);
	}

	public void printLoanRequest(String cust, int amount, String bank) {
		System.out.println(cust + " requests a loan of " + amount + " dollar(s) from " + bank);
	}

	public void printObjectiveNotReachedCustomer(String name, int amount) {
		System.out.println(name + " was only able to borrow " + amount + " dollar(s). Boo Hoo!");
	}

	public void printObjectiveReachedCustomer(String name, int amount) {
		System.out.println(name + " has reached the objective of " + amount + " dollar(s). Woo Hoo!");
	}

}
