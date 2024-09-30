package payment;

public abstract class Payment {
	
	private final int ID;
	private double amount;
	
	private static int paymentCount;
	
	protected Payment(double amount) {
		this.ID = ++paymentCount;
		this.amount = amount;
	}
	
	public double getAmount() {return amount;}
	public void setAmount(double amount) {this.amount = amount;}
	
	public String toString() {
		return "Amount         : RM " + amount + "\n";
	}
	public boolean equals(Object obj) {
		if(obj instanceof Payment) {
			if(((Payment)obj).ID == this.ID)
				return true;
		}
		return false;
	}
}
