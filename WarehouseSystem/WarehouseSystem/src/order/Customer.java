package order;

import employee.Person;

public class Customer extends Person{
	
	//Attributes
	private final int ID;
	private double amountSpend;
	private static int createdCust;
	
	
	
	//Constructors
	public Customer() {this(" ", " ", " ",0.0);}
	public Customer(String name, String email, String contact, double amountSpend){
		
		super(name,email,contact);
		this.ID = ++createdCust;
		this.amountSpend = amountSpend;
	}
	
	
	//Getter
	public int getId() {return ID;}
	public double getAmountSpend() {return amountSpend;}
	public static int getCreatedCust() {return createdCust;}
	
	
	
	
	public static boolean showCustomers(Customer[] customers) {
		boolean gotCust = false;
		System.out.println(" __________________________________________________________________________ \n"
						 + "| Customer ID |         Name         |        Email        |    Contact    |\n"
						 + "|=============|======================|=====================|===============|");
		
		for(Customer cust : customers) {
			if(cust == null) break;
			System.out.printf("| %-11d | %-20s | %-19s | %-13s |\n",cust.ID, cust.name, cust.email, cust.contact);
			gotCust = true;
		}
		if(!gotCust) 
			System.out.println("|                                                                          |\n"
							  +"|                          \u001B[31mNo Customer Found\u001B[0m                               |\n"
							  +"|                                                                          |");
		
		System.out.println("|_____________|______________________|_____________________|_______________|");
		return gotCust;
	}
	
	public static Customer findId(Customer[] customers, int custId) {
		Customer cust = null;
		for(Customer customer : customers) {
			if(customer == null) break;
			if(customer.getId() == custId) {
				cust = customer;
				break;
			}		
		}
		return cust;
	}
	
	public static void cancelCreateCust() {
		createdCust--;
		
	}
	
	
	public void addSpendAmount(double amount) {
		this.amountSpend += amount;
	}
	

	
	public boolean inputDetails(int index, String input) {
		
		boolean validInput = false;
		switch (index) {
			case 0: //Name
				validInput = inputName(input); 
				break;
			case 1: //Email
				validInput = inputEmail(input); 
				break;
			case 2: //Contact
				validInput = inputContact(input);
				break;
			default:
				System.out.println("[Invalid Input]");
		}
		if(!validInput) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return validInput;
	}

	@Override
	public String toString() {
		return String.format(
				  " ________________________________________ \n" + 
				  "|            Customer Details            |\n"
				+ "|----------------------------------------|\n" + 
				  "|ID            | %-24d|\n"
				+ "|--------------|-------------------------|\n" + 
				  "|Name          | %-24s|\n"
				+ "|--------------|-------------------------|\n" + 
				  "|Email         | %-24s|\n"
				+ "|--------------|-------------------------|\n" + 
				  "|Contact       | %-24s|\n"
				+ "|--------------|-------------------------|\n" + 
				  "|Amount Spend  | RM %-21.2f|\n"
				+ "|______________|_________________________|\n\n" ,
				
		ID, 
		(getName() == null) ? "-" : getName(), 
		(getEmail() == null) ? "-" : getEmail(),
		(getContact() == null) ? "-" : getContact(), 
		amountSpend);
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Customer) {
			if(((Customer)obj).getId() == this.ID)
				return true;
		}
		return false;
	}
}
