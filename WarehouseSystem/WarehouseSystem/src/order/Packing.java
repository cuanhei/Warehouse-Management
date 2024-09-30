package order;

import company.Branch;
import item.Item;

public class Packing {
	
	private final int ID;
	private Order orderToPack;
	private int[] packedQuantity;
	private boolean status;
	
	private static int packingCount;
	
	public Packing() {
		this(null);
	}
	public Packing(Order orderToPack) {
		this.ID = ++packingCount;
		this.orderToPack = orderToPack;
		this.packedQuantity = new int[orderToPack.getQuantity().length];
		this.status = false;
	}
	
	public int getId() {return ID;}
	public Order getOrderToPack() {return orderToPack;}
	public int[] getPackedQuantity() {return packedQuantity;}
	public boolean getStatus() {return status;}
	public void setStatus(boolean status) {this.status = status;}
	
	
	
	public static void showUnfinishPacking(Packing[] packings) {
		System.out.println(" ______________________________ \n"
						 + "| Packing ID | Progress (100%) |\n"
						 + "|------------|-----------------|");
		for(Packing pack : packings) {
			if(pack == null) break; 
			if(pack.status == false)
				System.out.printf("| %10d | %14.2f%% |\n",pack.ID,(double)pack.getTotalPackedQty()/(double)pack.orderToPack.getOrderItemsTotalQty()*100.0);
		}
		System.out.println("|____________|_________________|\n");
	}
	
	public static Packing findUnfinish(Packing[] packings, int packId, Branch branch) {
		for(Packing packing: packings) {
			if(packing == null) break;
			if(packing.status == false && packing.getOrderToPack().getBranch().equals(branch) && packing.getId() == packId)
				return packing;
		}
		return null;
		
	}
	
	public int getPackedItemQty(Item item) {
		Item[] items = orderToPack.getItems();
		for(int i = 0; i < items.length; i++) {
			if(items[i] == null) break;
			if(items[i].equals(item))
				return packedQuantity[i];
		}
		return -1;
	}
	
	public int getTotalPackedQty() {
		int sum =0;
		for(int i = 0; i < packedQuantity.length; i++) {
			sum += packedQuantity[i];
		}
		return sum;
	}
	
	public boolean checkFullyPacked() {
		int packedItemType = 0;
		Item[] items = orderToPack.getItems();
		for(int i = 0; i < items.length; i++) {
			if(packedQuantity[i] == orderToPack.getQuantity()[i]) {
				packedItemType++;
			}
		}
		if(packedItemType == items.length) {
			status = true;
			return true;
		}
		return status;
	}
	
	public void showPackingProgress(Item item) {
		int index = orderToPack.findItemIndex(item);
		if(index == -1) {
			System.out.println("[Invalid Item to pack due to order doesn't contains]");
			return;
		}
		System.out.printf(" ________________________\n"
						+ "| Item ID  | %-12d|\n"
						+ "|----------|-------------|\n"
						+ "| Quantity | (%4d/%-4d) |\n"
						+ "|__________|_____________|\n\n",item.getId(),packedQuantity[index],orderToPack.getQuantity()[index]);
	}
	public int addToPack(Item item, int qty) {
		int overItem = 0;
		Item[] items = orderToPack.getItems();
		for(int i = 0; i < items.length; i++) {
			if(items[i] == null) break;
			if(items[i].equals(item)) {
				int orderItemQty = orderToPack.getItemQty(item);
				if(orderItemQty != -1) {
					if(packedQuantity[i] + qty > orderItemQty) {
						overItem = (packedQuantity[i] + qty) - orderItemQty;
						packedQuantity[i] = orderItemQty;
						break;
					}else {
						packedQuantity[i] += qty;
					}
				}else {
					return -1;//Invalid Item passed
				}
			}
		}
		return overItem;
	}
	
	public boolean correctItem(Item item) {
		Item[] items = orderToPack.getItems();
		for(int i =0; i < items.length; i++) {
			if(items[i].equals(item))
				return true;
		}
		return false;
	}
	public String packingDetails() {
		StringBuilder result = new StringBuilder(); 
		
		if(orderToPack.getItemCount() == 0)
			return "         No Order Record(s)";
	    for (int i = 0; i < orderToPack.getItemCount(); i++) {
	        result.append(String.format(" %2d. %-16s [ID|%-2d]  (%s%-4d\u001B[0m/%-4d)\n", 
	                i+1, orderToPack.getItems()[i].getName(), orderToPack.getItems()[i].getId(),(packedQuantity[i] < orderToPack.getQuantity()[i])?"\u001B[31m":"\u001B[32m" ,packedQuantity[i],orderToPack.getQuantity()[i]));
	    }
	    
	    return result.toString();
	}
	
	
	public boolean packItem(Item item , int quantity) {
		for(int i = 0; i < orderToPack.getItemCount(); i++) {
			if(orderToPack.getItems()[i].equals(item)) {
				packedQuantity[i] += quantity;
				return true;
			}
		}
		return false; //If item not found
	}
	public boolean checkDonepack(Item holdingItem) {
		for(int i = 0; i < orderToPack.getItemCount(); i++) {
			if(orderToPack.getItems()[i].equals(holdingItem)) {
				if(packedQuantity[i] == orderToPack.getQuantity()[i])
					return true;
				break;
			}
		}
		return false; //If item not found
	}
	
	public String toString() {
		return String.format(" =========================================\n"
						   + " Packing ID: %-5d       (Order ID: %-5d)\n"
						   + " =========================================\n", ID,orderToPack.getId()) +
				packingDetails() +" =========================================\n";
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Packing) {
			if(((Packing)obj).ID == this.ID)
				return true;
		}
		return false;
	}
	
}
