package warehouse;

import company.Storeroom;
import item.Item;


public class Rack {
	
	//Attributes
	private final int ID; 
	private final char RACK_ID; //Each rack under each floor have unique ID
	private String category;
	private boolean status; //False = FULL
	private Item[][] items;
	private int[][] quantity;

	private static int rackCount;
	
	//Constants
	public static final int[] RACK_SIZE = {5,8}; // Grid [x] [y]
	private static final double MAX_GRID_VOLUME = 1000; // 1000 cm3
	
	
	
	
	//Constructors
	public Rack() {
		this(0," ",false);
	}
	public Rack(int rackCount, String category, boolean status) { 
		
		this.ID = ++Rack.rackCount;
		this.RACK_ID = generateRackId(rackCount);
		this.category = category;
		this.status = status;
		this.items = new Item[RACK_SIZE[0]][RACK_SIZE[1]];
		this.quantity = new int[RACK_SIZE[0]][RACK_SIZE[1]];
	}
	public Rack(int rackCount, String category, boolean status, Item[][] items, int[][] quantity) { 
		
		this.ID = ++Rack.rackCount;
		this.RACK_ID = generateRackId(rackCount);
		this.category = category;
		this.status = status;
		this.items = items;
		this.quantity = quantity;
	}
	
	
	
	
	//Getter & Setter
	public int getId() {return ID;}
	public char getRackId() {return RACK_ID;}
	public String getCategory() {return category;}
	public Item[][] getItems(){return items;}
	public int[][] getQuantity() {return quantity;}
	public boolean getStatus() {return status;}
	public void setCategory(String category) {this.category = category;}
	public void setStatus(boolean status) {this.status = status;}
	public static double getMaxGridVolume() {return MAX_GRID_VOLUME;}
	public static int getRackCount() {return rackCount;}
	public static int getMaxRow() {return RACK_SIZE[0];}
	public static int getMaxCol() {return RACK_SIZE[1];}
	
	
	
	
	  ///////////////////////////////////////////
	 ////////////// Static Methods /////////////
	///////////////////////////////////////////
	
	/*
	 * This method is used to generate the rack ID based on
	 * the number of rack currently having under the FLOOR
	 * which means when a floor contains ZERO rack the rack ID
	 * will be generated as 'A' if the floor contains 2 racks already
	 * it will generate rack ID 'C' for the rack
	 */
	private static char generateRackId(int rackCount) {
		return (char)(rackCount + (int)'A');
	}

	

	  ///////////////////////////////////////////
	 ///////////// Instance Methods ////////////
	///////////////////////////////////////////

	
	/*
	 * This method will based on the passed Item and check how many
	 * same type of the item in the total rack of this floor
	 */
	public int getItemCount(Item item) {
		int sum = 0;
		for(int i = 0; i < RACK_SIZE[0]; i++) {
			for(int x = 0; x < RACK_SIZE[1]; x++) {
				if(items[i][x]!=null) {
					if(items[i][x].equals(item))
						sum+= quantity[i][x];
				}
			}
		}
		return sum;
	}
	public void showGrid() {
		
		for(int i = 0; i < RACK_SIZE[0]; i++) {
			for(int x = 0; x < RACK_SIZE[1]*10 + 1; x++) 
				System.out.print("=");
			System.out.println();
			for(int j = 0; j < RACK_SIZE[1]; j++) {
				if(items[i][j]!=null)
					System.out.printf("| %s[%-2d]%s%-3d ", "\u001B[36m",items[i][j].getId(),"\u001B[0m",quantity[i][j]);
				else
					System.out.printf("|         ");
			}
			System.out.println("|");
		}
		for(int x = 0; x < RACK_SIZE[1]*10 + 1; x++) 
			System.out.print("=");
		System.out.println("\n");
	}
	
	public void showGrid(Item item) {
		
		for(int i = 0; i < RACK_SIZE[0]; i++) {
			for(int x = 0; x < RACK_SIZE[1]*6 + 1; x++) 
				System.out.print("=");
			System.out.println();
			for(int j = 0; j < RACK_SIZE[1]; j++) {
				if(items[i][j] == null)
					System.out.printf("|     ");
				else if(items[i][j].equals(item)) 
					System.out.printf("| %s%-3d%s ","\u001B[32m",quantity[i][j], "\u001B[0m");
				else
					System.out.printf("| %sXXX%s ","\u001B[31m", "\u001B[0m");
			}
			System.out.println("|");
		}	
		for(int x = 0; x < RACK_SIZE[1]*6 + 1; x++) 
			System.out.print("=");
		System.out.println("\n");
	}

	
	public String getStatusString() {
		return (status)?"Valid":"Full";
	}
	
	
	public int getTotalItemsQty() {
		
		int sum = 0;
		
		for(int i = 0; i < RACK_SIZE[0]; i++) {
			for(int j = 0; j < RACK_SIZE[1]; j++) {
				sum+= quantity[i][j];
			}
		}
		return sum;
	}
	
