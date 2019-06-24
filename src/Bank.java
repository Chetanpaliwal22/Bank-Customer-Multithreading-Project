import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

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
		
		try {
			Thread.sleep(100);
		
		resolveRequest(this, custName, balance, requestBQ);
		Thread.sleep(100);
		moneyobj.showBankBalance(this.name, balance);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	public synchronized void resolveRequest(Bank bank, String custName, int bankBalance,
			BlockingQueue<String> requestBQ) {
		try {

			while (!requestBQ.isEmpty()) {
				for (String s : requestBQ) {

					String[] inputArray = s.split(":");

					if (inputArray != null && Boolean.parseBoolean(inputArray[4])
							&& inputArray[1].equalsIgnoreCase(Thread.currentThread().getName()))

						if (inputArray != null && inputArray[1] != null) {

							if (balance >= Integer.parseInt(inputArray[2])) {
								// "Bank: " + 1);
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