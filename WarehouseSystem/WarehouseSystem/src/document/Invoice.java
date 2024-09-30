package document;

import order.Customer;
import order.Order;

public class Invoice extends Document{
	
	//Attributes
	private Order order;
	private double price;
	private Customer customer;
	private boolean status; //After payment become false
	
	private static int invoiceCount;
	
	//Constants
	private static final String TITLE = "INVOICE";
	private static final double TAX = 0.06;
	
	
	//Constructors
	public Invoice() {this(null,null);}
	public Invoice(Order order, Customer customer) {
		
		super(++invoiceCount,TITLE);
		this.status = true;
		this.order = order;
		this.customer = customer;
		this.price = order.getSubTotal() * TAX + order.getSubTotal();
	}
	
	
	//Getter & Setter
	public Order getOrder() {return order;}
	public Customer getCustomer() {return customer;}
	public double getPrice() {return price;}
	public boolean getStatus() {return status;}
	public void setStatus(boolean status) {this.status = status;}
	
	
	
	/*
	 * This method will first find out the instance of Invoice
	 * then later check does it paid, if not put it in an array then later 
	 * pass the array to the showInvoices() and it will show up all the
	 * unpaid invoice
	 */
	public static void showUnpaidInvoice(Document[] invoices) {
		
		Invoice[] unpaidInvoices = new Invoice[invoiceCount];
		int index = 0;
		for(Document inv : invoices) {
			if(inv == null) break;
			if(inv instanceof Invoice) {

				if(((Invoice)inv).getStatus() == true) {
					unpaidInvoices[index] = (Invoice)inv;
					index++;
				}
				
			}
		}
		showInvoices(unpaidInvoices);
	}
	
	/*
	 * This method will show up all the Invoices based on
	 * the invoice array passed from the parameter
	 */
	public static void showInvoices(Invoice[] invoices) {
		
		System.out.print(" ____________________________________________________________________________ \n"
					   + "| Invoice ID |       Customer       |    Date    |  Payment  |  Amount (RM)  |\n"
					   + "|------------|----------------------|------------|-----------|---------------|\n");
		for(int i = 0; i < invoiceCount; i++) {
			if(invoices[i] == null) break;
			System.out.printf("| %-10d | %-20s | %-10s | %-9s | %-13.2f |\n",
					invoices[i].getId(), invoices[i].customer.getName(),
					invoices[i].getDate(), (invoices[i].status)?"-":"Paid",
					invoices[i].price);
		}
		System.out.println("|____________|______________________|____________|___________|_______________|\n");
	}
	
	/*
	 * 
	 */
	public static Invoice findId(Document[] docments,int invoiceId) {
		
		for(Document inv : docments) {
			if(inv == null) break;
			if(inv instanceof Invoice) {
				if(inv.getId() == invoiceId)
					return (Invoice)inv;
			}
		}
		return null;
	}
	
	
	public void paid() {
		this.status = false;
	}
	
	
	@Override
	public String toString() {
		return super.toString() + 
			String.format(" %-66s\n"
						+ " ___________________________________________________________________\n"
						+ "%s"
						+ " ___________________________________________________________________\n"
						+ " %67s\n"
						+ " %67s\n"
						+ " %67s\n"
						+ " %76s\n"
						+ " ===================================================================\n",
						"Customer: "+customer.getName()+" [ID| "+customer.getId()+"]",
						order.toString(),
						"Sub Total: RM "+ order.getSubTotal(),
						"TAX("+TAX*100+"%): RM " + (String.format("%.2f", price-order.getSubTotal())),
						"Total : RM "+price,
						((status)?"\u001B[31m[Unpaid]":"\u001B[32m[Paid]")+"\u001B[0m");
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Invoice)
			if(this.getId() == ((Invoice)obj).getId())
				return true;
		return false;
	}

	
}
