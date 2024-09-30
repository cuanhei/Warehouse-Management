package payment;

public class Card extends Payment{
	private String cardNo;
	private String cvv;
	
	public Card(double amount, String cardNo, String cvv) {
		super(amount);
		this.cardNo = cardNo;
		this.cvv = cvv;
	}
	
	public String getCardNo() {return cardNo;}
	public String getCVV() {return cvv;}
	
	public String toString() {
		return super.toString() + 
			" Card No        : " + cardNo;
		
	}
	public boolean equals(Object obj) {
		if(obj instanceof Card) {
			return super.equals(obj);
		}
		return false;
	}
}
