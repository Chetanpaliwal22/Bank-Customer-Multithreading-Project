import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class money {

	private LinkedBlockingQueue<String> linkedBlockQueue = new LinkedBlockingQueue<String>();

	static ArrayList<Customer> custArrayList = new ArrayList<Customer>();
	static ArrayList<Bank> bankArrayList = new ArrayList<Bank>();
	static HashMap<Customer, Thread> hashMapCust = new HashMap<Customer, Thread>();
	static HashMap<Bank, Thread> hashMapBank = new HashMap<Bank, Thread>();

	public static void main(String[] args) {

		getCustomerData();
		getBankData();

		for (int i = 0; i < bankArrayList.size(); i++) {
			Bank bank = bankArrayList.get(i);
			Thread t1 = new Thread(bank);
			t1.start();
			hashMapBank.put(bankArrayList.get(i), t1);
		}

		for (int i = 0; i < custArrayList.size(); i++) {
			Customer cust = custArrayList.get(i);
			Thread t1 = new Thread(cust);
			t1.start();
			hashMapCust.put(custArrayList.get(i), t1);
		}
	}

	private static void getCustomerData() {
		try {
			FileReader fr = new FileReader(
					"G:\\Java Project\\Bank-Customer-Multithreading-Project\\src\\customers.txt");
			BufferedReader br = new BufferedReader(fr);
			String inputLine = "";
			System.out.println("** Customers and loan objectives **");
			while ((inputLine = br.readLine()) != null) {
				Customer cust = new Customer(inputLine.subSequence(1, inputLine.indexOf(',')).toString(), Integer
						.parseInt((String) inputLine.subSequence(inputLine.indexOf(',') + 1, inputLine.indexOf('}'))),
						0);
				custArrayList.add(cust);
				System.out.println(cust.name+": "+cust.loanReq);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void getBankData() {
		try {
			FileReader fr = new FileReader("G:\\Java Project\\Bank-Customer-Multithreading-Project\\src\\banks.txt");
			BufferedReader br = new BufferedReader(fr);
			String inputLine = "";
			System.out.println("** Banks and financial resources **");
			while ((inputLine = br.readLine()) != null) {
				Bank bank = new Bank(inputLine.subSequence(1, inputLine.indexOf(',')).toString(),
						Integer.parseInt(
								(String) inputLine.subSequence(inputLine.indexOf(',') + 1, inputLine.indexOf('}'))),
						null);
				bankArrayList.add(bank);
				System.out.println(bank.name+": "+bank.balance);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
