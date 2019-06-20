import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Bank extends Thread {

	BlockingQueue<String> requestBQ;

	String name;
	int balance;
	String custName;
	money moneyobj;

	Bank(String bankName, int bankBalance, ArrayList<Customer> custArrListt, String custNamee,
			BlockingQueue<String> requestHMapp, money moneyobjj) {
		name = bankName;
		balance = bankBalance;
		custName = custNamee;
		requestBQ = requestHMapp;
		moneyobj = moneyobjj;
	}

	@Override
	public void run() {
		// synchronized (requestHM) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		resolveRequest(this, custName, balance, requestBQ);
		// System.out.println("bank thread end"+this.name);
		moneyobj.showBankBalance(this.name, balance);
		// }
	}

	public synchronized void resolveRequest(Bank bank, String custName, int bankBalance,
			BlockingQueue<String> requestBQ) {
		try {

			while (!requestBQ.isEmpty()) {
				Thread.sleep(150);
				for (String s : requestBQ) {

					String[] inputArray = s.split(":");

					if (inputArray != null && Boolean.parseBoolean(inputArray[4])
							&& inputArray[1].equalsIgnoreCase(Thread.currentThread().getName()))

						if (inputArray != null && inputArray[1] != null) {

							if (balance >= Integer.parseInt(inputArray[2])) {
								// System.out.println("Bank: " + 1);
								this.balance = this.balance - Integer.parseInt(inputArray[2]);
								moneyobj.approveLoanBank(inputArray[1], Integer.parseInt(inputArray[2]), inputArray[0]);
								String newlr = inputArray[0] + ":" + this.name + ":" + -1 + ":" + inputArray[2] + ":"
										+ false + ":" + true;

								requestBQ.put(newlr);
								requestBQ.remove(s);
							} else {
								moneyobj.denyLoanBank(inputArray[1], Integer.parseInt(inputArray[2]), inputArray[0]);
								String newlr = inputArray[0] + ":" + this.name + ":" + -1 + ":" + -1 + ":" + false + ":"
										+ true;

								requestBQ.put(newlr);
								requestBQ.remove(s);
							}
						}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}