package document;

import payment.Payment;

public class Receipt extends Document{

	//Attributes
	private Payment paymentMethod;
	private Invoice paidInvoice;
	private static int receiptCount;
	
	
	//Constants
	private static final String TITLE = "RECEIPT";
	
	
	//Constructors
	public Receipt() {this(null,null);}
	public Receipt(Payment paymentMethod, Invoice paidInvoice) {
		
		super(++receiptCount,TITLE);
		this.paymentMethod = paymentMethod;
		this.paidInvoice = paidInvoice;
	}

	
	
	
	//getter & Setter
	public Payment getPaymentMethod() {return paymentMethod;}
	public void setPaymentMethod(Payment paymentMethod) {this.paymentMethod = paymentMethod;}
	public Invoice getPaidInvoice() {return paidInvoice;}
	public static int getReceiptCount() {return receiptCount;}
	
	

	@Override
	public String toString() {
		return super.toString() + 
				String.format(" %-66s\n"
						+ " ___________________________________________________________________\n"
						+ "%s"
						+ " ___________________________________________________________________\n"
						+ " %s\n"
						+ " ===================================================================\n",
						"Invoice ID: "+paidInvoice.getId(),
						paidInvoice.getOrder().toString(),
						paymentMethod.toString()
						);
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Receipt)
			if(((Receipt)obj).getId() == this.getId())
				return true;
		return false;
	}
	
}
