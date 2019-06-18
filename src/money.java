import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class money {

	static ArrayList<Customer> custArrayList = new ArrayList<Customer>();
	static ArrayList<Bank> bankArrayList = new ArrayList<Bank>();
	static HashMap<Customer, Thread> hashMapCust = new HashMap<Customer, Thread>();
	static HashMap<Bank, Thread> hashMapBank = new HashMap<Bank, Thread>();
	static Queue<LoanRequest> loanReqQueue = new LinkedList<LoanRequest>();
	
	money moneyObj = new money();
	
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

	private static void getBankData() {
		try {
			FileReader fr = new FileReader(
					"C:\\Users\\Chetan Paliwal\\Desktop\\Summer\\COMP 6411\\Project\\customers.txt");
			BufferedReader br = new BufferedReader(fr);
			String inputLine = "";
			while ((inputLine = br.readLine()) != null) {
				Customer cust = new Customer(inputLine.subSequence(1, inputLine.indexOf(',')).toString(),
						Integer.parseInt(
								(String) inputLine.subSequence(inputLine.indexOf(',') + 1, inputLine.indexOf('}'))),
						0);
				custArrayList.add(cust);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void getCustomerData() {
		try {
			FileReader fr = new FileReader("C:\\Users\\Chetan Paliwal\\Desktop\\Summer\\COMP 6411\\Project\\banks.txt");
			BufferedReader br = new BufferedReader(fr);
			String inputLine = "";
			while ((inputLine = br.readLine()) != null) {
				Bank bank = new Bank(inputLine.subSequence(1, inputLine.indexOf(',')).toString(),
						Integer.parseInt(
								(String) inputLine.subSequence(inputLine.indexOf(',') + 1, inputLine.indexOf('}'))),
						null);
				System.out.println("Bank name: " + bank.name + " : " + bank.balance);
				bankArrayList.add(bank);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

	class Customer extends Thread {
		String name;
		Integer loanReq;
		int disbursedAmt;
		money money = new money();

		Customer(String custName, Integer loanRequirement, int disbursedAmount) {
			name = custName;
			loanReq = loanRequirement;
			disbursedAmt = disbursedAmount;
		}

		@Override
		public void run() {
			System.out.println("in customer thread. ");

			generateReqeust(this, bankArrList, loanReqQueue);
		}

		public Integer getLoanReq() {
			return loanReq;
		}

		public void setLoanReq(Integer loanReq) {
			this.loanReq = loanReq;
		}

		public int getDisbursedAmt() {
			return disbursedAmt;
		}

		public void setDisbursedAmt(int disbursedAmt) {
			this.disbursedAmt = disbursedAmt;
		}

		public ArrayList<Bank> getBankArrList() {
			return bankArrList;
		}

		public void setBankArrList(ArrayList<Bank> bankArrList) {
			this.bankArrList = bankArrList;
		}

		public money getMoney() {
			return money;
		}

		public void setMoney(money money) {
			this.money = money;
		}

		public synchronized void generateReqeust(Customer cust, ArrayList<Bank> bankArrayList,
				Queue<LoanRequest> loanReqQueue) {
			try {
				while (!bankArrayList.isEmpty()) {
					Random rand = new Random();
					int amount = rand.nextInt(50);
					Bank bank = bankArrayList.get(rand.nextInt(bankArrayList.size()));

					//LoanRequest lr = new LoanRequest(custt, bankk, amountt)
					LoanRequest lr;// = new LoanRequest(cust, bank, amount);
					if (amount < lr.bank.balance) {
						System.out
								.println(cust.name + " requests a loan of " + amount + " dollar(s) from " + bank.name);
						loanReqQueue.add(lr);
					} else {
						System.out.println("Bank Array LIst size: " + bankArrayList.size());
						bankArrayList.remove(bank);
					}
					System.out.println("Size: " + loanReqArray.size());
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}