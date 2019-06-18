import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Customer extends Thread {

	String name;
	Integer loanReq;
	int disbursedAmt;
	LinkedBlockingQueue<LoanRequest> linkedBlockQueue;
	public ArrayList<Bank> bankArrayList = new ArrayList<Bank>();
	money money = new money();

	Customer(String custName, Integer loanRequirement, int disbursedAmount,
			LinkedBlockingQueue<LoanRequest> linkedBlockQueuee) {
		name = custName;
		loanReq = loanRequirement;
		disbursedAmt = disbursedAmount;
		linkedBlockQueue = linkedBlockQueuee;
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
		generateReqeust(this, bankArrayList, linkedBlockQueue);
	}

	public synchronized void generateReqeust(Customer cust, ArrayList<Bank> bankArrayList,
			LinkedBlockingQueue<LoanRequest> linkedBlockQueue) {
		try {
			// while(true) {
			// while (!bankArrayList.isEmpty()) {

			Random rand = new Random();
			int amount = rand.nextInt(50);
			System.out.println("Size: " + bankArrayList.size());
			Bank bank = bankArrayList.get(rand.nextInt(bankArrayList.size()));

			LoanRequest lr = new LoanRequest(cust, bank, amount);
			if (amount < lr.bank.balance) {
				System.out.println(
						"Approve " + cust.name + " requests a loan of " + amount + " dollar(s) from " + bank.name);
				linkedBlockQueue.add(lr);
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