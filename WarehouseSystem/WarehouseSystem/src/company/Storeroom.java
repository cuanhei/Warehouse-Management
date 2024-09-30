package company;

import item.Item;


public class Storeroom {
	
	//Attributes
	private final int ID;
	private Item[] items;
	private int[] itemsQty;
	private int itemCount;//Index of the array 
	
	private static int storeroomCount;
	
	
	//Constructors
	public Storeroom() {
		
		this.ID = ++storeroomCount;
		this.items = new Item[Item.MAX_ITEM_TYPE];
		this.itemsQty = new int[Item.MAX_ITEM_TYPE];
	}
	
	
	
	
	//Getter & Setter
	public int getId() {return ID;}
	public Item[] getItems() {return items;}
	public int[] getItemsQty() {return itemsQty;}
	public int itemCount() {return itemCount;}

	
	
	  ///////////////////////////////////////////
	 ///////////// Instance Methods ////////////
	///////////////////////////////////////////
	
	/*
	 * This method will sum up all of the
	 * items even it is different type and then 
	 * get total quantity and return 
	 */
	public int getTotalItemInSRoom() {
		
		int sum = 0;
		for(int i = 0; i < itemCount; i++) {
			sum+= itemsQty[i];
		}
		return sum;
	}
	
	/*
	 * This method will find for the item and
	 * then it will return the item's total quantity in 
	 * the storeroom
	 */
	public int getItemCount(Item item) {
		int sum = 0;
		for(int i = 0 ; i < itemCount; i++) {
			if(items[i].equals(item))
				sum += itemsQty[i];
		}
		return sum;
	}
	
	/*
	 * This method will check does the item exists in
	 * the current storeroom
	 */
	public boolean containsItem(Item item) {
		if(getItemIndex(item)!= -1)
			return true;
		return false;
	}
	
	/*
	 * This method will add up the Item and the
	 * quantity based on the parameter passed in
	 */
	public void addItem(Item item, int quantity) {
		int storeIndex = getItemIndex(item);
		if(storeIndex != -1) 
			itemsQty[storeIndex] += quantity;
		else {
			items[itemCount] = item;
			itemsQty[itemCount] = quantity;
			itemCount++;
		}
	}
	
	/*
	 * This method will remove the item quantity based
	 * on the parameter and also it will remove the item
	 * if all of it was took 
	 */
	public boolean removeItem(Item item, int quantity) {
		int storeIndex = getItemIndex(item);
		if(storeIndex != -1) {
			if(itemsQty[storeIndex]> quantity) {
				itemsQty[storeIndex]-= quantity;
				return true;
			}else if(itemsQty[storeIndex] == quantity) {
				shiftItem(item);
				return true;
			}else {
				System.out.println("\n[You can only MAXIMUM take "+itemsQty[storeIndex]+" of (Item ID: "+item.getId()+")from the Storeroom]\n");
			}
		}
		else {
			System.out.println("\n[Sorry, currently the Item ("+(item.getName())+") doesn't found in this storeroom]\n");
		}
		return false;
	}

	/*
	 * This will shift the index of the items
	 * it is used when the item has no longer with
	 * any quantity
	 */
	private void shiftItem(Item item) {
		for(int i = 0; i < itemCount-1; i++) {
			items[i] = items[i+1];
			itemsQty[i] = itemsQty[i+1];
		}
		itemCount--;
		
	}

	/*
	 * This will check from the items in the 
	 * storeroom and if found the item it return
	 * the index else it will return -1
	 */
	private int getItemIndex(Item item) {
		for(int i = 0; i < itemCount; i++) {
			if(items[i].equals(item))
				return i;
		}
		return -1;
	}
	
	/*
	 * This method will show the details of the items in the storeroom
	 * (it is showing the item ID, item name, and the quantity in table format)
	 * if there is no items it will just show "No Item(s) found in Storeroom!"
	 */
	public boolean showDetails() {
		System.out.println("====================\n"
						 + " Items In Storeroom \n"
						 + "====================\n");
		if(itemCount == 0) 
			System.out.println("No Item(s) found in Storeroom!\n");
		else {
			System.out.println("_______________________________________\n"
							+  " ID |       Item Name       | Quantity \n"
							+  "____|_______________________|__________");
			for(int i = 0; i < itemCount; i++) {
				System.out.printf(" %-2d | %-21s | %d\n", items[i].getId(), items[i].getName(), itemsQty[i]);
			}
			System.out.println("____|_______________________|__________\n");
			return true;
		}
		return false;
	}

	
	@Override
	public String toString() {
		
		return "Storeroom ID: " + ID + 
				"\nItems type: " + itemCount + "\n";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Storeroom) {
			Storeroom storeroom = (Storeroom)obj;
			if(this.ID == storeroom.ID)
				return true;
		}
		return false;
	}
	
}
