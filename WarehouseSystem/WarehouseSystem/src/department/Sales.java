package department;

public class Sales extends Department{
	
	private static int salesCount;
	
	//Constants
	private final static String TITLE = "Sales";
	//--- NORMAL_MENU is the Head Menu of each Sub Menus ---
	private final static String[] SALES_MENU = { "Check Inventoy Level",
												 "Create Order",
												 "Pay Order"
												 };
	
	
	//Constructor
	public Sales() {
		super(TITLE);
	}

	//Getter
	public int getSalesCount() {return salesCount;}
	
	
	
	
	@Override
	public void remDepEmp() {
		salesCount--;
	}
	@Override
	public void addDepEmp() {
		salesCount++;
	}
	@Override
	public String[] getMenu() {
		String[] menu = new String[SALES_MENU.length + BASIC_MENU.length];
		for(int i = 0; i < SALES_MENU.length; i++) {
			menu[i] = SALES_MENU[i];
		}
		for(int i = SALES_MENU.length; i < SALES_MENU.length + BASIC_MENU.length; i++) {
			menu[i] = BASIC_MENU[i-SALES_MENU.length];
		}
		return menu;
	}
	@Override
	public String toString() {
		return super.toString() + 
				" (Employee Count : " + salesCount + ")";
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Sales)
			return true;
		return false;
	}

}
