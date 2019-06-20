
public class LoanRequest {
	String cust;
	String bank;
	int requestamount;
	int amountIssued;
	boolean fromCustomerFlag;
	boolean fromBankFlag;

	public LoanRequest(String custt, String bankk, int amountt, int amountIssuedd, boolean fromCustomerFlagg,
			boolean fromBankFlagg) {
		cust = custt;
		bank = bankk;
		requestamount = amountt;
		amountIssued = amountIssuedd;
		fromCustomerFlag = fromCustomerFlagg;
		fromBankFlag = fromBankFlagg;
	}

	public boolean isFromCustomerFlag() {
		return fromCustomerFlag;
	}

	public void setFromCustomerFlag(boolean fromCustomerFlag) {
		this.fromCustomerFlag = fromCustomerFlag;
	}

	public boolean isFromBankFlag() {
		return fromBankFlag;
	}

	public void setFromBankFlag(boolean fromBankFlag) {
		this.fromBankFlag = fromBankFlag;
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
		return requestamount;
	}

	public void setAmount(int amount) {
		this.requestamount = amount;
	}
}