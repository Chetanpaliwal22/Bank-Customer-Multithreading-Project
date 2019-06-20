import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Customer extends Thread {

	BlockingQueue<String> requestBQ;

	String name;
	int loanReq;
	int disbursedAmt;

	public ArrayList<Bank> bankArrayList;
	money moneyobj;

	Customer(String custName, Integer loanRequirement, int disbursedAmount, BlockingQueue<String> requestBQQ,
			ArrayList<Bank> bankArrayListt, money moneyobjj) {
		name = custName;
		loanReq = loanRequirement;
		disbursedAmt = disbursedAmount;
		requestBQ = requestBQQ;
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
		try {

			if (bankArrayList.size() > 0) {
				Random rand = new Random();
				int amount = rand.nextInt(50)+1;

				Bank bank = bankArrayList.get(rand.nextInt(bankArrayList.size()));
				if (amount <= loanReq) {
					String lr = this.name + ":" + bank.name + ":" + amount + ":" + 0 + ":" + true + ":" + false;
					moneyobj.loanRequestCustomer(this.name, amount, bank.name);
					requestBQ.put(lr);
				}else if (this.loanReq > 0) {
					String lr = this.name + ":" + bank.name + ":" + this.loanReq + ":" + 0 + ":" + true + ":"
							+ false;
					moneyobj.loanRequestCustomer(this.name, this.loanReq, bank.name);
					requestBQ.put(lr);
				}
			} else {
				System.out.println("Bank is not there to raise the request.");
			}

			generateRequest(this, bankArrayList, requestBQ);

			if (loanReq == 0) {
				moneyobj.reachedObjectiveCust(this.name, this.disbursedAmt);
			} else {
				moneyobj.noteReachedObjectiveCust(this.name, this.disbursedAmt);
			}
			//System.out.println("customer thread end" + this.name);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public synchronized void generateRequest(Customer cust, ArrayList<Bank> bankArrayList,
			BlockingQueue<String> requestBQ) {
		try {

			while (!requestBQ.isEmpty()) {
Thread.sleep(100);
				boolean requestRaiseFlag = false;

				for (String s : requestBQ) {

					String[] inputArray = s.split(":");

					if (inputArray != null && Boolean.parseBoolean(inputArray[5])
							&& inputArray[0].equalsIgnoreCase(Thread.currentThread().getName())) {

						if (Integer.parseInt(inputArray[3]) >= 0) {
							//System.out.println("Loan Aproved request received from bank.");

							requestBQ.remove(s);
							this.loanReq = this.loanReq - Integer.parseInt(inputArray[3]);
							//System.out.println("loan req: " + this.loanReq);
							this.disbursedAmt = this.disbursedAmt + Integer.parseInt(inputArray[3]);
							requestRaiseFlag = true;
						} else if (Integer.parseInt(inputArray[3]) == -1) {
							requestBQ.remove(s);
							//System.out.println("Loan Denied request received from bank.");
							bankArrayList.remove(inputArray[1]);
							requestRaiseFlag = true;
						} else {
							System.out.println("not going to above two: " + inputArray[1] + " : " + inputArray[0]
									+ " : " + inputArray[3]);
						}
					}
				}

				if (requestRaiseFlag) {

					if (bankArrayList.size() > 0) {
						Random rand = new Random();
						int amount = rand.nextInt(50)+1;

						Bank bank = bankArrayList.get(rand.nextInt(bankArrayList.size()));

						if (amount <= this.loanReq) {
							String newlr = this.name + ":" + bank.name + ":" + amount + ":" + 0 + ":" + true + ":"
									+ false;
							moneyobj.loanRequestCustomer(this.name, amount, bank.name);
							requestBQ.put(newlr);
						} else if (this.loanReq > 0) {
							String newlr = this.name + ":" + bank.name + ":" + this.loanReq + ":" + 0 + ":" + true + ":"
									+ false;
							moneyobj.loanRequestCustomer(this.name, this.loanReq, bank.name);
							requestBQ.put(newlr);
						} else {
							//System.out.println("Customer has reached the objective. " + cust.name);
						}

					} else {
						System.out.println("Bank is not there to raise the request.");
					}
				}
			}

		} catch (

		Exception e) {
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