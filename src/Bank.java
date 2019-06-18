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
			Thread.sleep(200);
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
				//System.out.println("here in bank");
				//System.out.println("size:bank " + requestHM.size());
				for (String s : requestHM.keySet()) {
					if (requestHM.get(s).bank.equalsIgnoreCase(Thread.currentThread().getName())) {
						
//						System.out.println("Cust: " + requestHM.get(s).getCust());
//						System.out.println("2 :"+requestHM.get(s).cust);
//						System.out.println("2 :"+requestHM.get(s).bank);
//						System.out.println("2 :"+requestHM.get(s).requestamount);
//						System.out.println("2 :"+requestHM.get(s).amountIssued);
//						System.out.println("2 :"+requestHM.get(s).fromCustomerFlag);
//						System.out.println("2 :"+requestHM.get(s).fromBankFlag);
				//		System.out.println("Bank: " + requestHM.get(s).getBank());
						if (requestHM.get(s).cust != null && requestHM.get(s).fromCustomerFlag == true) {
							LoanRequest lr = (LoanRequest) requestHM.get(s);
							System.out.println("Name matched.");

							if (lr != null && lr.bank != null) {
								System.out.println("2");
								System.out.println("bankBalance: " + balance);
								System.out.println("lr.amount " + lr.requestamount);
								if (balance >= lr.requestamount) {
									System.out.println("Bank: " + 1);
									requestHM.remove(s);
									balance = balance - lr.requestamount;
									System.out.println(
											lr.bank + " approves a loan of " + lr.requestamount + " dollars from " + lr.cust);

									LoanRequest newlr = new LoanRequest(lr.cust, this.name, 0, lr.requestamount,false,true);
									requestHM.put(this.name, newlr);
								} else {
									System.out.println("Bank: " + 2);
									requestHM.remove(s);
									// requestHM.remove(s);
									System.out.println(
											lr.bank + " denies a loan of " + lr.requestamount + " dollars from " + lr.cust);
									LoanRequest newlr = new LoanRequest(lr.cust, this.name, -1, -1,false,true);
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