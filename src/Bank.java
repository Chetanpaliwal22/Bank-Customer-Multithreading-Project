import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Bank extends Thread {
	String name;
	int balance;
	String custName;
	
	//money money = new money();
	private LinkedBlockingQueue<LoanRequest> linkedBlockQueue;
	
	Bank(String bankName, int bankBalance, ArrayList<Customer> custArrListt,String custNamee,LinkedBlockingQueue<LoanRequest> linkedBlockQueuee) {
		name = bankName;
		balance = bankBalance;
		custName = custNamee;
		linkedBlockQueue = linkedBlockQueuee;
	}

	@Override
	public void run() {
		resolveReqeust(this,custName,balance,linkedBlockQueue);
	}

	public synchronized void resolveReqeust(Bank bank,String custName,int bankBalance,LinkedBlockingQueue<LoanRequest> linkedBlockQueuee) {
		try {
			if(linkedBlockQueuee.size() > 0) {
				while (!linkedBlockQueuee.isEmpty()) {
					//if (loanReqArray. size() > 0) {
						System.out.println("Processing the request in money thread.");
//						LoanRequest lr = loanReqArray.poll();
//						if (lr.amount <= lr.bank.balance) {
//							bank.balance = bank.balance - lr.amount;
//							System.out.println(lr.bank.name + " approves a loan of " + lr.amount + " dollars from "
//									+ lr.cust.name + ".");
//						} else {
//							bank.custArrList.remove(lr.cust);
//							System.out.println("Request not processed");
//						}
					}
				}
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}