import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Customer extends Thread {

	String name;
	Integer loanReq;
	int disbursedAmt;
	private ConcurrentHashMap<String,LoanRequest> requestHM;
	public ArrayList<Bank> bankArrayList;
	money money = new money();

	Customer(String custName, Integer loanRequirement, int disbursedAmount,
			ConcurrentHashMap<String,LoanRequest> linkedBlockQueuee,ArrayList<Bank> bankArrayListt) {
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
		generateReqeust(this, bankArrayList, requestHM);
	}

	public synchronized void generateReqeust(Customer cust, ArrayList<Bank> bankArrayList,
			 ConcurrentHashMap<String,LoanRequest> requestHM) {
		try {
			// while(true) {
			// while (!bankArrayList.isEmpty()) {
			Random rand = new Random();
			int amount = rand.nextInt(50);
			System.out.println("Size in Customer Bank Array: " + bankArrayList.size());
			Bank bank = bankArrayList.get(rand.nextInt(bankArrayList.size()));

			LoanRequest lr = new LoanRequest(cust, bank, amount);
			System.out.println("lr.getBankName(): "+lr.bank.name);
			if (amount < lr.bank.balance) {
				System.out.println(cust.name + " requests a loan of " + amount + " dollar(s) from " + bank.name);
				requestHM.put(lr.cust.name,lr);
			} else {
				System.out.println("Deny Bank Array LIst size: " + bankArrayList.size());
				// bankArrayList.remove(bank);
			}
			// System.out.println("Size: " + loanReqArray.size());
			// Thread.sleep(1000);
			// }
			// }
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