package order;

import company.Branch;
import document.*;
import item.Item;

public class Order {

	//Attributes
	private final int ID;
	private Item[] items;
	private int[] quantity;
	private boolean packed;
	private int itemCount;
	public Branch branch;
	
	private static int orderCount;
	
	//Constants
	private static final int MAX_ORDER_ITEM = Item.MAX_ITEM_TYPE;
	
	
	
	//Constructors
	public Order() {this(null);}
	public Order(Branch branch) {
		this.ID = ++orderCount;
		this.items = new Item[MAX_ORDER_ITEM];
		this.quantity = new int[MAX_ORDER_ITEM];
		this.packed = false;
		this.itemCount = 0;
		this.branch = branch;
	}
	public Order(Item[] items, int[] quantity, boolean packed, Branch branch, int itemCount) {
		this.ID = ++orderCount;
		this.items = items;
		this.quantity = quantity;
		this.packed = packed;
		this.itemCount = itemCount;
		this.branch = branch;
	}

	
	
	
	//Getter & Setter
	public int getId(){return ID;}
	public Item[] getItems() {return items;}
	public int[] getQuantity() {return quantity;}
	public int getItemCount() {return itemCount;}
	public boolean getPacked() {return packed;}
	public Branch getBranch() {return branch;}
	public static int getMaxOrderItem() {return MAX_ORDER_ITEM;}
	public static int getOrderCount() {return orderCount;}
	public void setPacked(boolean packed) {this.packed = packed;}
	
	
	
	public static boolean showUnpackOrders(Document[] documents, Branch branch, Packing[] packings) {
		boolean haveUnpackOrder = false;
		
		System.out.println(" ____________________________________________________ \n"
						 + "| Order ID | Types of Item | Number Of Items to Pack |\n"
						 + "|----------|---------------|-------------------------|");
		
		for(Document doc : documents) {
			if(doc == null) break;
			if(doc instanceof Invoice) {
				Order order = ((Invoice)doc).getOrder();
				if(!order.packed && order.branch.equals(branch)) {
					boolean packed = false;
					for(Packing packing: packings) {
						if(packing == null) break;
						if(packing.getOrderToPack().equals(order)) {
							packed = true;
							break;
						}
					}
					if(!packed) {
						haveUnpackOrder = true;
						System.out.printf("| %-8d | %-13d | %-23d |\n",order.ID,order.itemCount, order.getOrderItemsTotalQty());
					}
				}
			}
		}
		if(!haveUnpackOrder)
			System.out.println("|                No Unpack Order Found               |");
		System.out.println("|__________|_______________|_________________________|\n");
		return haveUnpackOrder;
	}

	public static Order findUnpackOrderId(Document[] documents, int orderId, Branch branch) {
		for(Document doc : documents) {
			if(doc == null) break;
			if(doc instanceof Invoice) {
				Order order = ((Invoice)doc).getOrder();
				if(!order.packed && order.branch.equals(branch) && order.getId() == orderId) {
					return order;
				}
			}
		}
		return null;
	}
	
	public static void cancelOrder() {
		orderCount--;
	}
	
	public int getOrderItemsTotalQty() {
		int sum = 0;
		for(int i = 0; i < itemCount; i++) {
			sum += quantity[i];
		}
		return sum;
	}
	
	public int findItemIndex(Item item) {
		for(int i = 0 ; i < itemCount; i++) {
			if(items[i].equals(item))
				return i;
		}
		return -1;
	}
	public void showDetails() {
		
		System.out.printf(" ========================================================\n"
						+ " Order ID: %d\n"
						+ " ========================================================\n\n",ID);
		showOrderItems();
		System.out.println("\n ========================================================");
		System.out.printf (" %56s\n","Sub Total: RM " + getSubTotal());
		System.out.println(" ========================================================\n");
	}
	
	public void showOrderItems() {
		if(itemCount == 0)
			System.out.println("         No Order Record(s)");
		for(int i = 0; i < itemCount; i++) {
			System.out.printf(" %2d. %-16s [ID|%-2d]  x%-4d %15s\n", 
					i+1, items[i].getName(), items[i].getId(), quantity[i], "RM " + String.valueOf(quantity[i] * items[i].getPrice()));
		}
	}
	
	public double getSubTotal() {
		double subTotal = 0;
		for(int i = 0; i < itemCount; i++) {
			subTotal += items[i].getPrice() * quantity[i];
		}
		return subTotal;
	}
	

	public void addItem(Item itemToAdd, int quantityToAdd) {
		if(findItemIndex(itemToAdd) != -1 ) 
			quantity[findItemIndex(itemToAdd)] += quantityToAdd;
		else{
			items[itemCount] = itemToAdd;
			quantity[itemCount] = quantityToAdd;
			itemCount++;
		}	
	}
	

	public int getItemQty(Item item) {
		for(int i = 0; i < itemCount; i++ ) {
			if(items[i]== null) break;
			if(items[i].equals(item))
				return quantity[i];
		}
		return -1;	
	}
	
	public String toString() {
		
		StringBuilder result = new StringBuilder(); 
		
		if(itemCount == 0)
			return "         No Order Record(s)";
	    for (int i = 0; i < itemCount; i++) {
	        result.append(String.format(" %2d. %-16s [ID|%-2d]  x%-4d %15s\n", 
	                i+1, items[i].getName(), items[i].getId(), quantity[i], 
	                "RM " + String.format("%.2f", quantity[i] * items[i].getPrice())));
	    }
	    
	    return result.toString();
	}

	public boolean equals(Object obj) {
		if(obj instanceof Order) {
			if(((Order)obj).getId() == this.ID)
				return true;
		}
		return false;
	}
	
}
