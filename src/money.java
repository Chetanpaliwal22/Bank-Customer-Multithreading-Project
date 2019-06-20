import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class money {

	static money moneyobj = new money();
	
	static BlockingQueue<String> requestBQ = new LinkedBlockingQueue<String>();
	
	static ArrayList<Customer> custArrayList = new ArrayList<Customer>();
	static ArrayList<Bank> bankArrayList = new ArrayList<Bank>();
	static HashMap<Customer, Thread> hashMapCust = new HashMap<Customer, Thread>();
	static HashMap<Bank, Thread> hashMapBank = new HashMap<Bank, Thread>();

	public static void main(String[] args) {

		String pathBank = "G:\\Java Project\\Bank-Customer-Multithreading-Project\\src\\banks.txt";
		String pathCust = "G:\\Java Project\\Bank-Customer-Multithreading-Project\\src\\customers.txt";
		
		getCustomerData( pathCust);
		getBankData( pathBank);
		
		for (int i = 0; i < custArrayList.size(); i++) {
			Customer cust = custArrayList.get(i);
			cust.setBankArrayList(bankArrayList);
			Thread t1 = new Thread(cust);
			t1.setName(cust.name);
			t1.start();
			hashMapCust.put(cust, t1);
		}

		for (int i = 0; i < bankArrayList.size(); i++) {
			Bank bank = bankArrayList.get(i);
			Thread t1 = new Thread(bank);
			t1.setName(bank.name);
			t1.start();
			hashMapBank.put(bankArrayList.get(i), t1);
		}

	}

	private static void getCustomerData(String path) {
		try {
			
			FileReader fr = new FileReader(
					path);
			BufferedReader br = new BufferedReader(fr);
			String inputLine = "";
			System.out.println("** Customers and loan objectives **");
			while ((inputLine = br.readLine()) != null) {
				Customer cust = new Customer(inputLine.subSequence(1, inputLine.indexOf(',')).toString(),
						Integer.parseInt(
								(String) inputLine.subSequence(inputLine.indexOf(',') + 1, inputLine.indexOf('}'))),
						0, requestBQ, bankArrayList,moneyobj);
				custArrayList.add(cust);
				System.out.println(cust.name + ": " + cust.loanReq);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void getBankData(String path) {
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			String inputLine = "";
			System.out.println("** Banks and financial resources **");
			while ((inputLine = br.readLine()) != null) {
				Bank bank = new Bank(inputLine.subSequence(1, inputLine.indexOf(',')).toString(),
						Integer.parseInt(
								(String) inputLine.subSequence(inputLine.indexOf(',') + 1, inputLine.indexOf('}'))),
						null, "dummyCustName", requestBQ,moneyobj);
				bankArrayList.add(bank);
				System.out.println(bank.name + ": " + bank.balance);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void showBankBalance(String bankName,int balance) {
		System.out.println(bankName + " has " + balance + " dollar(s) remaining.");
	}
	
	public void approveLoanBank(String bank,int amount,String cust) {
		System.out.println(bank + " approves a loan of " + amount
				+ " dollars from " +cust);
	}
	
	public void denyLoanBank(String bank,int amount,String cust) {
		System.out.println(bank + " denies a loan of " + amount
				+ " dollars from " + cust);
	}
	
	public void loanRequestCustomer(String cust,int amount, String bank) {
		System.out.println(cust+ " requests a loan of " + amount + " dollar(s) from " + bank);
	}
	
	public void noteReachedObjectiveCust(String name,int amount) {
		System.out.println(name+" was only able to borrow "+amount+" dollar(s). Boo Hoo!");
	}

	public void reachedObjectiveCust(String name,int amount) {
		System.out.println(name+" has reached the objective of "+amount+" dollar(s). Woo Hoo!");
	}


}
