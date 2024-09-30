package report;

import java.util.Arrays;

import order.Customer;

public class CustomerReport extends Report{

		
	private Customer[] customers;
	
    private static final String TITLE = "TOP 10 CUSTOMER SPEND THE MOST";
    

    public CustomerReport(Customer[] customers) {
        super(TITLE);
        this.customers = Arrays.copyOf(customers, customers.length);
    }

    // Sort customers by amountSpend in descending order using bubble sort
    private void sortCustomers() {
  
        for (int i = 0; i < customers.length - 1; i++) {
            for (int j = 0; j < customers.length - 1 - i; j++) {
            	if(customers[j + 1] == null) break;
                if (customers[j].getAmountSpend() < customers[j + 1].getAmountSpend()) {
                    // Swap customers[j] and customers[j + 1]
                    Customer temp = customers[j];
                    customers[j] = customers[j + 1];
                    customers[j + 1] = temp;
                }
            }
        }
    }
	
    
    // Instance method to print a vertical bar chart in the console
    @Override
    public void drawReport() {
    	sortCustomers();
    	System.out.println(super.toString());
    	System.out.println("  _________________________________________________________________________________________________ \n"
						 + " | Customer ID |         Name         |        Email        |    Contact    |  Total Amount Spend  |\n"
						 + " |=============|======================|=====================|===============|======================|");

		for(int i = 0; i < 10; i++) {
			if(customers[i] == null) break;
			System.out.printf(" | %-11d | %-20s | %-19s | %-13s |  RM %-15.2f  |\n",customers[i].getId(), customers[i].getName(), customers[i].getEmail(), customers[i].getContact(),customers[i].getAmountSpend());
		}
		System.out.println(" |_____________|______________________|_____________________|_______________|______________________|\n");
    }
    
    @Override
    public String toString() {
    	return super.toString();
    }
    @Override
    public boolean equals(Object obj) {
    	return super.equals(obj);
    }
}
