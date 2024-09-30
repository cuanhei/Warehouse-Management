package warehouse;

import company.Storeroom;
import item.Item;

public class Warehouse {
	
	//Attributes
	private final int ID; //Used for equals
	private final char WAREHOUSE_ID;
	private String desc;
	private boolean status;
	private Floor[] floors;
	private int floorCount;
	
	private static int wHouseCount;
	
	//Constants
	public static final int MAX_FLOOR = 10;
	
	
	
	
	//Constructors
	public Warehouse() {
		this(null,false,0,null,0);
	}
	public Warehouse(String desc, boolean status, int wHouseCount) { //The wHouseCount is the Branch Warehouse count
		
		ID = ++Warehouse.wHouseCount;
		WAREHOUSE_ID = generateWarehouseId(wHouseCount);
		this.desc = desc;
		this.status = status;
		this.floors = new Floor[MAX_FLOOR];
		this.floorCount = 0;
	}
	public Warehouse(String desc, boolean status, int wHouseCount, Floor[] floors, int floorCount) {//The wHouseCount is the Branch Warehouse count
		
		ID = ++Warehouse.wHouseCount;
		WAREHOUSE_ID = generateWarehouseId(wHouseCount);
		this.desc = desc;
		this.status = status;
		this.floors = floors;
		this.floorCount = floorCount;
	}


	
	
	
	//Getter & Setter
	public int getId() {return ID;}
	public char getWarehouseId() {return WAREHOUSE_ID;}
	public void setDesc(String desc) {this.desc = desc;}
	public void setStatus(boolean status) {this.status = status;}
	public String getDesc() {return desc;}
	public boolean getStatus() {return status;}
	public Floor[] getFloors() {return floors;}
	public int getFloorCount() {return floorCount;}
	
	
	
	
	  ///////////////////////////////////////////
	 ////////////// Static Methods /////////////
	///////////////////////////////////////////
	
	/*
	 * This method will generate a Character ID for the warehouse
	 * It is generated based on the actual parameter size
	 * If the actual parameter size is 2 then it will generate the
	 * character by adding (int)A and 2 which is ASCII value of 'C'
	 */
	public static char generateWarehouseId(int wHouseCount) {
		return (char)((int)'A'+ wHouseCount);
	}
	
	
		
	  ///////////////////////////////////////////
	 ///////////// Instance Methods ////////////
	///////////////////////////////////////////
	
	public void clear(Storeroom storeroom) {
		for(Floor floor : floors) {
			if(floor != null) {
				for(Rack rack : floor.getRacks()) {
					if(rack!=null)
						rack.storeItemsToSR(storeroom);
				}
			}
		}
	}

	/*
	 * This method show the details of the warehouse
	 * It will show each Floor in the warehouse including the Floor details
	 * Which will be the Level of floor, Number of Rack, Each floor Status, Total Quantity of Items in the Floor 
	 */
	public void showDetails() {
		
		System.out.printf("  ___________________________________________  \n"
						+ " |  Floor  |  Rack  |  Status  |  Item(Qty)  | \n"
						+ " |---------|--------|----------|-------------| \n");
		for(Floor floor : floors) {
			if(floor==null)break;
			System.out.printf(" | Level %-2d|  %-4d  |  %-7s |  %-9d  |\n",
					floor.getLevel(), floor.getRackCount(), floor.getStatus(), floor.getAllItemsQty());
		}
		System.out.println(" |_________|________|__________|_____________|\n");
	}
	
	/*
	 * This method is used to add a floor into the warehouse
	 * But it will add an empty floor which the floor contains zero rack
	 */
	public boolean addFloor() {
		if(floorCount < MAX_FLOOR) {
			floors[floorCount] = new Floor(floorCount + 1);
			floorCount++;
			return true;
		}
		return false;
	}
	
	/*
	 * This method is used to add a specific floor
	 * by just passing the actual parameter to the parameter list
	 */
	public boolean addFloor(Floor floor) {
		if(floorCount < MAX_FLOOR) {
			floors[floorCount] = floor;
			floorCount++;
			return true;
		}
		return false;
	}
	
	
	/*
	 * This method is used to find the specific floor in the Warehouse
	 * with the Level of floor passed
	 */
	public Floor findLevel(int level) {
		
		for(Floor floor : floors) {
			if(floor==null)
				break;
			if(floor.getLevel() == level )
				return floor;
		}
		return null;
	}
	
	/*
	 * this method will based on the item passed in and calculate in
	 * the warehouse how many does the items exists
	 */
	public int getItemCount(Item item) {
		int sum = 0;
		for(int i = 0 ; i < floorCount; i++) {
			sum += floors[i].getItemCount(item);
		}
		return sum;
	}

	@Override
	public String toString() {
		
		return "Warehouse ID: " + WAREHOUSE_ID + 
				"\nDescription : " + desc + 
				"\nStatus: " + status + 
				"\nFloor Count: " + floorCount + "\n";
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Warehouse) {
			Warehouse wHouse = (Warehouse)obj;
			if(wHouse.ID == this.ID) 
				return true;
		}
		return false;
	}
}
