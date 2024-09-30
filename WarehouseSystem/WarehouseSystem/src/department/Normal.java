package department;

public class Normal extends Department{
	
	private static int normalCount;
	
	//Constants
	private final static String TITLE = "Normal";	
	//--- NORMAL_MENU is the Head Menu of each Sub Menus ---
	private final static String[] NORMAL_MENU = {"Pack Order",
												 "Move Item From Storeroom",
												 "Manage Rack Item"};
	
	
	//Constructor
	public Normal() {
		super(TITLE);
	}

	//Getter
	public int getNormalCount() {return normalCount;}
	
	@Override
	public void remDepEmp() {
		normalCount--;
	}
	@Override
	public void addDepEmp() {
		normalCount++;
	}
	
	@Override
	public String[] getMenu() {
		String[] menu = new String[NORMAL_MENU.length + BASIC_MENU.length];
		for(int i = 0; i < NORMAL_MENU.length; i++) {
			menu[i] = NORMAL_MENU[i];
		}
		for(int i = NORMAL_MENU.length; i < NORMAL_MENU.length + BASIC_MENU.length; i++) {
			menu[i] = BASIC_MENU[i-NORMAL_MENU.length];
		}
		return menu;
	}
	@Override
	public String toString() {
		return super.toString() + 
				" (Employee Count : " + normalCount + ")";
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Normal)
			return true;
		return false;
	}
}
