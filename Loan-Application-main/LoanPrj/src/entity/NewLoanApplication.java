package entity;

public class NewLoanApplication {
	private long accNo;
	private double amount;
	private String loanType;
	private int loanDuration;
	
	public NewLoanApplication() {
		super();
		this.accNo = 0;
		this.amount = 0;
		this.loanType = "";
		this.loanDuration = 0;
	}
	
	public NewLoanApplication(long accNo, double amount, String loanType, int loanDuration) {
		super();
		this.accNo = accNo;
		this.amount = amount;
		this.loanType = loanType;
		this.loanDuration = loanDuration;
	}


	public long getAccNo() {
		return accNo;
	}


	public void setAccNo(long accNo) {
		this.accNo = accNo;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public String getLoanType() {
		return loanType;
	}


	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}


	public int getLoanDuration() {
		return loanDuration;
	}


	public void setLoanDuration(int loanDuration) {
		this.loanDuration = loanDuration;
	}
	
}
