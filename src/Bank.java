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
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		resolveRequest(this, custName, balance, requestHM);
		System.out.println(this.name+" has "+balance +" dollar(s) remaining.");
	}

	public synchronized void resolveRequest(Bank bank, String custName, int bankBalance,
			ConcurrentHashMap<String, LoanRequest> requestHM) {
		try {
			 System.out.println("size:bank " + requestHM.size());
			while (!requestHM.isEmpty()) {
				//System.out.println("size:bank " + requestHM.size());
				for (String s : requestHM.keySet()) {
					if (requestHM.get(s).bank.equalsIgnoreCase(Thread.currentThread().getName())) {
						//System.out.println("Cust: " + requestHM.get(s).getCust());
						//System.out.println("Bank: " + requestHM.get(s).getBank());
						if (requestHM.get(s).cust != null && requestHM.get(s).cust.length() > 0) {
							LoanRequest lr = (LoanRequest) requestHM.get(s);
							System.out.println("Name matched.");

							if (lr != null && lr.bank != null) {
								System.out.println("2");
								System.out.println("bankBalance: " + balance);
								System.out.println("lr.amount " + lr.amount);
								if (balance >= lr.amount) {
									System.out.println("Bank: " + 1);
									requestHM.remove(s);
									balance = balance - lr.amount;
									System.out.println(
											lr.bank + " approves a loan of " + lr.amount + " dollars from " + lr.cust);

									LoanRequest newlr = new LoanRequest("", this.name, -1, lr.amount);
									requestHM.put(lr.cust, newlr);
								} else {
									System.out.println("Bank: " + 2);
									requestHM.remove(s);
									// requestHM.remove(s);
									System.out.println(
											lr.bank + " denies a loan of " + lr.amount + " dollars from " + lr.cust);
									LoanRequest newlr = new LoanRequest("", this.name, -1, -1);
									requestHM.put(this.name, newlr);
								}
							}
						}
					}
				}			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}