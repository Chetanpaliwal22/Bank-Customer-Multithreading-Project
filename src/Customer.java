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
	money moneyobj;

	Customer(String custName, Integer loanRequirement, int disbursedAmount,
			ConcurrentHashMap<String, LoanRequest> requestHMapp, ArrayList<Bank> bankArrayListt,money moneyobjj) {
		name = custName;
		loanReq = loanRequirement;
		disbursedAmt = disbursedAmount;
		requestHM = requestHMapp;
		bankArrayList = bankArrayListt;
		moneyobj = moneyobjj;
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
				moneyobj.loanRequestCustomer(this.name,amount,bank.name);
				requestHM.put(this.name, lr);
			}
		} else {
			System.out.println("Bank is not there to raise the request.");
		}

		generateRequest(this, bankArrayList, requestHM);

		if (loanReq == 0) {
			moneyobj.reachedObjectiveCust(this.name);
		} else {
			moneyobj.noteReachedObjectiveCust(this.name);
		}

	}

	public synchronized void generateRequest(Customer cust, ArrayList<Bank> bankArrayList,
			ConcurrentHashMap<String, LoanRequest> requestHM) {
		try {

			while (!requestHM.isEmpty()) {

				boolean requestRaiseFlag = false;

				for (String s : requestHM.keySet()) {

					LoanRequest lr = (LoanRequest) requestHM.get(s);

					if (lr != null && lr.fromBankFlag == true && Thread.currentThread().isAlive()
							&& Thread.currentThread().getName() != null
							&& requestHM.get(s).cust.equalsIgnoreCase(Thread.currentThread().getName())) {

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
							System.out.println(
									"not going to above two: " + lr.bank + " : " + lr.cust + " : " + lr.amountIssued);
						}
					}
				}

				if (requestRaiseFlag) {

					if (bankArrayList.size() > 0) {
						Random rand = new Random();
						int amount = rand.nextInt(50);

						Bank bank = bankArrayList.get(rand.nextInt(bankArrayList.size()));

						if (amount <= loanReq) {
							LoanRequest lr = new LoanRequest(this.name, bank.name, amount, 0, true, false);
							moneyobj.loanRequestCustomer(this.name,amount,bank.name);
							requestHM.put(this.name, lr);

						} else {
							//System.out.println("Customer has reached the objective. " + cust.name);
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