	public void storeItemsToSR(Storeroom storeroom) {
		
		for(int i = 0; i < RACK_SIZE[0]; i++) {
			for(int j = 0; j < RACK_SIZE[1]; j++) {
				if(this.items[i][j] != null) {
					storeroom.addItem(items[i][j], quantity[i][j]);
				}
			}
		}
	}
	
	
	

	// It will return the remaining item quantity
	public int addItem(Item item, int quantity, int row, int col) {
	    double currentGridVolume = 0;

	    // Check if the grid already contains an item
	    if (items[row][col] != null) {
	        // If the same item, try to add more
	        if (items[row][col].equals(item)) {
	            currentGridVolume = this.quantity[row][col] * item.getVolume();

	            // Check if grid is already full
	            if (currentGridVolume >= MAX_GRID_VOLUME) {
	                System.out.println("[This grid is FULL! Please choose another grid]");
	                try {
	        	        Thread.sleep(2000); // 2 seconds pause
	        	    } catch (InterruptedException e) {
	        	        e.printStackTrace();
	        	    }
	                return -1; // Return the same quantity since none can be stored
	            } else {
	                // Calculate how much can be stored based on remaining grid volume
	                int quantityToStore = calMaxAmountCanStore(currentGridVolume, item.getVolume(), quantity);
	                this.quantity[row][col] += quantityToStore; // Update quantity in the grid
	                quantity -= quantityToStore; // Subtract stored quantity from remaining quantity
	            }
	        } else {
	            System.out.println("[Oops! Cannot put different items in one grid]");
	            try {
	    	        Thread.sleep(2000); // 2 seconds pause
	    	    } catch (InterruptedException e) {
	    	        e.printStackTrace();
	    	    }
	            return -1;
	        }
	    } else {
	        // Grid is empty, place the item here
	        items[row][col] = item;
	        int quantityToStore = calMaxAmountCanStore(currentGridVolume, item.getVolume(), quantity);
	        this.quantity[row][col] += quantityToStore; // Place item in grid
	        quantity -= quantityToStore; // Subtract stored quantity from remaining quantity
	    }

	    return quantity; // Return remaining quantity after storing
	}

	
	
	private int calMaxAmountCanStore(double currentGridVolume, double volumePerItem, int quantity) {
	    // Calculate the maximum volume the grid can store
	    double availableVolume = MAX_GRID_VOLUME - currentGridVolume;

	    // Calculate how many items can fit in the available volume
	    int maxQuantity = (int) (availableVolume / volumePerItem);

	    // Return the smaller of maxQuantity or the input quantity (whichever is less)
	    return Math.min(maxQuantity, quantity);
	}

	
	
	public int takeItem(int row, int col, Item item, int quantity) {
		if(item.equals(items[row][col])) {
			if(this.quantity[row][col] > quantity) {
				this.quantity[row][col] -= quantity;
				return quantity;
			}else if(this.quantity[row][col] == quantity) {
				this.quantity[row][col] = 0;
				this.items[row][col] = null;
				return quantity;
			}else {
				
				int canTake = this.quantity[row][col];
				items[row][col] = null;
				this.quantity[row][col] = 0;
				return canTake;
			}
		}
		return -1;
	}
	
	public Item getItem(int row, int col) {
		if(row > RACK_SIZE[0] || col > RACK_SIZE[1] || row < 0 || col < 0) 
			return null;

		if(items[row][col] != null) {
			return items[row][col];
		}
		return null;
	}
	
	
	public void checkRackFull() {
		boolean notFull = false;
		for(int i = 0; i < RACK_SIZE[0]; i++) {
			for(int j = 0; j < RACK_SIZE[1]; j++) {
				if(items[i][j] == null) {
					notFull = true;
					break;
				}
				else if(items[i][j].getVolume() * quantity[i][j] < MAX_GRID_VOLUME) {
					notFull = true;
					break;
				}
			}
		}
		status = notFull;
	}
	
	@Override
	public String toString() {
		
		return String.format("  __________________________________  \n"
						+ " | ID         | %-19c | \n"
						+ " |------------|---------------------| \n"
						+ " | Category   | %-19s | \n"
						+ " |------------|---------------------| \n"
						+ " | Status     | %-19s | \n"
						+ " |------------|---------------------| \n"
						+ " | Items(Qty) | %-19d | \n"
						+ " |____________|_____________________| \n",RACK_ID,category,getStatusString(),getTotalItemsQty(), items);
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Item) {
			if(this.ID == ((Item)obj).getId())
				return true;
		}
		return false;
	}
	


}
