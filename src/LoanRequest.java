
public class LoanRequest {
	Customer cust;
	Bank bank;
	int amount;
	int amountIssued;

	public LoanRequest(Customer custt, Bank bankk, int amountt,int amountIssuedd) {
		cust = custt;
		bank = bankk;
		amount = amountt;
		amountIssued = amountIssuedd;
	}

	public Customer getCust() {
		return cust;
	}

	public void setCust(Customer cust) {
		this.cust = cust;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}