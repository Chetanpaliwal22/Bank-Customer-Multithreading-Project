import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Customer extends Thread {

	ConcurrentHashMap<String, LoanRequest> requestHM;

	String name;
	int loanReq;
	int disbursedAmt;
	// LinkedBlockingQueue<String> lbq;
	public ArrayList<Bank> bankArrayList;
	money money = new money();

	Customer(String custName, Integer loanRequirement, int disbursedAmount,
			ConcurrentHashMap<String, LoanRequest> requestHMapp, ArrayList<Bank> bankArrayListt) {
		name = custName;
		loanReq = loanRequirement;
		disbursedAmt = disbursedAmount;
		requestHM = requestHMapp;
		bankArrayList = bankArrayListt;
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

	@Override
	public void run() {

		if (bankArrayList.size() > 0) {
			Random rand = new Random();
			int amount = rand.nextInt(50);

			Bank bank = bankArrayList.get(rand.nextInt(bankArrayList.size()));
			if (amount <= loanReq) {
				LoanRequest lr = new LoanRequest(this.name, bank.name, amount, 0, true, false);
				System.out.println(this.name + " requests a loan of " + amount + " dollar(s) from " + bank.name);
				requestHM.put(this.name, lr);
			} else if (amount > 0) {
				LoanRequest lr = new LoanRequest(this.name, bank.name, loanReq, 0, true, false);
				System.out.println(this.name + " requests a loan of " + loanReq + " dollar(s) from " + bank.name);
				requestHM.put(this.name, lr);
			}
		} else {
			System.out.println("Bank is not there to raise the request.");
		}

		generateRequest(this, bankArrayList, requestHM);

		if (loanReq == 0) {
			System.out.println("woo hooo");
		} else {
			System.out.println("Boo hoo: " + loanReq);
		}
		// System.out.println("Customer is done. ");
	}

	public synchronized void generateRequest(Customer cust, ArrayList<Bank> bankArrayList,
			ConcurrentHashMap<String, LoanRequest> requestHM) {
		try {
			Thread.sleep(1100);
			while (!requestHM.isEmpty()) {
				// System.out.println("here in customer");
				boolean requestRaiseFlag = false;

				for (String s : requestHM.keySet()) {
					// System.out.println(requestHM.get(s).cust);
					// System.out.println(Thread.currentThread().getName());
					LoanRequest lr = (LoanRequest) requestHM.get(s);

					// System.out.println("1 :"+lr.cust);
					// System.out.println("1 :"+lr.bank);
					// System.out.println("1 :"+lr.requestamount);
					// System.out.println("1 :"+lr.amountIssued);
					// System.out.println("1 :"+lr.fromCustomerFlag);
					// System.out.println("1 :"+lr.fromBankFlag);

					if (lr != null && lr.fromBankFlag == true && Thread.currentThread().isAlive()
							&& requestHM.get(s).cust.equalsIgnoreCase(Thread.currentThread().getName())) {

						// && requestHM.get(s).cust.equalsIgnoreCase(Thread.currentThread().getName())

						if (lr != null && lr.amountIssued >= 0 && lr.fromBankFlag == true) {
							System.out.println("Loan Aproved request received from bank.");
							requestHM.remove(s);
							this.loanReq = this.loanReq - lr.amountIssued;
							requestRaiseFlag = true;
						} else if (lr != null && lr.amountIssued == -1 && lr.fromBankFlag == true) {
							requestHM.remove(s);
							System.out.println("Loan Denied request received from bank.");
							bankArrayList.remove(lr.bank);
							requestRaiseFlag = true;
						} else {
							// System.out.println(
							// "not going to above two: " + lr.bank + " : " + lr.cust + " : " +
							// lr.amountIssued);
						}
					}
				}

				// System.out.println("3");
				// Thread.sleep(1000);
				if (requestRaiseFlag) {
					System.out.println("4");
					if (bankArrayList.size() > 0) {
						Random rand = new Random();
						int amount = rand.nextInt(50);

						Bank bank = bankArrayList.get(rand.nextInt(bankArrayList.size()));

						if (amount <= loanReq) {
							LoanRequest lr = new LoanRequest(this.name, bank.name, amount, 0, true, false);
							System.out.println(
									cust.name + " requests a loan of " + amount + " dollar(s) from " + bank.name);
							requestHM.put(this.name, lr);
						} else if (amount > 0) {
							LoanRequest lr = new LoanRequest(this.name, bank.name, loanReq, 0, true, false);
							System.out.println(
									this.name + " requests a loan of " + loanReq + " dollar(s) from " + bank.name);
							requestHM.put(this.name, lr);
						} else {
							System.out.println("Customer has reached the objective. " + cust.name);
						}
					} else {
						System.out.println("Bank is not there to raise the request.");
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Bank> getBankArrayList() {
		return bankArrayList;
	}

	public void setBankArrayList(ArrayList<Bank> bankArrayList) {
		this.bankArrayList = bankArrayList;
	}
}