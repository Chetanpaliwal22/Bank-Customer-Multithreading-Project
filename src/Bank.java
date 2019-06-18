import java.util.ArrayList;

public class Bank extends Thread{
	String name;
	int balance;
	ArrayList<Customer> custArrList;

	money money = new money();
	
	Bank(String bankName,int bankBalance,ArrayList<Customer> custArrListt){
		name = bankName;
		balance = bankBalance;
	custArrList = custArrListt;
	}

	public ArrayList<Customer> getCustArrList() {
		return custArrList;
	}

	public void setCustArrList(ArrayList<Customer> custArrList) {
		this.custArrList = custArrList;
	}

	@Override
	public void run() {
		System.out.println("in bank thread");
		// TODO Auto-generated method stub
		resolveReqeust(this);
		
	}
	
	public synchronized void resolveReqeust(Bank bank) {
		try {
			System.out.println("here size: " + loanReqArray.size());
			while (!bank.custArrList.isEmpty()) {
				while (!loanReqArray.isEmpty()) {
					if (loanReqArray.size() > 0) {
						System.out.println("Processing the request in money thread.");
						LoanRequest lr = loanReqArray.poll();
						if (lr.amount <= lr.bank.balance) {
							bank.balance = bank.balance - lr.amount;
							System.out.println(lr.bank.name + " approves a loan of " + lr.amount + " dollars from "
									+ lr.cust.name + ".");
						} else {
							bank.custArrList.remove(lr.cust);
							System.out.println("Request not processed");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}s
}
