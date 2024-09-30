package department;

public class Admin extends Department{
	
	private static int adminCount;

	//Constants
	private final static String TITLE = "Admin";
	//--- ADMIN_MENU is the Head Menu of each Sub Menus ---
	private final static String[] ADMIN_MENU = { "Employee",
												 "Branch",
												 "Supplier",
												 "Report",
												 "Items"
												};
	//Constructor
	public Admin() {
		super(TITLE);
	}
	
	
	//Getter
	public static int getAdminCount() {return adminCount;}
	
	
	@Override
	public String[] getMenu() {
		
		String[] menu = new String[ADMIN_MENU.length + BASIC_MENU.length];
		for(int i = 0; i < ADMIN_MENU.length; i++) {
			menu[i] = ADMIN_MENU[i];
		}
		for(int i = ADMIN_MENU.length; i < ADMIN_MENU.length + BASIC_MENU.length; i++) {
			menu[i] = BASIC_MENU[i-ADMIN_MENU.length];
		}
		return menu;
	}
	
	@Override
	public void remDepEmp() {
		adminCount--;
	}

	@Override
	public void addDepEmp() {
		adminCount++;
	}
	
	public String toString() {
		return super.toString() + 
				" (Employee Count : " + adminCount + ")";
	}
	public boolean equals(Object obj) {
		if(obj instanceof Admin)
			return true;
		return false;
	}

}
