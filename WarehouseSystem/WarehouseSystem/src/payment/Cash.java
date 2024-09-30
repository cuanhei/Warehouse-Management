package payment;

public class Cash extends Payment{
	
	private double paidAmount;
	private double refundAmount;
	
	public Cash(double amount, double paidAmount) {
		super(amount);
		this.paidAmount = paidAmount;
		this.refundAmount = calculateRefund(amount, paidAmount);
	}

	
	
	
	public double getPaidAmount() {return paidAmount;}
	public double getRefundAmount() {return refundAmount;}

	
	private static double calculateRefund(double amount, double paidAmount) {
		return paidAmount - amount;
	}
	
	public String toString() {
		return super.toString() + 
				" Paid Amount    : RM " + paidAmount +"\n" +
				" Refunded Amount: RM " + refundAmount;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Cash) {
			return super.equals(obj);
		}
		return false;
	}
}
