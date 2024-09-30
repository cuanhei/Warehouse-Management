package warehouse;

import item.Item;

public class Floor {

	// Attributes
	private final int ID;
	private final int LEVEL;
	private boolean status;
	private Rack[] racks;
	private int rackCount;

	private static int floorCount;

	// Constants
	public static final int MAX_RACK = 10;

	// Constructors
	public Floor() {
		this(0, false, null, 0);
	}
	public Floor(int level) {
		this.ID = ++floorCount;
		this.LEVEL = level;
		this.racks = new Rack[MAX_RACK];
		this.rackCount = 0; // Improve readability
		this.status = true;
	}
	public Floor(int level, boolean status, Rack[] racks, int rackCount) {
		this.ID = ++floorCount;
		this.LEVEL = level;
		this.racks = racks;
		this.rackCount = rackCount;
		this.status = status;
	}

	
	
	// Getters & Setters
	public int getId() {return ID;}
	public int getLevel() {return LEVEL;}
	public boolean getStatus() {return status;}
	public void setStatus(boolean status) {this.status = status;}
	public Rack[] getRacks() {return racks;}
	public int getRackCount() {return rackCount;}

	
	

	  ///////////////////////////////////////////
	 ///////////// Instance Methods ////////////
	///////////////////////////////////////////

	public void addRack(Rack rack) {

		racks[rackCount] = rack;
		rackCount++;
	}

	public int getItemCount(Item item) {
		int sum = 0;
		for(int i = 0; i < rackCount; i++) {
			sum += racks[i].getItemCount(item);
		}
		return sum;
	}

	public boolean showRacks() {

		if (rackCount == 0) {
			System.out.println("---------------------\n" + "    No Rack Found    \n" + "---------------------\n");
			return false;
		} else {
			System.out.print("  ___________________________________________________  \n"
					+ " | ID |      Category      |  Items(Qty)  |  Status  | \n"
					+ " |----|--------------------|--------------|----------| \n");
			for (Rack rack : racks) {
				if (rack == null)
					break;
				System.out.printf(" | %-2c |   %-14s   |  %-10d  |  %-6s  | \n", rack.getRackId(), rack.getCategory(),
						rack.getTotalItemsQty(), rack.getStatusString());
			}
			System.out.println(" |____|____________________|______________|__________|\n");
			return true;
		}
	}

	
	public void showingRackId(char rackId) {
		
//		 _________________________
//		| Rack ID | A | B | C | D |
//		|_________|___|___|___|___|
		
		System.out.print(" __________");
		for (int i = 0; i < rackCount; i++) 
            System.out.print("___");
        
		for(int i = 0; i < rackCount-1; i++) 
			System.out.print("_");
		System.out.println();
		System.out.print("| Rack ID |");
		for(int i = 0; i < rackCount; i++) 
			System.out.printf(" %s%c%s |",(rackId==racks[i].getRackId())?"\u001B[32m":"\u001B[0m",racks[i].getRackId(),"\u001B[0m");
		System.out.println();
		System.out.print("|_________|");
		for (int i = 0; i < rackCount; i++) 
            System.out.print("___|");
		System.out.println("\n");
	}

	public Rack findRack(char rackId) {
		rackId = Character.toUpperCase(rackId);
		for(Rack rack : racks) {
			if(rack == null) break;
			if(rack.getRackId() == rackId)
				return rack;
		}
		return null;
	}
	
	public int getAllItemsQty() {

		int totalItemInFloor = 0;

		for (int i = 0; i < rackCount; i++) {
			totalItemInFloor += racks[i].getTotalItemsQty();
		}
		return totalItemInFloor;
	}


	@Override
	public String toString() {

		return "Level: " + LEVEL +
				"\nStatus: " + status + 
				"\nRack Count: " + rackCount + "\n";
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof Floor) {
			Floor floor = (Floor) obj;
			if (this.getId() == floor.ID)
				return true;
		}
		return false;
	}
	
}
