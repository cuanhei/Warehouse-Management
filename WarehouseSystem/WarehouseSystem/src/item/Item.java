package item;

import warehouse.Rack;

public class Item {
	
	//Attributes
	private final int ID;
	private String name;
	private String category;
	private double price;
	private double volume;
	
	private static int itemCount = 0;
	
	//Constants
	public static final int MAX_ITEM_TYPE = 100;
	private static final String[] CATEGORIES = {"Pencil", "Pen", "Glue", "Penknife", "Ruler", "Other"}; 
	
	
	
	//Constructors
	public Item() {this(" "," ",0,0);}
	public Item(String name, String category, double price, double volume){
		
		this.ID = ++itemCount;
		this.name = name;
		this.category = category;
		this.price = price;
		this.volume = volume;
	}

	
	
	
	//--- Getter & Setter
	public int getId() {return ID;}
	public String getName() {return name;}
	public String getCategory() {return category;}
	public double getPrice() {return price;}
	public double getVolume() {return volume;}
	public void setName(String name) {this.name = name;}
	public void setCategory(String category) {this.category = category;}
	public void setPrice(double price) {this.price = price;}
	public void setVolume(double volume) {this.volume = volume;}
	public static String[] getCategories() {return CATEGORIES;}
	public static int getItemCount() {return itemCount;}
	
	
	
	
	
	
	public static boolean showAllItems(Item[] items) {

		if(itemCount == 0) return false;
		System.out.println();
		System.out.println("  ___________________________________________________________________");
		System.out.println("   ID |       Name      |  Category  |  Price (RM)  |  Volume (cm\u00B3)");
		System.out.println("  ____|_________________|____________|______________|________________");
		for(int i = 0 ; i < itemCount; i++) {
			System.out.printf("   %-2d | %-15s | %-10s | %-12.2f | %.2f\n",i+1,items[i].name,items[i].category, items[i].price, items[i].volume);
		}
		System.out.println("  ____|_________________|____________|______________|________________");
		return true;
	}
	
	public static Item findId(Item[] items, int id) {
		for(int i = 0; i < itemCount; i++) {
			if(items[i].getId() == id)
				return items[i];
		}
		return null;
	}
	
	public static Item findId(Item[] items,String id) {
		
		return findId(items,Integer.parseInt(id));
	}
	
	public static void showItemQty(Item[] items, int[] quantity) {
		System.out.println("  ________________________________________________________________________________");
		System.out.println("   ID |       Name      |  Category  |  Price (RM)  |  Volume (cm\u00B3)  |  Quantity  ");
		System.out.println("  ____|_________________|____________|______________|________________|____________");
		for(int i = 0 ; i < itemCount; i++) {
			System.out.printf("   %-2d | %-15s | %-10s | %-12.2f | %-15.2f|  %d\n",i+1,items[i].name,items[i].category, items[i].price, items[i].volume, quantity[i]);
		}
		System.out.println("  ____|_________________|____________|______________|________________|____________\n");
		
	}
	
	public static void cancelCreate() {
		itemCount--;
	}
	
	public boolean inputDetails(int index, String input) {
		
		switch(index) {
		case 0: //Item name
			if(input.length()<=0 || input.length() >20)
				System.out.println("[Please input a valid name (Length between 1 ~ 20)]");
			else {
				this.name = input;
				return true;
			}
			break;
		case 1: //Choose Item Category
			int categoryIndex = -1;
			try {
				categoryIndex = Integer.parseInt(input);
			}catch(NumberFormatException e) {
				System.out.println("[Please input a number]");
				break;
			}
			if(categoryIndex < 0 || categoryIndex >CATEGORIES.length-1)
				System.out.println("[Please select a valid Category]");
			else {
				this.category = CATEGORIES[categoryIndex];
				return true;
			}
			break;
		case 2: //Set Item Price
			
			double price;
			try {
				price = Double.parseDouble(input);
			}catch(NumberFormatException e) {
				System.out.println("[Please input a double value]");
				break;
			}
			if(price <= 0)
				System.out.println("[The price of Item CANNOT be less than or ZERO]");
			else {
				this.price = price;
				return true;
			}
			break;
		case 3: //Set Volume
			double volume;
			try {
				volume = Double.parseDouble(input);
			}catch(NumberFormatException e) {
				System.out.println("[Please input a double value]");
				break;
			}
			if(volume <= 0) 
				System.out.println("[Please input a valid Volume]");
			else if(volume > Rack.getMaxGridVolume())
				System.out.println("[The volume is to big to store (Maximum: "+Rack.getMaxGridVolume()+")]");
			else {
				this.volume = volume;
				return true;
			}
			break;
		default:
			System.out.println("[Invalid Input]");
			break;
		}
		return false;
	}
	
	public String toString() {
		
		return String.format("  ____________________________________ \n"
						+ " | ID          | %-20d |\n"
						+ " |-------------|----------------------| \n"
						+ " | Name        | %-20s |\n"
						+ " |-------------|----------------------| \n"
						+ " | Category    | %-20s |\n"
						+ " |-------------|----------------------| \n"
						+ " | Price       | RM %-17.2f |\n"
						+ " |-------------|----------------------| \n"
						+ " | Volume(cm\u00B3) | %-20.2f |\n"
						+ " |_____________|______________________| \n\n",ID,name,category,price,volume);
	}
		
	public boolean equals(Object obj) {
		if(obj instanceof Item) {
			if(this.getId() == ((Item)obj).getId())
				return true;
		}
		return false;
	}

}
