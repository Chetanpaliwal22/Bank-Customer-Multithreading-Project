import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Customer extends Thread {
		
		String name;
		Integer loanReq;
		int disbursedAmt;
		private LinkedBlockingQueue<String> linkedBlockQueue;
		money money = new money();

		Customer(String custName, Integer loanRequirement, int disbursedAmount) {
			name = custName;
			loanReq = loanRequirement;
			disbursedAmt = disbursedAmount;
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
			System.out.println("In customer thread name: ");
			// TODO Auto-generated method stub
			//generateReqeust(this,);

		}
		
		public synchronized void generateReqeust(Customer cust, ArrayList<Bank> bankArrayList,
				Queue<LoanRequest> loanReqQueue) {
			try {
				while (!bankArrayList.isEmpty()) {
					Random rand = new Random();
					int amount = rand.nextInt(50);
					Bank bank = bankArrayList.get(rand.nextInt(bankArrayList.size()));

					//LoanRequest lr = new LoanRequest(custt, bankk, amountt)
					LoanRequest lr;// = new LoanRequest(cust, bank, amount);
//					if (amount < lr.bank.balance) {
//						System.out
//								.println(cust.name + " requests a loan of " + amount + " dollar(s) from " + bank.name);
//						//loanReqQueue.add(lr);
//					} else {
//						System.out.println("Bank Array LIst size: " + bankArrayList.size());
//						bankArrayList.remove(bank);
//					}
					//System.out.println("Size: " + loanReqArray.size());
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}