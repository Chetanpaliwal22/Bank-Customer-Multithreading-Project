import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Customer extends Thread {

	String name;
	int loanReq;
	int disbursedAmt;
	private ConcurrentHashMap<String, LoanRequest> requestHM;
	public ArrayList<Bank> bankArrayList;
	money money = new money();

	Customer(String custName, Integer loanRequirement, int disbursedAmount,
			ConcurrentHashMap<String, LoanRequest> linkedBlockQueuee, ArrayList<Bank> bankArrayListt) {
		name = custName;
		loanReq = loanRequirement;
		disbursedAmt = disbursedAmount;
		requestHM = linkedBlockQueuee;
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
				LoanRequest lr = new LoanRequest(this.name, bank.name, amount, 0);
				System.out.println(this.name + " requests a loan of " + amount + " dollar(s) from " + bank.name);
				requestHM.put(this.name, lr);
			} else if (amount > 0) {
				LoanRequest lr = new LoanRequest(this.name, bank.name, loanReq, 0);
				System.out.println(this.name + " requests a loan of " + loanReq + " dollar(s) from " + bank.name);
				requestHM.put(this.name, lr);
			}
		} else {
			System.out.println("Bank is not there to raise the request.");
		}
		generateReqeust(this, bankArrayList, requestHM);
	}

	public synchronized void generateReqeust(Customer cust, ArrayList<Bank> bankArrayList,
			ConcurrentHashMap<String, LoanRequest> requestHM) {
		try {
			while (!requestHM.isEmpty()) {
				boolean requestRaiseFlag = false;

				for (String s : requestHM.keySet()) {
					if (s.equalsIgnoreCase(Thread.currentThread().getName())) {
						LoanRequest lr = (LoanRequest) requestHM.get(s);
						if(lr.amount == -1) {
						System.out.println("1 :"+lr.cust);
						System.out.println("1 :"+lr.bank);
						System.out.println("1 :"+lr.amount);
						System.out.println("1 :"+lr.amountIssued);
						
						if (lr != null && lr.amountIssued > 0) {
							System.out.println("Removing 1");
							requestHM.remove(s);
							loanReq = loanReq - lr.amountIssued;
						} else if (lr.amountIssued == -1) {
							requestHM.remove(s);
							System.out.println("Removing 2");
							bankArrayList.remove(lr.bank);
						}
					}
					}
					if (bankArrayList.contains(s)) {
						System.out.println("2 making it true");
						LoanRequest lr = (LoanRequest) requestHM.get(s);
						if (lr.cust.equalsIgnoreCase(Thread.currentThread().getName())) {
							requestRaiseFlag = true;
						}
					}
				}
				//System.out.println("3");
				// Thread.sleep(1000);
				if (requestRaiseFlag) {
					System.out.println("4");
					if (bankArrayList.size() > 0) {
						Random rand = new Random();
						int amount = rand.nextInt(50);

						Bank bank = bankArrayList.get(rand.nextInt(bankArrayList.size()));

						if (amount <= loanReq) {
							LoanRequest lr = new LoanRequest(name, bank.name, amount, 0);
							System.out.println(
									cust.name + " requests a loan of " + amount + " dollar(s) from " + bank.name);
							requestHM.put(this.name, lr);
						} else if (amount > 0) {
							LoanRequest lr = new LoanRequest(name, bank.name, loanReq, 0);
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