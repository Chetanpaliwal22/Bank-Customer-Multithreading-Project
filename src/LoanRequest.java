
public class LoanRequest {
	String cust;
	String bank;
	int amount;
	int amountIssued;

	public LoanRequest(String custt, String bankk, int amountt,int amountIssuedd) {
		cust = custt;
		bank = bankk;
		amount = amountt;
		amountIssued = amountIssuedd;
	}

	public String getCust() {
		return cust;
	}

	public void setCust(String cust) {
		this.cust = cust;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public int getAmountIssued() {
		return amountIssued;
	}

	public void setAmountIssued(int amountIssued) {
		this.amountIssued = amountIssued;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}