package item;

import company.Branch;
import company.Company;

public class ItemSupplier extends Company{

	//Attributes
	private Item[] sellingItems;
	private double[] price;
	private int itemCount;
	private double amountSpend;
	
	private static int supCount;

	
	
	//Constructors
	public ItemSupplier() {this(" "," "," "," ");}
	public ItemSupplier(String companyName, String email, String contact, String address) {
		
		super(++supCount,companyName,email,contact,address);
		this.sellingItems = new Item[Item.MAX_ITEM_TYPE];
		this.price = new double[Item.MAX_ITEM_TYPE];
		this.itemCount = 0;
		this.amountSpend = 0;
	}


	public Item[] getSellingItems() {return sellingItems;}
	public double[] getPrice() {return price;}
	public int getItemCount() {return itemCount;}
	public double getAmountSpend() {return amountSpend;}
	public static int getSupCount() {return supCount;}
	
	
	
	public static ItemSupplier findId(ItemSupplier[] sups, int id) {

		for(ItemSupplier sup : sups) {
			if(sup == null)
				break;
			if(sup.getId() == id)
				return sup;
		}
		return null;
	}
	
	public static boolean showSuppliers(ItemSupplier[] sups) {
		if(supCount <= 0)
			return false;
		
		System.out.println("  __________________________________________________  ");
		System.out.println(" | ID |   Company Name    | Selling Item Type (Qty) | ");
		System.out.println(" |____|___________________|_________________________| ");
		for(int i = 0; i < supCount; i++) {
			System.out.printf(" | %-2d | %-17s | %-23d |\n", sups[i].getId(), sups[i].getComName(), sups[i].itemCount);
		}
		System.out.println(" |____|___________________|_________________________| \n");
		return true;
	}
	
	public static boolean showSellingItemSup(ItemSupplier[] suppliers, Item item) {
		boolean foundSupplier = false;
		System.out.println("==================================================");
		System.out.println(" ID | Company Name |    Email     | Selling Price ");
		System.out.println("==================================================");
		for(ItemSupplier sup: suppliers) {
			if(sup==null) break;
			if(sup.findItem(item)) {
				System.out.printf(" %-2d   %-12s   %-12s   RM %.2f\n", sup.getId(), sup.getComName(), sup.getComEmail(), sup.getItemPrice(item));
				foundSupplier = true;
			}
		}
		if(!foundSupplier) 
			System.out.println("\u001B[31m[No Supplier(s) found for selling this Item]\u001B[0m");
		System.out.println("==================================================");
		return foundSupplier;
	}
	
	public static void cancelAddSup() {
		supCount--;
	}
	
	public void showDetails() {
		System.out.printf(" [ ID      ] %-20d [ Company Name ] %s\n",getId(),getComName());
		System.out.printf(" [ Email   ] %-20s [ Contact      ] %s\n",getComEmail(), getComContact());
		System.out.println(" [ Address ] " + getAddress());
		
		showSellingItems();
	}
	
	public boolean showSellingItems() {
		System.out.println("________________________________________________________________________");
		if(itemCount==0) 
			System.out.println(" No Selling Item(s) Found!");
		else {
			System.out.println("  Index  |         Item Name         |   Category   |  Selling Price   \n" +
							   "---------|---------------------------|--------------|-------------------");
			for(int i = 0 ; i < itemCount; i++) {
				System.out.printf(" %6d. | %-25s |   %-11s|  RM %.2f\n",i+1,sellingItems[i].getName(),sellingItems[i].getCategory(),price[i]);
			}
		}
		System.out.println("________________________________________________________________________\n");
		
		return (itemCount!=0);
	}
	
	public boolean inputDetails(int index, String input) {
		boolean valid = false;
		switch(index) {
			case 0:
				valid = inputName(input);
				break;
			case 1:
				valid = inputEmail(input);
				break;
			case 2:
				valid = inputContact(input);
				break;
			case 3:
				valid = inputAddress(input);
				break;
			default:
				break;
		}
		return valid;
	}
	
	
	public void addItem(Item item, double price) {
		
		boolean foundItem = false;
		for(int i = 0; i < itemCount; i++) {
			if(sellingItems[i].equals(item)) {
				foundItem = true;
				this.price[i] = price;
				break;
			}
		}
		if(foundItem == false) {
			sellingItems[itemCount] = item;
			this.price[itemCount] = price;
			itemCount++;
		}
	}
	
	public boolean purchaseItem(Branch branch, Item item , int quantity) {
		
		int priceIndex = findSellingItemIndex(item);
		if(priceIndex == -1) return false;
		
		double totalPrice = price[priceIndex]*quantity;
		
		if(branch.getBalance() < totalPrice) {
			System.out.println("[Current Branch doesn't have enought balance to purchace "+quantity+" of (Item ID: "+item.getId() + ")]\n");
			return false;
		}
		
		branch.deductBalance(totalPrice);
		branch.addStoreroomItem(item, quantity);
		amountSpend += totalPrice;
		return true;
	}
	

	
	
	public void removeSellingItem(int itemIndex) {
		for(int i = itemIndex; i < itemCount; i++) {
			sellingItems[i] = sellingItems[i+1];
			price[i] = price[i+1];
		}
		itemCount--;
		
	}
	public int findSellingItemIndex(Item item) {
		
		for(int i = 0; i < itemCount; i++) {
			if(sellingItems[i].equals(item))
				return i;
		}
		return -1;
	}

	public void setItemPrice(int itemIndex, double editPrice) {
		price[itemIndex] = editPrice;
		
	}
	public boolean findItem(Item itemToPurchase) {
		for(Item item : sellingItems) {
			if(item == null) break;
			if(item.equals(itemToPurchase))
				return true;
		}
		return false;
	}
	public double getItemPrice(Item itemToPurchase) {
		double price = -1;
		for(int i = 0; i < itemCount; i++) {
			if(sellingItems[i].equals(itemToPurchase)) {
				price = this.price[i];
			}
		}
		return price;
	}
	
	public String toString() {
		return super.toString() + 
				"Selling Items: " + itemCount + " types\n";
	}
	
	public boolean equals(Object obj) {
		if(super.equals(obj)) {
			if(obj instanceof ItemSupplier) {
				return true;
			}
		} 
		return false;
	}
}
