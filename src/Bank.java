import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Bank extends Thread {

	ConcurrentHashMap<String, LoanRequest> requestHM;

	String name;
	int balance;
	String custName;
	money moneyobj;

	Bank(String bankName, int bankBalance, ArrayList<Customer> custArrListt, String custNamee,
			ConcurrentHashMap<String, LoanRequest> requestHMapp,money moneyobjj) {
		name = bankName;
		balance = bankBalance;
		custName = custNamee;
		requestHM = requestHMapp;
		moneyobj = moneyobjj;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		resolveRequest(this, custName, balance, requestHM);
		moneyobj.showBankBalance( this.name, balance);
	}

	
	public synchronized void resolveRequest(Bank bank, String custName, int bankBalance,
			ConcurrentHashMap<String, LoanRequest> requestHM) {
		try {

			while (!requestHM.isEmpty()) {

				for (String s : requestHM.keySet()) {

					if (requestHM != null && !requestHM.isEmpty() && Thread.currentThread().isAlive()
							&& Thread.currentThread().getName() != null
							&& requestHM.get(s).bank.equalsIgnoreCase(Thread.currentThread().getName())) {

						if (requestHM != null && requestHM.get(s).fromCustomerFlag == true) {
							LoanRequest lr = (LoanRequest) requestHM.get(s);

							if (lr != null && lr.bank != null) {

								if (balance >= lr.requestamount) {
									// System.out.println("Bank: " + 1);
									requestHM.remove(s);
									balance = balance - lr.requestamount;
									moneyobj.approveLoanBank(lr.bank,lr.requestamount,lr.cust);

									LoanRequest newlr = new LoanRequest(lr.cust, this.name, 0, lr.requestamount, false,
											true);
									requestHM.put(this.name, newlr);
								} else {
									requestHM.remove(s);
									moneyobj.denyLoanBank(lr.bank,lr.requestamount,lr.cust);
									LoanRequest newlr = new LoanRequest(lr.cust, this.name, -1, -1, false, true);
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