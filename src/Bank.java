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
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resolveReqeust(this, custName, balance, requestHM);
	}

	public synchronized void resolveReqeust(Bank bank, String custName, int bankBalance,
			ConcurrentHashMap<String, LoanRequest> requestHM) {
		try {
			System.out.println("size:bank " + requestHM.size());
			while (!requestHM.isEmpty()) {
				boolean flag = false;
				System.out.println("size:bank " + requestHM.size());
				if (requestHM.size() > 0) {
					while (!requestHM.isEmpty()) {
						for (String s : requestHM.keySet()) {
							// System.out.println("Processing key: "+s);
							if (requestHM.get(s).bank.name.equalsIgnoreCase(Thread.currentThread().getName())) {
								LoanRequest lr = (LoanRequest) requestHM.get(s);
								System.out.println("Name matched.");
								System.out.println(lr.bank.name);
								System.out.println(Thread.currentThread().getName());
								if (lr != null && lr.bank != null
										&& lr.bank.name.equalsIgnoreCase(Thread.currentThread().getName())) {
									System.out.println("bankBalance: " + bankBalance);
									System.out.println("lr.amount " + lr.amount);
									if (bankBalance >= lr.amount) {
										System.out.println("Bank: "+1);
										requestHM.remove(s);
										bankBalance = bankBalance - lr.amount;
										System.out.println(lr.bank.name + " approves a loan of " + lr.amount
												+ " dollars from " + lr.cust.name);

										LoanRequest newlr = new LoanRequest(lr.cust, this, -1, lr.amount);
										requestHM.put(this.name, newlr);
									} else {
										System.out.println("Bank: "+2);
										
										//requestHM.remove(s);
										System.out.println(lr.bank.name + " denies a loan of " + lr.amount
												+ " dollars from " + lr.cust.name);
										LoanRequest newlr = new LoanRequest(lr.cust, this, -1, -1);
										requestHM.put(this.name, newlr);
									}
								}
							}
						}
						// System.out.println("Processing the request in money thread.");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}