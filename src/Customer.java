import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Customer extends Thread {

	BlockingQueue<String> requestBQ;

	String name;
	int loanReq;
	int disbursedAmt;

	public ArrayList<String> bankArrayList = new ArrayList<String>();;
	money moneyobj;

	Customer(String custName, Integer loanRequirement, int disbursedAmount, BlockingQueue<String> requestBQQ,
			ArrayList<String> bankArrayListt, money moneyobjj) {
		name = custName;
		loanReq = loanRequirement;
		disbursedAmt = disbursedAmount;
		requestBQ = requestBQQ;
		moneyobj = moneyobjj;

		for (int i = 0; i < bankArrayListt.size(); i++) {
			bankArrayList.add(bankArrayListt.get(i));
		}
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
				int amount = rand.nextInt(50) + 1;

				String bankName = bankArrayList.get(rand.nextInt(bankArrayList.size()));
				if (amount <= loanReq) {
					String lr = this.name + ":" + bankName + ":" + amount + ":" + 0 + ":" + true + ":" + false;
					moneyobj.loanRequestCustomer(this.name, amount, bankName);
					requestBQ.put(lr);
				} else if (this.loanReq > 0) {
					String lr = this.name + ":" + bankName + ":" + this.loanReq + ":" + 0 + ":" + true + ":" + false;
					moneyobj.loanRequestCustomer(this.name, this.loanReq, bankName);
					requestBQ.put(lr);
				}
			} else {
				// "Bank is not there to raise the request.");
			}

			generateRequest(this, bankArrayList, requestBQ);

			if (loanReq == 0) {
				moneyobj.reachedObjectiveCust(this.name, this.disbursedAmt);
			} else {
				moneyobj.noteReachedObjectiveCust(this.name, this.disbursedAmt);
			}
			// "customer thread end" + this.name);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public synchronized void generateRequest(Customer cust, ArrayList<String> bankArrayList,
			BlockingQueue<String> requestBQ) {
		try {

			while (!requestBQ.isEmpty()) {
				boolean removeBankData = false;
				String sremove = "";
				Random rand = new Random();
				Thread.sleep(rand.nextInt(100));
				boolean requestRaiseFlag = false;

				for (String s : requestBQ) {

					String[] inputArray = s.split(":");

					if (inputArray != null && Boolean.parseBoolean(inputArray[5])
							&& inputArray[0].equalsIgnoreCase(Thread.currentThread().getName())) {

						if (Integer.parseInt(inputArray[3]) >= 0) {
							removeBankData =true;
							sremove = s;
							//requestBQ.remove(s);
							this.loanReq = this.loanReq - Integer.parseInt(inputArray[3]);
							this.disbursedAmt = this.disbursedAmt + Integer.parseInt(inputArray[3]);
							requestRaiseFlag = true;
						} else if (Integer.parseInt(inputArray[3]) == -1) {
							//requestBQ.remove(s);
							sremove = s;
							bankArrayList.remove(String.valueOf(inputArray[1]).trim());
							removeBankData = true;
							requestRaiseFlag = true;
						} else {
							// "not going to above two: " + inputArray[1] + " : " + inputArray[0]
							// + " : " + inputArray[3]);
						}
					}
				}

				if (requestRaiseFlag) {

					if (bankArrayList.size() > 0) {
						int amount = rand.nextInt(50) + 1;

						String bankName = bankArrayList.get(rand.nextInt(bankArrayList.size()));

						if (amount <= this.loanReq) {
							String newlr = this.name + ":" + bankName + ":" + amount + ":" + 0 + ":" + true + ":"
									+ false;
							moneyobj.loanRequestCustomer(this.name, amount, bankName);
							requestBQ.put(newlr);
							if(removeBankData) {
								requestBQ.remove(sremove);
							}
						} else if (this.loanReq > 0) {

							String newlr = this.name + ":" + bankName + ":" + this.loanReq + ":" + 0 + ":" + true + ":"
									+ false;
							moneyobj.loanRequestCustomer(this.name, this.loanReq, bankName);
							requestBQ.put(newlr);
							if(removeBankData) {
								requestBQ.remove(sremove);
							}
						} else {

							if(removeBankData) {
								requestBQ.remove(sremove);
							}
							// "Customer has reached the objective. " + cust.name);
						}

					} else {
						if(removeBankData) {
							requestBQ.remove(sremove);
						}
						// Bank is not there to raise the request.
					}
				}
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getBankArrayList() {
		return bankArrayList;
	}

	public void setBankArrayList(ArrayList<String> bankArrayList) {
		this.bankArrayList = bankArrayList;
	}
}