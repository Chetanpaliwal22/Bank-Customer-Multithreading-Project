import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Bank extends Thread {
	String name;
	int balance;
	String custName;

	// money money = new money();
	ConcurrentHashMap<String, LoanRequest> requestHM;

	Bank(String bankName, int bankBalance, ArrayList<Customer> custArrListt, String custNamee,
			ConcurrentHashMap<String, LoanRequest> linkedBlockQueuee) {
		name = bankName;
		balance = bankBalance;
		custName = custNamee;
		requestHM = linkedBlockQueuee;
	}

	@Override
	public void run() {
		resolveReqeust(this, custName, balance, requestHM);
	}

	public synchronized void resolveReqeust(Bank bank, String custName, int bankBalance,
			ConcurrentHashMap<String, LoanRequest> requestHM) {
		try {
			 while(true) {
			boolean flag = false;
			System.out.println("size: " + requestHM.size());
			if (requestHM.size() > 0) {
				while (!requestHM.isEmpty()) {
					for (String s : requestHM.keySet()) {
						System.out.println("Processing key: "+s);
						if (requestHM.get(s).bank.name.equalsIgnoreCase(Thread.currentThread().getName())) {
							LoanRequest lr = (LoanRequest) requestHM.get(s);
							requestHM.remove(s);
							System.out.println("Name matched.");
							System.out.println(lr.bank.name);
							System.out.println(Thread.currentThread().getName());
							if (lr != null && lr.bank != null && lr.bank.name.equalsIgnoreCase(Thread.currentThread().getName())) {
								System.out.println("bankBalance: "+bankBalance);
								System.out.println("lr.amount "+lr.amount);
								if (bankBalance >= lr.amount) {
									bankBalance = bankBalance - lr.amount;
									System.out.println(lr.bank.name + " approves a loan of " + lr.amount
											+ " dollars from " + lr.cust.name);
								}
							}
						}
					}
					Thread.sleep(10000);
					System.out.println("Processing the request in money thread.");

				}
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}