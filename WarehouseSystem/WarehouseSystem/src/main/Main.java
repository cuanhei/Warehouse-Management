/* 
 * ===============================
 * DFT Y2S1 G7 (2023 June Intakes)
 * ===============================
 * This is a warehouse system that made for a wholesaler company [A-Plus Sdn Bhd] company
 * It is used to manage all the items in the branch, each branch contains few amount of warehouse, 
 * and also each warehouse contains few amount of floor, each floor contains few amount of racks.
 * 
 * This system is used to manage the items easier in this kind of large company.
 * It contains feature such as recording amount of items in a specific grid, 
 * on the specific rack, on the specific floor, in a specific warehouse, at a
 * specific branch.
 * 
 * Employees are required to register and wait for the administrator to approve the registration request.
 * After approved and logged in, the system will based on the account's department and show the specific
 * department menu. Each department play different roles in the system.
 * 
 * This system allowed the administrator to generate a few kind of reports too, so it can 
 * help the administrator to make a better decision.
 */

package main;

import java.util.*;

import company.*;
import department.*;
import document.*;
import employee.*;
import item.*;
import warehouse.*;
import order.*;
import payment.*;
import report.*;

public class Main {
	
	//--- Constants --------------------------------------------------------------------------------------------------
	private static final String COMPANY_NAME = "[A-Plus Sdn Bhd]";
	private static final int MAX_BRANCH = 10;
	private static final int MAX_EMP = MAX_BRANCH*Branch.EMPLOYEE_COUNT_PER_BRANCH;
	private static final int MAX_SUP = Item.MAX_ITEM_TYPE;
	private static final int MAX_CUSTOMER = 10000;
	private static final int MAX_PACKING = 1000;
	private static final Department[] DEPARTMENTS = {new Admin(), new Normal(), new Sales()};
	
	//Colors
	public static final String BG_WHITE = "\u001B[47m";
	public static final String BG_BLACK = "\u001B[40m";
	public static final String BLACK = "\u001B[30m";
	public static final String WHITE = "\u001B[37m";
	public static final String YELLOW = "\u001B[33m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String BLUE = "\u001B[36m";
	public static final String RESET = "\u001B[0m";
	//-----------------------------------------------------------------------------------------------------------------
	
	
	//--- Database ----------------------------------------------------------------------------------------------------
	private static Employee[] employees = new Employee[MAX_EMP];
	private static Branch[] branches = new Branch[MAX_BRANCH];
	private static Item[] items = new Item[Item.MAX_ITEM_TYPE];
	private static Document[] documents = new Document[1000];
	private static ItemSupplier[] suppliers = new ItemSupplier[MAX_SUP];
	private static Customer[] customers = new Customer[MAX_CUSTOMER];
	private static Packing[] packings = new Packing[MAX_PACKING];
	private static int docCount = 0;
	private static int packCount = 0;
	//-----------------------------------------------------------------------------------------------------------------
	
	
	//--- Common used in this application -----------------------------------------------------------------------------
	private static final Scanner scanner = new Scanner(System.in); // This is the scanner that use in the whole application
	private static Employee loggedEmp ;
	//-----------------------------------------------------------------------------------------------------------------	
	
	
	
	public static void main(String[] args) {
		
		loadDB();
		mainMenu();
	}
	
	
	
	private static void loadDB() {
		
		Employee emp1 = new Employee("LIM CUAN HEI","cuanhei@gmail.com","01121451187","123",
				new Admin(), 20000, 1);
		Employee emp2 = new Employee("Test LOL","jy@gmail.com","01232132111","123",
				new Sales(), 20000, 1);
		Employee emp3 = new Employee("TEST A","a@gmail.com","01121451187","123",
				new Normal(), 20000, 1);
		Employee emp4 = new Employee("TEST B","b@gmail.com","01232132111","123",
				new Normal(), 20000, 1);
		Employee emp5 = new Employee("TestC","c@gmail.com","01121451187","123",
				new Admin(), 20000, 1);
		Employee emp6 = new Employee("test D","d@gmail.com","01232132111","123",
				new Sales(), 20000, 1);
		
		employees[0] = emp1;
		employees[1] = emp2;
		employees[2] = emp3;
		employees[3] = emp4;
		employees[4] = emp5;
		employees[5] = emp6;
		
		//private static final String[] CATEGORIES = {"Pencil", "Pen", "Glue", "Penknife", "Ruler", "Other"}; 
		Item item1 = new Item("Shaker X", "Pencil", 7, 10);
		Item item2 = new Item("PenMate Pro", "Pencil", 3.5, 20);
		Item item3 = new Item("DX", "Pencil", 7, 20);
		Item item4 = new Item("G2", "Pen", 4, 20);
		Item item5 = new Item("Gel Pen", "Pen", 2, 20);
		Item item6 = new Item("Quill Ever", "Pen", 10, 20);
		Item item7 = new Item("Sticky Glue", "Glue", 3, 0.8);
		Item item8 = new Item("UHU", "Glue", 5, 20);
		Item item9 = new Item("Super Glue", "Glue", 7, 15);
		Item item10 = new Item("Sharp 99", "Penknife", 0.8, 1);
		Item item11 = new Item("Extreme Sas", "Penknife", 1.5, 1);
		Item item12 = new Item("SharpEdge", "Penknife", 1, 1);
		Item item13 = new Item("Maped", "Ruler", 1, 1);
		Item item14 = new Item("Straight R", "Ruler", 2, 1);
		Item item15 = new Item("Dang Ruler", "Ruler", 1.5, 1);
		Item item16 = new Item("SharpnerXD", "Other", 5, 10);
		Item item17 = new Item("Notebook Pro", "Other", 8, 40);
		
		items[0] = item1;
		items[1] = item2;
		items[2] = item3;
		items[3] = item4;
		items[4] = item5;
		items[5] = item6;
		items[6] = item7;
		items[7] = item8;
		items[8] = item9;
		items[9] = item10;
		items[10] = item11;
		items[11] = item12;
		items[12] = item13;
		items[13] = item14;
		items[14] = item15;
		items[15] = item16;
		items[16] = item17;
		
		Customer cust1 = new Customer("Peter Lim", "pro@lim.com","01233646465", 0.0);
		Customer cust2 = new Customer("Dchei", "dchei@lim.com"  ,"01238334484", 0.0);
		Customer cust3 = new Customer("Mr X", "Xmr@gmail.com"   ,"01263488449", 0.0);
		Customer cust4 = new Customer("Lengzai", "lengzai@gmail.com"   ,"0160192834", 0.0);
		Customer cust5 = new Customer("Cuan", "Cuan@gmail.com"   ,"01267448449", 0.0);
		
		customers[0] = cust1;
		customers[1] = cust2;
		customers[2] = cust3;
		customers[3] = cust4;
		customers[4] = cust5;
		
		ItemSupplier sup1 = new ItemSupplier("Pilot", "pilot@pl.com","0124445678", "5-Block X Georg");
		ItemSupplier sup2 = new ItemSupplier("Zebra", "zebra@gmail.com","0124345598", "10-Lot 3 Kedah");
		ItemSupplier sup3 = new ItemSupplier("Stabilo", "stabilo@yahoo.com","0114435608", "33C jalan Aplus");
		
		sup1.addItem(item1, 5);
		sup1.addItem(item2, 2);
		sup1.addItem(item3, 6);
		sup1.addItem(item8,0.4);
		
		sup2.addItem(item1, 4);
		sup2.addItem(item3, 5);
		sup2.addItem(item4, 2);
		sup2.addItem(item5, 3.5);
		
		sup3.addItem(item5, 3.2);
		sup3.addItem(item6, 5);
		sup3.addItem(item7, 0.5);
		sup3.addItem(item8, 0.5);
		
		suppliers[0] = sup1;
		suppliers[1] = sup2;
		suppliers[2] = sup3;
		
		Item[][] branch1WhouseAFloor1RackAItems = new Item[Rack.RACK_SIZE[0]][Rack.RACK_SIZE[1]];
		int[][] branch1WhouseAFloor1RackAQty = new int[Rack.RACK_SIZE[0]][Rack.RACK_SIZE[1]];
		branch1WhouseAFloor1RackAItems[0][0] = item1;
		branch1WhouseAFloor1RackAItems[2][3] = item2;
		branch1WhouseAFloor1RackAItems[4][4] = item3;
		branch1WhouseAFloor1RackAQty[0][0] = 20;
		branch1WhouseAFloor1RackAQty[2][3] = 10;
		branch1WhouseAFloor1RackAQty[4][4] = 8;
		
		Item[][] branch1WhouseAFloor1RackBItems = new Item[Rack.RACK_SIZE[0]][Rack.RACK_SIZE[1]];
		int[][] branch1WhouseAFloor1RackBQty = new int[Rack.RACK_SIZE[0]][Rack.RACK_SIZE[1]];
		branch1WhouseAFloor1RackBItems[0][3] = item1;
		branch1WhouseAFloor1RackBItems[1][2] = item2;
		branch1WhouseAFloor1RackBItems[3][5] = item3;
		branch1WhouseAFloor1RackBQty[0][3] = 10;
		branch1WhouseAFloor1RackBQty[1][2] = 6;
		branch1WhouseAFloor1RackBQty[3][5] = 8;
		
		Item[][] branch1WhouseAFloor1RackCItems = new Item[Rack.RACK_SIZE[0]][Rack.RACK_SIZE[1]];
		int[][] branch1WhouseAFloor1RackCQty = new int[Rack.RACK_SIZE[0]][Rack.RACK_SIZE[1]];
		branch1WhouseAFloor1RackCItems[0][4] = item4;
		branch1WhouseAFloor1RackCItems[1][1] = item4;
		branch1WhouseAFloor1RackCItems[2][2] = item5;
		branch1WhouseAFloor1RackCQty[0][4] = 10;
		branch1WhouseAFloor1RackCQty[1][1] = 6;
		branch1WhouseAFloor1RackCQty[2][2] = 8;
		
		Rack branch1WhouseAFloor1RackA = new Rack(0, "Pencil", true,branch1WhouseAFloor1RackAItems,branch1WhouseAFloor1RackAQty);
		Rack branch1WhouseAFloor1RackB = new Rack(1, "Pencil", true,branch1WhouseAFloor1RackBItems,branch1WhouseAFloor1RackBQty);
		Rack branch1WhouseAFloor1RackC = new Rack(2, "Pen", true,branch1WhouseAFloor1RackCItems,branch1WhouseAFloor1RackCQty);
		
		Branch branch1 = new Branch("A-Plus", "Aplus@gmail.com", "1231231231", "Lot-1 PP Georg", // Super()
				10000000, true);
			//Branch 1 Warehouse
			Warehouse branch1WhouseA = new Warehouse("Store Big Item", true, 0);
			Warehouse branch1WhouseB = new Warehouse("Store Small Item", true, 1);
				//warehouse A
				Floor branch1WhouseAFloor1 = new Floor(1, true, new Rack[Floor.MAX_RACK], 0);
					branch1WhouseAFloor1.addRack(branch1WhouseAFloor1RackA);
					branch1WhouseAFloor1.addRack(branch1WhouseAFloor1RackB);
					branch1WhouseAFloor1.addRack(branch1WhouseAFloor1RackC);
					branch1WhouseAFloor1.addRack(new Rack(3, "Penknife", true));
				Floor branch1WhouseAFloor2 = new Floor(2, true, new Rack[Floor.MAX_RACK], 0);
					branch1WhouseAFloor2.addRack(new Rack(0, "Glue", true));
					branch1WhouseAFloor2.addRack(new Rack(1, "Pen", true));
				Floor branch1WhouseAFloor3 = new Floor(3, true, new Rack[Floor.MAX_RACK], 0);
					branch1WhouseAFloor3.addRack(new Rack(0, "Other", true));
					branch1WhouseAFloor3.addRack(new Rack(1, "Pen", true));
				//warehouse B
				Floor branch1WhouseBFloor1 = new Floor(1, true, new Rack[Floor.MAX_RACK], 0);
					branch1WhouseBFloor1.addRack(new Rack(0, "Glue", true));
					branch1WhouseBFloor1.addRack(new Rack(1, "Pen", true));
				Floor branch1WhouseBFloor2 = new Floor(2, true, new Rack[Floor.MAX_RACK], 0);
					branch1WhouseBFloor2.addRack(new Rack(0, "Glue", true));
					branch1WhouseBFloor2.addRack(new Rack(1, "Pen", true));
					
		branch1.addEmp(emp1);
		branch1.addEmp(emp2);
		branch1.addEmp(emp3);
		
		branch1WhouseA.addFloor(branch1WhouseAFloor1);
		branch1WhouseA.addFloor(branch1WhouseAFloor2);
		branch1WhouseA.addFloor(branch1WhouseAFloor3);
		
		branch1WhouseB.addFloor(branch1WhouseBFloor1);
		branch1WhouseB.addFloor(branch1WhouseBFloor2);
		
		branch1.addWarehouse(branch1WhouseA);
		branch1.addWarehouse(branch1WhouseB);
					
		Branch branch2 = new Branch("B-Plus", "Bplus@gmail.com", "2323232323", "Lot-2 PP Georg", // Super()
				20000000, true);
		//Branch 2 Warehouse
		Warehouse branch2WhouseA = new Warehouse("Store Big Item", true, 0);
		Warehouse branch2WhouseB = new Warehouse("Store Danger Item", true, 1);
			//warehouse A
			Floor branch2WhouseAFloor1 = new Floor(1, true, new Rack[Floor.MAX_RACK], 0);
				branch2WhouseAFloor1.addRack(new Rack(0, "Pencil", true));
				branch2WhouseAFloor1.addRack(new Rack(1, "Glue", true));
				branch2WhouseAFloor1.addRack(new Rack(2, "Ruler", true));
				branch2WhouseAFloor1.addRack(new Rack(3, "Penknife", true));
			Floor branch2WhouseAFloor2 = new Floor(2, true, new Rack[Floor.MAX_RACK], 0);
				branch2WhouseAFloor2.addRack(new Rack(0, "Glue", true));
				branch2WhouseAFloor2.addRack(new Rack(1, "Pen", true));
			Floor branch2WhouseAFloor3 = new Floor(3, true, new Rack[Floor.MAX_RACK], 0);
				branch2WhouseAFloor3.addRack(new Rack(0, "Other", true));
				branch2WhouseAFloor3.addRack(new Rack(1, "Pen", true));
			//warehouse B
			Floor branch2WhouseBFloor1 = new Floor(1, true, new Rack[Floor.MAX_RACK], 0);
				branch2WhouseBFloor1.addRack(new Rack(0, "Glue", true));
				branch2WhouseBFloor1.addRack(new Rack(1, "Pen", true));
			Floor branch2WhouseBFloor2 = new Floor(2, true, new Rack[Floor.MAX_RACK], 0);
				branch2WhouseBFloor2.addRack(new Rack(0, "Glue", true));
				branch2WhouseBFloor2.addRack(new Rack(1, "Pen", true));
				
		branch2.addEmp(emp4);
		branch2.addEmp(emp5);
		branch2.addEmp(emp6);
		
		branch2WhouseA.addFloor(branch2WhouseAFloor1);
		branch2WhouseA.addFloor(branch2WhouseAFloor2);
		branch2WhouseA.addFloor(branch2WhouseAFloor3);
		
		branch2WhouseB.addFloor(branch2WhouseBFloor1);
		branch2WhouseB.addFloor(branch2WhouseBFloor2);
		
		branch2.addWarehouse(branch2WhouseA);
		branch2.addWarehouse(branch2WhouseB);
		
		sup1.purchaseItem(branch1, item1, 101);
		sup1.purchaseItem(branch1, item2, 140);
		sup1.purchaseItem(branch1, item3, 403);
		sup2.purchaseItem(branch1, item5, 2500);
		sup3.purchaseItem(branch1, item7, 102);
		sup3.purchaseItem(branch1, item8, 210);
		
		sup1.purchaseItem(branch2, item1, 140);
		sup1.purchaseItem(branch2, item2, 110);
		sup1.purchaseItem(branch2, item3, 450);
		sup2.purchaseItem(branch2, item5, 2400);
		sup3.purchaseItem(branch2, item7, 105);
		sup3.purchaseItem(branch2, item8, 220);
		
		branches[0] = branch1;
		branches[1] = branch2;
		
		Item[] order1Items = new Item[Item.MAX_ITEM_TYPE];
		Item[] order2Items = new Item[Item.MAX_ITEM_TYPE];
		Item[] order3Items = new Item[Item.MAX_ITEM_TYPE];
		Item[] order4Items = new Item[Item.MAX_ITEM_TYPE];
		Item[] order5Items = new Item[Item.MAX_ITEM_TYPE];
		
		int[] order1Qty = new int[Item.MAX_ITEM_TYPE];
		int[] order2Qty = new int[Item.MAX_ITEM_TYPE];
		int[] order3Qty = new int[Item.MAX_ITEM_TYPE];
		int[] order4Qty = new int[Item.MAX_ITEM_TYPE];
		int[] order5Qty = new int[Item.MAX_ITEM_TYPE];

		
		order1Items[0] = item1;
		order1Items[1] = item2;
		order1Items[2] = item4;
		order1Qty[0] = 30;
		order1Qty[1] = 15;
		order1Qty[2] = 3;
		
		order2Items[0] = item4;
		order2Items[1] = item5;
		order2Items[2] = item6;
		order2Items[3] = item7;
		order2Qty[0] = 110;
		order2Qty[1] = 105;
		order2Qty[2] = 300;
		order2Qty[3] = 100;
		
		order3Items[0] = item7;
		order3Items[1] = item8;
		order3Items[2] = item9;
		order3Qty[0] = 100;
		order3Qty[1] = 250;
		order3Qty[2] = 400;
		
		order4Items[0] = item10;
		order4Items[1] = item11;
		order4Items[2] = item12;
		order4Qty[0] = 500;
		order4Qty[1] = 300;
		order4Qty[2] = 400;
		
		order5Items[0] = item13;
		order5Items[1] = item14;
		order5Items[2] = item15;
		order5Qty[0] = 400;
		order5Qty[1] = 200;
		order5Qty[2] = 900;
		
		
		Order order1 = new Order(order1Items,order1Qty,false,branch1,3);
		Order order2 = new Order(order2Items,order2Qty,false,branch2,4);
		Order order3 = new Order(order3Items,order3Qty,false,branch2,3);
		Order order4 = new Order(order4Items,order4Qty,false,branch2,3);
		Order order5 = new Order(order5Items,order5Qty,false,branch2,3);
	
		Invoice inv1 = new Invoice(order1,cust1);
		Invoice inv2 = new Invoice(order2,cust2);
		Invoice inv3 = new Invoice(order3,cust3);
		Invoice inv4 = new Invoice(order4,cust4);
		Invoice inv5 = new Invoice(order5,cust5);
		
		documents[docCount++] = inv1;
		documents[docCount++] = inv2;
		documents[docCount++] = inv3;
		documents[docCount++] = inv4;
		documents[docCount++] = inv5;
	}
	
	// Main MENU
	public static void mainMenu() {
		
		int selection;
		do {
			selection = -1;
			
			clear("==== Main Menu ====");
			System.out.println("Please select :\n\n"
							 + "[0] Exit\n"
							 + "[1] Register\n"
							 + "[2] Log In\n"
							 + "[3] Help\n");
			System.out.print(">> ");

			try {
				selection = Integer.parseInt(Main.scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("[Invalid Input, please enter a number]");
				Main.sleep();
				continue; 
			}
			
			switch (selection) {
				case 0:	break;// Exit
				case 1: //Register
					register();
					break;
				case 2:	//Login
					login();
					break;
				case 3: 
					help();
					pressEnter();
					break;
				default:
					System.out.println("[Invalid Input]");
					sleep();
			}
		} while (selection != 0);

		scanner.close();
		clear();
		System.out.println("[Thank you for using our system!]\n\n\n\n\n");
	}
	
	private static void register() {
			
		Employee emp = new Employee();
		Branch empBranch = Branch.searchEmp(branches, emp);
		for (int i = 0; i < 4; i++) { //Register only required 4 details
			clear("=== Register an Employee account ===\n");
			System.out.println("[Note] Once the Branch, Department, Salary has set by the admin only you can log in.\n");
			emp.showDetails(Branch.searchEmp(branches, emp));
			
			switch(i) {
				case 0:
					System.out.print("Please input Name : ");
					break;
				case 1:
					System.out.print("Please input Email : ");
					break;
				case 2:
					System.out.print("Please input Contact : ");
					break;
				case 3:
					System.out.print("Please input Password : ");
					break;
				default: 
					System.out.println("Something went Wrong!");
					break;
			}
			String input = scanner.nextLine();
			if(input.equals("0")) {
				Employee.cancelRegisterEmp();
				return;
			}
			if(!emp.inputDetails(i,input,employees,DEPARTMENTS,branches)) 
				i--;
		}

		clear("=== Register an Employee account ===");
		emp.showDetails(empBranch);
		System.out.println("Are you sure you wanted to create this account ? [Y/N]");
		System.out.print(">> ");
		char yesNo = Main.scanner.next().charAt(0);
		Main.scanner.nextLine();
		
		if (yesNo == 'Y' || yesNo == 'y') {
			Main.clear("=== Register an Employee account ===");
			emp.showDetails(empBranch);
			employees[Employee.getCreatedEmp()-1] = emp;
			System.out.println("[The account is now pending for approve]\n");
			System.out.println("[Note] Please wait for the management to approve your registration request.\n\n");
			pressEnter();
			
		} else {
			System.out.println("[The account is not CREATED]");
			Employee.cancelRegisterEmp();
			pressEnter();
		}
	}
	
	private static void login() {
		do {
			clear("======= Log In =======");
			System.out.print("ID / Email : ");
			String idEmail = scanner.nextLine();
			
			if(idEmail.equals("0")) return;
			
			System.out.print("Password : ");
			String password = scanner.nextLine();
			if(password.equals("0")) return;
			
			if(idEmail.contains("@")) 
				loggedEmp = Employee.validLogIn(employees, idEmail, password);
			else {
				try {
					int id = Integer.parseInt(idEmail);
					loggedEmp = Employee.validLogIn(employees, id, password);
				}
				catch(Exception ex) {
					continue;
				}
			}
			
			if(loggedEmp == null) {
				System.out.println(RED + "[Invalid ID / Email or password]\n" + RESET);
				pressEnter();
			}else {
				if(!loggedEmp.checkStatus())
					pressEnter();
				else if(!Branch.searchEmp(branches, loggedEmp).getStatus()) {
					System.out.println("[Sorry, the current Branch you are in has been "+RED+"locked"+RESET+" please contact the admin]");
					pressEnter();
				}
				else {
					if(loggedEmp.getDepartment() instanceof Admin) 
						adminMenu();
					else if(loggedEmp.getDepartment() instanceof Sales)
						salesMenu();
					else
						normalMenu();
				}
			}
		}while(loggedEmp == null);
	}
	
	private static void salesMenu() {
		
		final String[] MENU = (loggedEmp.getDepartment()).getMenu();
		int select = -1;
		do {
			clear("======= Normal Menu =======");
			System.out.println("ID: " + loggedEmp.getId() + "\n");
			System.out.println("Please select : \n");
			for(int i = 0; i < MENU.length; i++) {
				System.out.printf("[%d] %s\n", i+1, MENU[i]);
			}
			System.out.print("\n>> ");
			
			try {
				select = Integer.parseInt(scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("[Invalid Input, please enter a number]");
				Main.sleep();
				continue;
			}
			
			switch(select) {
				case 1: //Check Inventory Level
					clear("============================== Current Branch Inventory Level ===========================");
					Branch.searchEmp(branches, loggedEmp).showInventoryLevel(items);
					pressEnter();
					break;
				case 2: //Create Order
					Invoice inv = createOrder();
					if(inv !=null) {
						documents[docCount++] = inv;
						payInvoiceMenu(inv);
					}
					break;
				case 3: //Manage Rack Item
					Invoice unPaidInv = selectUnPaidInvoice();
					if(unPaidInv != null) {
						payInvoiceMenu(unPaidInv);
					}
					break;
				case 4:
					profile();
					break;
				case 5:
					logOut();
					break;
				default:
					invalidInput();
			}
			
		}while(select != 5); // 5 is log out
	}



	private static void help() {
		clear("[HELP]");
		System.out.println("This system is used to manage items across different branches. Based on the account you log in with, "
			    + "the system will allow you to manage the quantity of items in the branch associated with that account.\n"
			    + "After registration, you won't be able to log in immediately—you must wait for the admin to approve your request. "
			    + "Once approved, you can manage the branch your account is linked to, choose a department, and update your salary.\n"
			    + "Some accounts listed are automatically generated by the system since no external database is used in this application.\n");

	}
	
	private static void normalMenu() {
		
		final String[] MENU = (loggedEmp.getDepartment()).getMenu();
		int select = -1;
		do {
			clear("======= Normal Menu =======");
			System.out.println("ID: " + loggedEmp.getId() + "\n");
			System.out.println("Please select : \n");
			for(int i = 0; i < MENU.length; i++) {
				System.out.printf("[%d] %s\n", i+1, MENU[i]);
			}
			System.out.print("\n>> ");
			
			try {
				select = Integer.parseInt(scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("[Invalid Input, please enter a number]");
				Main.sleep();
				continue;
			}
			
			switch(select) {
				case 1: //Pack Order
					packOrderMenu();
					break;
				case 2: //Move Item From Storeroom
					moveItemFromStore();
					break;
				case 3: //Manage Rack Item
					manageRackItem();
					break;
				case 4:
					profile();
					break;
				case 5:
					logOut();
					break;
				default:
					invalidInput();
			}
			
		}while(select != 5); // 5 is log out
	}
	

	
	private static void packOrderMenu() {
		int select = -1;
		do {
			clear("============= Pack Order =============");
			System.out.println("[0] Back\n"
							  +"[1] Pack Unpack Order\n"
							  +"[2] Continue Pack Unfinish Packing\n");
			System.out.print(">> ");
			try {
				select = Integer.parseInt(scanner.nextLine());
			}catch(NumberFormatException ex) {
				System.out.println("[Invalid input]");
				sleep();
				continue;
			}
			
			switch(select) {
			case 0:
				break;
			case 1:
				Packing packing;
				Order unpackOrder = selectUnpackOrder();
				if(unpackOrder == null)
					break;
				unpackOrder.setPacked(true);
				packing = new Packing(unpackOrder);
				packings[packCount++] = packing;
				startPacking(packing);
				break;
			case 2:
				packing = selectUnfinishPacking();
				if(packing != null)
					startPacking(packing);
				break;
			default:
				invalidInput();
			}
		}while(select!= 0);
	}



	private static Packing selectUnfinishPacking() {
		while(true) {
			clear("============ Pack Unfinish Packing ============");
			Packing.showUnfinishPacking(packings);
			System.out.print("Please select the Packing ID: ");
			int packId;
			try {
				packId = Integer.parseInt(scanner.nextLine());
			}catch(NumberFormatException ex) {
				invalidInput();
				continue;
			}
			if(packId == 0) break;
			Packing packing = Packing.findUnfinish(packings,packId,Branch.searchEmp(branches, loggedEmp));
			if(packing != null)
				return packing;
			System.out.println("[Packing ID not found]");
			sleep();
		}
		return null;
	}



	private static void startPacking(Packing packing) {
		while(true) {
			clear("=================== Packing Order ===================");
			System.out.println(packing.toString());
			System.out.print("Please input the Item Index to pack: ");
			int itemIndex;
			try {
				itemIndex = Integer.parseInt(scanner.nextLine());
			}catch(NumberFormatException ex) {
				System.out.println("[Invalid Item Index]");
				sleep();
				continue;
			}
			
			if(itemIndex == 0) 
				break;
			else if (itemIndex > packing.getOrderToPack().getItemCount() || itemIndex < 0) {
				System.out.println("[Item Index not found]");
				sleep();
				continue;
			}
			Item item = packing.getOrderToPack().getItems()[itemIndex-1];
			loggedEmp.setHoldingItem(item);
			packItemToPacking(packing);
			if(packing.checkFullyPacked()) {
				clear("=================== Packing Order ===================");
				System.out.println(packing.toString());
				System.out.println("[You had "+GREEN+"done "+RESET+"packing this order]");
				pressEnter();
				break;
			}
		}
		
	}



	private static void packItemToPacking(Packing packing) {

		while(true) {
			boolean donePack = false;
			Warehouse wHouse = selectWarehouse();
			if(wHouse == null) break;
			
			while(true) {
				Floor floor = selectLevel(wHouse);
				if(floor == null) break;
				
				char rackId = ' ';

				while(true) {
					clear("=============================== Level " + floor.getLevel() + " =================================");
					floor.showingRackId(rackId);
					
					 try {
		                	System.out.print("Please select the Rack ID: ");
		                	rackId = Character.toUpperCase(scanner.nextLine().charAt(0));
		                }catch(Exception ex) {
		                	rackId = ' ';
		                	continue;
		                }
		                
	                if (rackId == '0') break;

	                Rack rack = floor.findRack(rackId);
	                if (rack == null) {
	                    System.out.println("[Rack ID not found]");
	                    continue;
	                }
	                clear("=============================== Level " + floor.getLevel() + " =================================");
	        		floor.showingRackId(rackId);
	        		System.out.println("[Rack Category: "+rack.getCategory()+"]");
	        		rack.showGrid(loggedEmp.getHoldingItem());
	        		
	        		if(rack.getStatus() == false) {
	        			System.out.println("[The rack is FULL please choose other rack]");
	        			sleep();
	        			continue;
	        		}
	        		if(rack.getCategory().equals(loggedEmp.getHoldingItem().getCategory())) {
		        		if(!confirm("Confirm "+BLUE+"PACK"+RESET+" Item from this rack? [Y/N]: ")) {
		                	rackId = ' ';
		                	continue;
		                }
	        		}
	        		else {
	        			System.out.println("[Current rack has "+RED+"different"+RESET+" Categroy that the Item you wanted to pack]");
	        			pressEnter();
	        			rackId = ' ';
	                	continue;
	        		}
	        		packItemFromRackGrid(floor,rack,packing);	
	        		rackId = ' ';
	        		donePack = packing.checkDonepack(loggedEmp.getHoldingItem());
	        		
	        		if(donePack) {
	        			clear();
	        			System.out.println("[Due to you had packed the Item(ID: "+YELLOW+loggedEmp.getHoldingItem().getId()+RESET+") you can now choose another Item to pack]");
	        			pressEnter();
	        			loggedEmp.setHoldingItem(null);
	        			break;
	        		}
				}
				if(donePack)
					break;
			}
			if(donePack) break;
		}
	}



	private static void packItemFromRackGrid(Floor floor, Rack rack,Packing packing) {
		while(true) {
			clear("=============================== Level " + floor.getLevel() + " =================================");
			System.out.println("Required to pack: ");
			packing.showPackingProgress(loggedEmp.getHoldingItem());
			System.out.println("[Rack Category: "+rack.getCategory()+"]");
			rack.showGrid(loggedEmp.getHoldingItem());

			System.out.println("[Example of inputing Row and Col]");
        	System.out.println("      Col\n"
        					 + "      ________________\n"
        					 + " Row | 0,0 | 0,1 | ...\n"
        					 + "     |=====|=====|====\n"
        					 + "     | 1,0 | 1,1 | ...\n"
        					 + "     ...\n");
        	System.out.print("Please input the grid that wanted to pack [Row, Col]: ");
        	String rowCol = scanner.nextLine();
        	if(rowCol.equals("0")) break;
        	int row = 0;
        	int col = 0;
        	int quantity;
        	try {
        		String[] rowColSplit = rowCol.split(",");
        		if(rowColSplit.length == 2) {
        			row = Integer.parseInt(rowColSplit[0]);
        			col = Integer.parseInt(rowColSplit[1]);
        		}
        	}catch(Exception ex) {
        		System.out.println("[Please input the grid according to the format [Row,Col]]");
        		sleep();
        		continue;
        	}
        	
        	if(row>Rack.getMaxRow()-1 || col>Rack.getMaxCol()-1 || row < 0 || col < 0) {
        		System.out.println(RED+"[The row or col is out of range, please try again!]"+RESET);
        		sleep();
        		continue;
        	}
        	if(rack.getItem(row, col) == null) {
        		System.out.println("[This grid is empty, please choose another]");
        		sleep();
        		continue;
        	}
        	if(!rack.getItem(row, col).equals(loggedEmp.getHoldingItem())) {
        		System.out.println("[Sorry, you are not required to pack this Item]");
        		sleep();
        		continue;
        	}
        	
        	try {
        		System.out.print("Please input the quantity to pack: ");
        		quantity = Integer.parseInt(scanner.nextLine());
        	}catch(NumberFormatException ex) {
        		System.out.println("[Please input a valid quantity]");
        		sleep();
        		continue;
        	}
        	

        	int canTakeAmount = rack.takeItem(row, col, loggedEmp.getHoldingItem(), quantity);
        	int overItem = packing.addToPack(loggedEmp.getHoldingItem(), canTakeAmount);
        	if(overItem > 0) {
        		rack.addItem(loggedEmp.getHoldingItem(), overItem, row, col);
        		System.out.println("[Due to the amount you packed into is "+RED+"MORE"+RESET+" than the actual amount that the order needed]\n");
        		System.out.println("[System will "+YELLOW+"put it back to the previous grid"+RESET+" for those extra packed Items]\n");
        		pressEnter();
        		continue;
        	}
        	rack.checkRackFull();
		}
	}



	private static Order selectUnpackOrder() {
		Order order = null;
		while(true) {
			clear("================== Select An Order To Pack ==================");
			if(!Order.showUnpackOrders(documents, Branch.searchEmp(branches, loggedEmp),packings)) {
				pressEnter();
				return null;
			}
			int orderId = -1;
			try {
				System.out.print("Please input the Order ID: ");
				orderId = Integer.parseInt(scanner.nextLine());
			}catch(NumberFormatException ex) {
				System.out.println("[Please input a valid Order ID]");
				sleep();
				continue;
			}
			if(orderId == 0)
				break;
			order = Order.findUnpackOrderId(documents, orderId, Branch.searchEmp(branches, loggedEmp));
			
			if(order == null) {
				System.out.println("[Please select the Order ID from the List above]");
				sleep();
				continue;
			}
			break;
				
		}
		return order;
	}



	private static void manageRackItem() {
		boolean picking = true;
		while(true) {
			Warehouse wHouse = selectWarehouse();
			if(wHouse == null) break;
			
			while(true) {
				Floor floor = selectLevel(wHouse);
				if(floor == null) break;
				
				char rackId = ' '; // Default Rack [A]
				//Picking
				while(picking) {
					clear("=============================== Level " + floor.getLevel() + " =================================");
					floor.showingRackId(rackId);
					
					 try {
		                	System.out.print("Please select the Rack ID: ");
		                	rackId = Character.toUpperCase(scanner.nextLine().charAt(0));
		                }catch(Exception ex) {
		                	rackId = ' ';
		                	continue;
		                }
		                
	                if (rackId == '0') break;

	                Rack rack = floor.findRack(rackId);
	                if (rack == null) {
	                    System.out.println("[Rack ID not found]");
	                    continue;
	                }
	                clear("=============================== Level " + floor.getLevel() + " =================================");
	        		floor.showingRackId(rackId);
	        		System.out.println("[Rack Category: "+rack.getCategory()+"]");
	        		rack.showGrid();
	        		
	        		if(!confirm("Confirm "+BLUE+"PICK"+RESET+" Item from this rack? [Y/N]: ")) {
	                	rackId = ' ';
	                	continue;
	                }
	        		boolean tookItem = getItemFromRackGrid(floor,rack);
	        		if(tookItem) {
	        			clear("==================================== Picked Item ====================================");
	        			System.out.println("[You took "+YELLOW+loggedEmp.getHoldingQty()+RESET+" of Item(ID: "+YELLOW+loggedEmp.getHoldingItem().getId()+RESET+")]\n");
	        			System.out.println("[You are now required to choose where to Store the Item(s)]\n");
	        			picking = false;
	        			rackId = ' ';
	        			pressEnter();
	        		}
	        		
				}
				//Placing
				while(!picking) {
					clear("=============================== Level " + floor.getLevel() + " =================================");
					floor.showingRackId(rackId);
					
					 try {
		                	System.out.print("Please select the Rack ID: ");
		                	rackId = Character.toUpperCase(scanner.nextLine().charAt(0));
		                }catch(Exception ex) {
		                	rackId = ' ';
		                	continue;
		                }
		                
	                if (rackId == '0') break;

	                Rack rack = floor.findRack(rackId);
	                if (rack == null) {
	                    System.out.println("[Rack ID not found]");
	                    rackId = ' ';
	                    continue;
	                }
	                clear("=============================== Level " + floor.getLevel() + " =================================");
	        		floor.showingRackId(rackId);
	        		System.out.println("[Rack Category: "+rack.getCategory()+"]");
	        		rack.showGrid(loggedEmp.getHoldingItem());
	        		
	        		if(!rack.getCategory().equals(loggedEmp.getHoldingItem().getCategory())) {
	        			System.out.println("[Due to this rack has "+RED+"NOT"+RESET+" same category, you can't place the item here]\n");
	        			pressEnter();
	        			rackId = ' ';
	        			continue;
	        		}
	        		if(!confirm("Confirm "+BLUE+"PLACE"+RESET+" Item into this rack? [Y/N]: ")) {
	                	rackId = ' ';
	                	continue;
	                }
	        		putItemInRackGrid(floor,rack);
	        		
	        		if(loggedEmp.getHoldingQty() == 0) {
	        			clear();
	        			picking = true;
	        			System.out.println("[You had "+GREEN+"DONE"+RESET+" managed the "+YELLOW+"previous"+RESET+"]\n");
	        			System.out.println("[Press ENTER to manage more Item]\n");
	        			pressEnter();
	        			picking = true;
	        			break;
	        		}else {
	        			clear();
	        			System.out.println("Currently Holding: ");
	        			loggedEmp.showHoldingItemAndQty();
	        			System.out.println("\n[Due to you had "+RED+"NOT"+RESET+" finished placing those Items]\n");
	        			if(confirm("Do you want to place them into Storeroom ? [Y/N]: ")) {
	        				Storeroom sr = Branch.searchEmp(branches, loggedEmp).getStoreroom();
	        				sr.addItem(loggedEmp.getHoldingItem(), loggedEmp.getHoldingQty());
	        				System.out.println("[Successfully placed "+YELLOW+loggedEmp.getHoldingQty()+RESET+" amount of Item(ID: "+YELLOW+loggedEmp.getHoldingItem().getId()+RESET+") into storeroom]\n");
	        				loggedEmp.setHoldingItem(null);
	        				loggedEmp.setHoldingQty(0);
	        				pressEnter();
	        				break;
	        			}
	        			rackId = ' ';
	        			continue;
	        		}
				}
			}
		}
		if(loggedEmp.getHoldingQty() != 0) {
			clear();
			System.out.println("\n[Due to you had "+RED+"NOT"+RESET+" finished placing those Items, the remaining "+YELLOW+loggedEmp.getHoldingQty()+RESET+" amount of Item(ID: "+YELLOW+loggedEmp.getHoldingItem().getId()+RESET+") will automatic store into the Storeroom]\n");
			Storeroom sr = Branch.searchEmp(branches, loggedEmp).getStoreroom();
			sr.addItem(loggedEmp.getHoldingItem(), loggedEmp.getHoldingQty());
			loggedEmp.setHoldingItem(null);
			loggedEmp.setHoldingQty(0);
			pressEnter();
		}
	}






	private static void moveItemFromStore() {
		
		Storeroom storeroom = Branch.searchEmp(branches, loggedEmp).getStoreroom();
		while(true) {
			clear("============================= Moving Item From Storeroom =============================");
			if(!storeroom.showDetails()) {
				pressEnter();
				break;
			}
			System.out.print("Please select the Item ID: ");
			int itemId;
			int quantity;
			try {
				itemId = Integer.parseInt(scanner.nextLine());
			}catch(NumberFormatException ex) {
				System.out.println("[Please input a valid Item ID]");
				sleep();
				continue;
			}
			if(itemId == 0) break;
			Item item = Item.findId(items, itemId);
			if(item == null) {
				System.out.println("[Item (ID: "+itemId+") doesn't found]");
				sleep();
				continue;
			}
			if(!storeroom.containsItem(item)) {
				System.out.println("[Please select the Item ID that the storerrom contains with]");
				System.out.println("[The selected Item ID has no stocks in the Storerrom now]");
				pressEnter();
				continue;
			}
			System.out.print("Please input the quantity that wanted to take: ");
			try {
				quantity = Integer.parseInt(scanner.nextLine());
			}catch(NumberFormatException ex) {
				System.out.println("[Please input a valid quantity]");
				sleep();
				continue;
			}
			if(quantity == 0) break;
			if(!storeroom.removeItem(item, quantity)) {
				pressEnter();
				continue;
			}
			loggedEmp.setHoldingItem(item);
			loggedEmp.setHoldingQty(quantity);
			
			placeItem();

			if(loggedEmp.getHoldingQty()!=0) {
				clear();
				storeroom.addItem(loggedEmp.getHoldingItem(), loggedEmp.getHoldingQty());
				System.out.println("[Due to you had "+RED+"NOT finish"+RESET+" placed the Item(ID: "+loggedEmp.getHoldingItem().getId()+") quantity("+YELLOW+loggedEmp.getHoldingQty()+RESET+") has placed back to the Storeroom]\n");
				pressEnter();
				loggedEmp.setHoldingItem(null);
				loggedEmp.setHoldingQty(0);
			}
		}
	}
	
	private static void placeItem() {
	    
	    while (true) {
	        Warehouse wHouse = selectWarehouse();
	        if (wHouse == null) break;

	        while (true) {
	            Floor floor = selectLevel(wHouse);
	            if (floor == null) break;
	            
	            selectPutItemRack(floor);
	        }
	    }
	}

	
	private static void selectPutItemRack(Floor floor) {
		char rackId = ' ';
        while (true) {
        	clear("=============================== Level " + floor.getLevel() + " =================================");
            floor.showingRackId(rackId);
            	
            try {
            	System.out.print("Please select the Rack ID: ");
            	rackId = Character.toUpperCase(scanner.nextLine().charAt(0));
            }catch(Exception ex) {
            	rackId = ' ';
            	continue;
            }
            
            if (rackId == '0') break;

            Rack rack = floor.findRack(rackId);
            if (rack == null) {
                System.out.println("[Rack ID not found]");
                sleep();
                continue;
            }
            clear("=============================== Level " + floor.getLevel() + " =================================");
    		floor.showingRackId(rackId);
            boolean validRackCategory = validateRackCategory(rack,loggedEmp.getHoldingItem());
            if(!validRackCategory) {
            	rackId = ' ';
            	continue;
            }
            
            if(!confirm("Confirm "+BLUE+"STORING"+RESET+" Item into this rack? [Y/N]: ")) {
            	rackId = ' ';
            	continue;
            }
            
            putItemInRackGrid(floor,rack);
            rackId = ' ';
        }
		
	}

	

	private static void putItemInRackGrid(Floor floor, Rack rack) {
		while(true) {
			clear("=============================== Level " + floor.getLevel() + " =================================");
			System.out.println("Currently Holding :");
			loggedEmp.showHoldingItemAndQty();
			System.out.println("[Rack Category: "+rack.getCategory()+"]");
			rack.showGrid(loggedEmp.getHoldingItem());
			
			if(loggedEmp.getHoldingQty() == 0) {
				System.out.println(GREEN+"[You had placed all the Item(s), please return back to pick other Item]\n"+RESET);
				pressEnter();
				break;
			}
			System.out.println("[Example of inputing Row and Col]");
        	System.out.println("      Col\n"
        					 + "      ________________\n"
        					 + " Row | 0,0 | 0,1 | ...\n"
        					 + "     |=====|=====|====\n"
        					 + "     | 1,0 | 1,1 | ...\n"
        					 + "     ...\n");
        	//System.out.println("[System will automatic place the "+YELLOW+"MAXIMUM "+RESET+"volume of item that the grid can hold]\n");
        	System.out.print("Please input the placing grid [Row, Col]: ");
        	String rowCol = scanner.nextLine();
        	if(rowCol.equals("0")) break;
        	int row = 0;
        	int col = 0;
        	try {
        		String[] rowColSplit = rowCol.split(",");
        		if(rowColSplit.length == 2) {
        			row = Integer.parseInt(rowColSplit[0]);
        			col = Integer.parseInt(rowColSplit[1]);
        		}
        	}catch(Exception ex) {
        		System.out.println("Please input the grid according to the format [Row,Col]");
        		sleep();
        		continue;
        	}
        	
        	if(row>Rack.getMaxRow()-1 || col>Rack.getMaxCol()-1 || row < 0 || col < 0) {
        		System.out.println(RED+"[The row or col is out of range, please try again!]"+RESET);
        		sleep();
        		continue;
        	}
        	int quantity;
        	try {
        		System.out.print("Please input quantity to place into the Grid: ");
        		quantity = Integer.parseInt(scanner.nextLine());
        	}catch(NumberFormatException ex) {
        		System.out.println("[Invalid Quantity]");
        		sleep();
        		continue;
        	}
        	if(rack.getItem(row, col)!=null) {
	        	if(!rack.getItem(row, col).getCategory().equals(loggedEmp.getHoldingItem().getCategory())) {
	        		System.out.println("[You can't place the different Item into one Grid]");
	        		sleep();
	        		continue;
	        	}
        	}
        	if(quantity > loggedEmp.getHoldingQty()) {
        		System.out.println("\n[Due to placing amount "+RED+"MORE"+RESET+" than you holding, system will just put the "+YELLOW+"MAXIMUM "+RESET+"amount of you hold]\n");
        		pressEnter();
        		quantity = loggedEmp.getHoldingQty();
        	}
        	int remain = rack.addItem(loggedEmp.getHoldingItem(), quantity, row, col);
        	if(remain == loggedEmp.getHoldingQty() || remain == -1) 
        		continue;
        	else if(remain != 0) {
        		int placed = quantity-remain;
        		remain = loggedEmp.getHoldingQty()-quantity+remain;
        		System.out.println("\n[Placed "+GREEN+placed+RESET+" amount of Item(ID: "+loggedEmp.getHoldingItem().getId()+") into ("+row+","+col+") remaining "+YELLOW+remain+RESET+" of it holding due to grid has stored max volume]\n");
        		loggedEmp.setHoldingQty(remain);
        		pressEnter();
        	}else {
        		System.out.println("[Successfully placed "+YELLOW+quantity+RESET+" of Item(ID: "+YELLOW+loggedEmp.getHoldingItem().getId()+RESET+")]\n");
        		int newHoldQty = loggedEmp.getHoldingQty() - quantity;
        		loggedEmp.setHoldingQty(newHoldQty);
        		pressEnter();
        	}
        	if(loggedEmp.getHoldingQty() == 0) {
        		clear("=============================== Level " + floor.getLevel() + " =================================");
    			System.out.println("Currently Holding :");
    			loggedEmp.setHoldingQty(0);
	    		loggedEmp.setHoldingItem(null);
    			loggedEmp.showHoldingItemAndQty();
    			System.out.println("[Rack Category: "+rack.getCategory()+"]");
    			rack.showGrid();
	        	System.out.println("\n[You had placed "+GREEN+"ALL"+RESET+" of the Holding Item into ("+row+","+col+")]\n");
	    		
	    		pressEnter();
	    		rack.checkRackFull();
	    		break;
        	}
		}
	}
	
	private static boolean getItemFromRackGrid(Floor floor, Rack rack) {
		boolean took = false;
		while(true) {
			clear("=============================== Level " + floor.getLevel() + " =================================");
			System.out.println("[Rack Category: "+rack.getCategory()+"]");
			if(loggedEmp.getHoldingItem() != null)
				rack.showGrid(loggedEmp.getHoldingItem());
			else
				rack.showGrid();
			
			System.out.println("[Example of inputing Row and Col]");
        	System.out.println("      Col\n"
        					 + "      ________________\n"
        					 + " Row | 0,0 | 0,1 | ...\n"
        					 + "     |=====|=====|====\n"
        					 + "     | 1,0 | 1,1 | ...\n"
        					 + "     ...\n");
        	System.out.print("Please input the grid that wanted to manage [Row, Col]: ");
        	String rowCol = scanner.nextLine();
        	if(rowCol.equals("0")) break;
        	int row = 0;
        	int col = 0;
        	int quantity;
        	try {
        		String[] rowColSplit = rowCol.split(",");
        		if(rowColSplit.length == 2) {
        			row = Integer.parseInt(rowColSplit[0]);
        			col = Integer.parseInt(rowColSplit[1]);
        		}
        	}catch(Exception ex) {
        		System.out.println("[Please input the grid according to the format [Row,Col]]");
        		sleep();
        		continue;
        	}
        	
        	if(row>Rack.getMaxRow()-1 || col>Rack.getMaxCol()-1 || row < 0 || col < 0) {
        		System.out.println(RED+"[The row or col is out of range, please try again!]"+RESET);
        		sleep();
        		continue;
        	}
        	
        	try {
        		System.out.print("Please input the quantity to take: ");
        		quantity = Integer.parseInt(scanner.nextLine());
        	}catch(NumberFormatException ex) {
        		System.out.println("[Please input a valid quantity]");
        		sleep();
        		continue;
        	}
        	
        	loggedEmp.setHoldingItem(rack.getItem(row, col));
        	if(loggedEmp.getHoldingItem() == null) {
        		System.out.println("[Current grid doesn't contain any Item]");
        		sleep();
        		continue;
        	}
        	int empHoldingQty = rack.takeItem(row, col, loggedEmp.getHoldingItem(), quantity);
        	loggedEmp.setHoldingQty(empHoldingQty);
        	took = true;
        	
        	if(loggedEmp.getHoldingQty() < quantity) {
        		System.out.println("[You had picked "+YELLOW+empHoldingQty+RESET+" of the item due to the grid "+RED+"only"+RESET+" contains that amount ]");
        		pressEnter();
        	}
        	break;
		}
		return took;
	}



	private static boolean confirm(String prompt) {
		char yesNo;
		try {
			System.out.print(prompt);
			yesNo = scanner.nextLine().charAt(0);
			if(yesNo == 'Y' || yesNo == 'y')
				return true;
		}catch(Exception ex) {
			return false;
		}
		return false;
	}



	private static boolean validateRackCategory(Rack rack, Item holdingItem) {
		
		System.out.println("[Rack Category: "+rack.getCategory()+"]");
		rack.showGrid();
		
		if(holdingItem == null) {
			System.out.println("[You are currently Holding nothing!]\n");
			pressEnter();
			return false;
		}
		else if(!rack.getCategory().equals(holdingItem.getCategory())) {
			System.out.println("[This Rack have different category, please choose other Rack]\n");
			pressEnter();
			return false;
		}

		return true;
	}



	private static Warehouse selectWarehouse() {
		Branch currentBranch = Branch.searchEmp(branches, loggedEmp);
		while(true) {
			clear("===================== Warehouses Under Current Banch =====================");
			currentBranch.showWarehouses();
			System.out.print("Please select the Warehouse ID "+GREEN+"[0 to EXIT]"+RESET+": ");
			char wHouseId =Character.toUpperCase(scanner.nextLine().charAt(0));
			if(wHouseId == '0') break;		
			
			Warehouse wHouse = currentBranch.findId(wHouseId);
			if(wHouse == null) {
				System.out.println("[Does not found Warehouse(ID: "+wHouseId+")]");
				pressEnter();
				continue;
			}else {
				return wHouse;
			}
		}
		return null;
	}
	
	private static Floor selectLevel(Warehouse wHouse) {
		while(true) {
			clear("============================= Warehouse " + wHouse.getWarehouseId() + " ==============================");
	        wHouse.showDetails();
	        
	        int level;
	        System.out.print("Please input Floor Level "+GREEN+"[0 to EXIT]"+RESET+":");
	        try {
	        	level = Integer.parseInt(scanner.nextLine());
	        }catch(NumberFormatException ex) {
	        	System.out.println("[Please input a number Level]");
	        	sleep();
	        	continue;
	        }
	        if (level == 0) break;
	
	        Floor floor = wHouse.findLevel(level);
	        if (floor == null) {
	            System.out.println("[Floor level not found, please try again]");
	            Main.sleep();
	            continue;
	        }
	        else if(floor.getRackCount() == 0) {
	        	System.out.println("[Can't place item in this floor due to no Rack available]");
	        	pressEnter();
	        	continue;
	        }else 
	        	return floor;
		}
		return null;
	}
	
	private static Invoice selectUnPaidInvoice() {
		
		Invoice unPaidInv = null;
		while(true) {
			clear("========== Unpaid Invoices ==========");
			Invoice.showUnpaidInvoice(documents);
			System.out.print("Select Invoice ID: ");
			int invoiceId;
			try {
				invoiceId = Integer.parseInt(scanner.nextLine());
				if(invoiceId == 0)
					break;
			}catch(NumberFormatException ex) {
				System.out.println(RED+"[Invalid Invoice ID]");
				sleep();
				continue;
			}
			unPaidInv = Invoice.findId(documents, invoiceId);
			if(unPaidInv == null) {
				System.out.println("[Invoice ID not found]");
				sleep();
				continue;
			}
			else if(unPaidInv.getStatus() == false) {
				System.out.println("[Please select the Unpaid Invoice]");
				sleep();
				continue;
			}
			else
				break;
		}
		return unPaidInv;
	}
	
	private static void payInvoiceMenu(Invoice inv) {
		
		double amount = inv.getPrice();
		
		while(true) {
			clear("============================= Pay Invoice =============================");
			System.out.println(inv.toString());
			System.out.println("Please select a Payment Method: \n");
			System.out.println("[0] Pay Later\n"
							 + "[1] Cash\n"
							 + "[2] Card\n");
			System.out.print(">> ");
			int select;
			try {
				select = Integer.parseInt(scanner.nextLine());
			}catch(NumberFormatException ex) {
				invalidInput();
				continue;
			}
			if(select == 0) break;
			System.out.println("======================================================================");
			if(select == 1) {
				System.out.println(GREEN+ "[pay with Cash]" + RESET);
				System.out.print("Please input amount paid : RM ");
				try {
					double paidAmount = Double.parseDouble(scanner.nextLine());
					if(paidAmount >= amount) {
						Payment cash = new Cash(amount,paidAmount);
						Receipt receipt = new Receipt(cash,inv);
						documents[docCount++] = receipt;
						inv.paid(); //If paid then set become false so the invoice not valid anymore
						Customer cust = inv.getCustomer();
						
						cust.addSpendAmount(amount);
						inv.getOrder().getBranch().addBalance(amount);
						System.out.println("[Invoice has paid "+GREEN+"SUCCESSFULLY"+RESET+" and "+YELLOW+"RM "+((Cash)cash).getRefundAmount()+RESET+" has refunded to you]");
						pressEnter();
						clear();
						System.out.println(receipt.toString());
						pressEnter();
						break;
					}else {
						System.out.println("\n[You are required to pay RM "+RED+amount+RESET+"]\n");
						System.out.println("[RM "+YELLOW +paidAmount+RESET+" has refunded to you]\n");
						pressEnter();
						continue;
					}	
				}catch(NumberFormatException ex) {
					System.out.println("[Please input a valid cash amount]");
					sleep();
					continue;
				}
			}else if(select == 2) {
				System.out.println(GREEN+ "[pay with Card]" + RESET);
				System.out.print("Please input Card Number [16-digit]: ");

				String cardNo = scanner.nextLine();
				if(cardNo.length() != 16) {
					System.out.println("[Please input a 16-digit card number]");
					sleep();
					continue;
				}
				
				boolean allDigit = true;
				for(int i = 0;  allDigit && i < cardNo.length(); i++) {
					if(!Character.isDigit(cardNo.charAt(i))) 
						allDigit = false;
				}
				
				if(allDigit) {
					System.out.print("Please input your CVV number [3-digit]: ");
					String cvv = scanner.nextLine();
					
					if(cvv.length() != 3) {
						System.out.println("[Only 3 Digit can input to CVV number]");
						sleep();
						continue;
					}
					
					try {
						Integer.parseInt(cvv);
					}catch(NumberFormatException ex) {
						System.out.println("[Please input only Digits for your CVV number]");
						sleep();
						continue;
					}
					
					Payment card = new Card(amount, cardNo, cvv);
					Receipt receipt = new Receipt(card,inv);
					documents[docCount++] = receipt;
					
					inv.paid(); //If paid then set become false so the invoice not valid anymore
					Customer cust = inv.getCustomer();
					
					cust.addSpendAmount(amount);
					inv.getOrder().getBranch().addBalance(amount);
					System.out.println("[Invoice has paid "+GREEN+"SUCCESSFULLY"+RESET+" and "+YELLOW+"RM "+amount+RESET+" has deducted from CardNo: "+cardNo+"]");
					pressEnter();
					clear();
					System.out.println(receipt.toString());
					pressEnter();
					break;
				}else {
					System.out.println("[You can only input Digits in your card number]");
					sleep();
					continue;
				}
				
			}else
				invalidInput();
		}
	}
	
	private static Invoice createOrder() {
	    Order order = new Order(Branch.searchEmp(branches, loggedEmp));
	    
	    // Add items to the order
	    addItemsToOrder(order);
	    
	    // If no items were added, cancel the order
	    if(order.getItemCount() == 0) {
	        Order.cancelOrder();
	        return null;
	    }
	    
	    // Confirm and proceed with the order
	    if(confirmOrder(order)) {
	        Customer customer = selectCustomerForOrder(order);
	        if (customer != null) {
	            if(finalizeOrder(order, customer)) {
	            	Invoice inv = new Invoice(order,customer);
	            	return inv;
	            }
	        }
	    }
	    
	    Order.cancelOrder();
	    return null;
	}

	// Method to add items to the order
	private static void addItemsToOrder(Order order) {
		
	    while(order.getItemCount() < Order.getMaxOrderItem()) {
	        clear("===================== Creating Order =====================");
	        order.showDetails();
	        Item.showAllItems(items);
	        
	        int itemId;
	        try{
	        	System.out.print("\nSelect Item ID to add to current order "+GREEN+"[0 To Proceed]"+RESET+": ");
	        	itemId = Integer.parseInt(scanner.nextLine());
	        }catch(NumberFormatException ex) {
	        	invalidInput();
	        	sleep();
	        	continue;
	        }	
	        if(itemId == 0) break;
	        
	        Item itemToAdd = Item.findId(items,itemId);
	        if(itemToAdd == null) continue;
	        
	        int quantityToAdd;
	        try{
	        	System.out.print("Quantity to add: ");
	        	quantityToAdd = Integer.parseInt(scanner.nextLine());
	        }catch(NumberFormatException ex) {
	        	invalidInput();
	        	continue;
	        }
	        if(quantityToAdd > 0) {
	            order.addItem(itemToAdd, quantityToAdd);
	        }
	    }
	}

	// Method to confirm order
	private static boolean confirmOrder(Order order) {
	    clear("===================== Creating Order =====================");
	    order.showDetails();
	    return (confirm("Are you sure you want to create this order? [Y/N]: "));
	}

	// Method to select a customer for the order
	private static Customer selectCustomerForOrder(Order order) {
		Customer cust = null;
	    while (cust == null) {
	        clear("===================== Creating Order =====================");
	        order.showDetails();
	        System.out.println("Please input the customer for this Order:\n");
	        System.out.println("[0] Cancel Order\n[1] Recorded Customer\n[2] New Customer\n");
	        
	        int select;
	        System.out.print(">> ");
	        try {
	        	select = Integer.parseInt(scanner.nextLine());
	        }catch(NumberFormatException ex) {
	        	invalidInput();
	        	continue;
	        }
	        if (select == 0) break;  // Cancel order
	        if (select == 1) cust = selectRecordedCustomer();
	        if (select == 2) cust = createCustomer();
	    }
		return cust;
	}

	// Method to select a recorded customer
	private static Customer selectRecordedCustomer() {
	    clear("========== Select a Customer ==========");
	    if (Customer.showCustomers(customers)) {
	        int custId = -1;
	        System.out.print("Please input the Customer ID: ");
	        try {
	        	custId = Integer.parseInt(scanner.nextLine());
	        }catch(NumberFormatException ex) {
	        	invalidInput();
	        }
	        Customer customer = Customer.findId(customers, custId);
	        if (customer != null) return customer;
	        System.out.println("[Customer (ID: "+ custId +") not found]");
	    }
	    pressEnter();
	    return null;
	}

	// Method to finalize the order and create an invoice
	private static boolean finalizeOrder(Order order, Customer customer) {
		clear("===================== Creating Order =====================");
	    System.out.println(YELLOW + " Customer: " + customer.getName() + " (ID:" + customer.getId() + ")" + RESET);
	    order.showDetails();

	    if (confirm("Confirm this order? [Y/N]: ")) {
	        System.out.println(GREEN+"[The order has been Successfully Created!]"+RESET);
	        pressEnter();
	        return true;
	    }
	    return false;
	}
	
	private static Customer createCustomer() {
		Customer customer = new Customer();
		for(int i = 0; i < 3; i++) {
			clear("========== Creating New Customer ==========");
			System.out.print(customer);
			
			switch(i) {
				case 0:
					System.out.print("Please input Customer Name: ");
					break;
				case 1:
					System.out.print("Please input Customer Email: ");
					break;
				case 2:
					System.out.print("Please input Customer Contact: ");
					break;
				default:
					System.out.println("Something went Wrong!");
			}
			String input = scanner.nextLine();
			if(input.equals("0")) {
				customer = null;
				Customer.cancelCreateCust();
				return customer;
			}
			if(!customer.inputDetails(i, input))
				i--;
		}
		clear("========== Creating New Customer ==========");
		System.out.print(customer);
		
		if(!confirm("Confirm the Customer Details? [Y/N]: ")) {
			customer = null;
			Customer.cancelCreateCust();
		}else {
			customers[Customer.getCreatedCust() -1] = customer;
		}
		return customer;
	}
	// ADMIN MENU
	
	
	private static void adminMenu() {
		
		final String[] MENU = (loggedEmp.getDepartment()).getMenu();
		int select = -1;
		do {
			clear("======= Admin Menu =======");
			System.out.println("ID: " + loggedEmp.getId() + "\n");
			System.out.println("Please select : \n");
			for(int i = 0; i < MENU.length; i++) {
				System.out.printf("[%d] %s\n", i+1, MENU[i]);
			}
			System.out.print("\n>> ");
			
			try {
				select = Integer.parseInt(scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("[Invalid Input, please enter a number]");
				Main.sleep();
				continue;
			}
			
			switch(select) {
				case 1:
					empMenu();
					break;
				case 2:
					branchMenu();
					break;
				case 3:
					supplierMenu();
					break;
				case 4:
					reportMenu();
					break;
				case 5:
					itemMenu();
					break;
				case 6:
					profile();
					break;
				case 7:
					logOut();
					break;
				default:
					invalidInput();
			}
			
		}while(select != 7); // 7 is log out
	}



	private static void reportMenu() {

		int select = -1;
		do {
			Main.clear();
			System.out.println("=================== Report ====================\n");
			System.out.println("Please select :\n");
			System.out.println("[0] Back\n"
							 + "[1] Item Report\n"
							 + "[2] Customer Report\n");
			System.out.print(">> ");
			
			try {
				select = Integer.parseInt(Main.scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("[Invalid Input, please enter a number]");
				Main.sleep();
				continue;
			}
			
			switch(select) {
			case 0: break;
			case 1:
				clear();
				Report itemReport = new ItemReport(items,documents);
				itemReport.drawReport();
				System.out.println("\n");
				pressEnter();
				break;
			case 2:
				clear();
				Report custReport = new CustomerReport(customers);
				custReport.drawReport();
				pressEnter();
				break;
			default:
					Main.invalidInput();
			}
		}while(select != 0);
	}



	private static void supplierMenu() {
		
		int select = -1;
		do {
			Main.clear();
			System.out.println("=================== Supplier ====================\n");
			System.out.println("Please select :\n");
			System.out.println("[0] Back\n"
							 + "[1] Add New Supplier\n"
							 + "[2] Edit Supplier\n"
							 + "[3] Purchase Stocks\n");
			System.out.print(">> ");
			
			try {
				select = Integer.parseInt(Main.scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("[Invalid Input, please enter a number]");
				Main.sleep();
				continue;
			}
			
			switch(select) {
			case 0: break;
			case 1:
				addSupplier();
				break;
			case 2:
				Main.clear("================= Edit Supplier =================");
				ItemSupplier supToEdit = selectSup();
				if(supToEdit!=null) 
					editSup(supToEdit);
				break;
			case 3:
				Main.clear("===================== Purchase Stock From Supplier =====================\n");
				ItemSupplier supToPurchase = selectSup();
				if(supToPurchase!=null) {
					do {
						Main.clear("===================== Purchase Stock From Supplier =====================\n");
						supToPurchase.showDetails();
						int itemIndex = findSellingItemIndex(supToPurchase);
						if(itemIndex != -1) {
							System.out.print("Please input the quantity to purchase: ");
							int quantity = Integer.parseInt(Main.scanner.nextLine().trim());
							if(quantity >= 0) {
								Item item = supToPurchase.getSellingItems()[itemIndex];
								Branch currentBranch = Branch.searchEmp(branches,loggedEmp);
								if(supToPurchase.purchaseItem(currentBranch, item, quantity))
									System.out.println(GREEN + "\n[Successfully purchased "+quantity+" amount of "+item.getName()+"(Item ID: "+item.getId()+")]\n" + RESET);
							}else 
								System.out.println(RED+ "\n[The Item was not purchased due to the amount is ZERO or less than]\n" + RESET);
							Main.pressEnter();
						}else
							break;
					}while(true);
				}
				break;
			default:
					invalidInput();
			}
		}while(select != 0);
		
	}
	
	private static void editSup(ItemSupplier supToEdit) {
		int select = -1;
		do {
			Main.clear("=============== Edit Supplier ===============\n");
			supToEdit.showDetails();
			System.out.println("Please select: \n\n"
							 + "[0] Back\n"
							 + "[1] Company Name\n"
							 + "[2] Email\n"
							 + "[3] Contact\n"
							 + "[4] Address\n"
							 + "[5] Add Selling Item\n"
							 + "[6] Edit Selling Item Price\n"
							 + "[7] Remove Selling Item\n");
			System.out.print(">> ");
			select = Integer.parseInt(Main.scanner.nextLine().trim());
			if(select == 0)
				break;
			else if(select < 0 || select > 7) {
				Main.invalidInput();
				continue;
			}
			Main.clear("=============== Edit Supplier ===============\n");
			supToEdit.showDetails();
			System.out.println("===========================================\n[Editing]\n");
			if(select <=4) {
				System.out.println("--------------------------------------------------");
				System.out.println("[Input Details]\n");
				switch(select-1){
					case 0:
						System.out.print("Supplier Company Name: ");
						break;
					case 1:
						System.out.print("Supplier Email: ");
						break;
					case 2:
						System.out.print("Supplier Contact: ");
						break;
					case 3:
						System.out.print("Supplier Company Address: ");
						break;
					default:
						System.out.println("Something went Wrong!");
						continue;
				}
				String input = scanner.nextLine();
				supToEdit.inputDetails(select-1, input);
			}
			else if(select == 5)
				addSupSellItem(supToEdit);
			else if(select == 6) {
				int itemIndex = findSellingItemIndex(supToEdit);
				if(itemIndex != -1) {
					System.out.print("Please input price of supplier selling for the Item : RM");
					double editPrice;
					try {
					     editPrice = Double.parseDouble(scanner.nextLine());
					}catch(NumberFormatException ex) {
						System.out.println(RED + "[Please input a Double value]" + RESET);
						sleep();
						continue;
					}
					if(editPrice > 0)
						supToEdit.setItemPrice(itemIndex,editPrice);
					else{
						System.out.println("[Sorry, the selling price must not less than ZERO]");
						Main.sleep();
					}
				}else {
					System.out.println("[Item Index Not Found!]");
					Main.sleep();
				}
			}else if(select == 7) {

				int itemIndex = findSellingItemIndex(supToEdit);
				if(itemIndex != -1) 
					supToEdit.removeSellingItem(itemIndex);
				else {
					System.out.println("[Item Index Not Found!]");
					Main.sleep();
				}
			}
		}while(select != 0);
	}
		
	private static int findSellingItemIndex(ItemSupplier sup) {
		System.out.print("Please input the Item Index : ");
		int index = Integer.parseInt(Main.scanner.nextLine().trim());
		if(index > 0 && index <= sup.getItemCount())
			return index-1;
		
		return -1;
	}
	
	private static ItemSupplier selectSup() {
		ItemSupplier selectedSup = null;
		do {
			clear("========== All Suppliers ==========");
			if(ItemSupplier.showSuppliers(suppliers)) {
				System.out.print("Please input the Supplier ID: ");
				int supId;
				try {
					supId = Integer.parseInt(scanner.nextLine());
				}catch(NumberFormatException ex) {
					System.out.println("[Please input the Supplier ID]");
					sleep();
					continue;
				}
				if(supId == 0)
					return null;
				selectedSup = ItemSupplier.findId(suppliers,supId);
				if(selectedSup == null) {
					System.out.println("[Supplier (ID: "+supId+") not found]");
					sleep();
				}	
			}else {
				System.out.println(RED + "[Currently Have No Supplier]\n" + RESET);
				pressEnter();
				return null;
			}
		}while(selectedSup == null);
		return selectedSup;
	}
	
	private static void addSupplier() {
	
		if(Item.getItemCount() == 0) {
			Main.clear("============================= Add Supplier =============================\n");
			System.out.println(Main.RED + "[Oops! Please create item that you are selling before you create a Supplier]\n" + Main.RESET);
			Main.pressEnter();
			return;
		}
		ItemSupplier sup = new ItemSupplier();
		
		for(int i = 0; i < 4; i++) {
			Main.clear("============================= Add Supplier =============================\n");
			sup.showDetails();

			System.out.println("========================================================================");
			System.out.println("[Input Details]\n");
			switch(i){
				case 0:
					System.out.print("Supplier Company Name: ");
					break;
				case 1:
					System.out.print("Supplier Email: ");
					break;
				case 2:
					System.out.print("Supplier Contact: ");
					break;
				case 3:
					System.out.print("Supplier Company Address: ");
					break;
				default:
					System.out.println("Something went Wrong!");
					continue;
			}
			String input = scanner.nextLine();
			
			if(!sup.inputDetails(i,input))
				i--;
		}
		boolean addItem = true;
		do {
			addItem = addSupSellItem(sup);
		}while(addItem);
		
		Main.clear("============================= Add Supplier =============================\n");
		sup.showDetails();
		
		if(confirm("Do you want to create this Supplier ? (Y/N) : ")) {
			suppliers[ItemSupplier.getSupCount()-1] = sup;
			System.out.println(Main.GREEN + "\n[Successfully create the Supplier]\n" + Main.RESET);
			Main.pressEnter();
		}else {
			ItemSupplier.cancelAddSup();
			System.out.println(Main.RED + "\n[The Supplier was not created!]\n" + Main.RESET);
			Main.pressEnter();
		}
		
	}
	
	private static boolean addSupSellItem(ItemSupplier sup) {
		Main.clear("===================== Add Supplier Selling Item(s) =====================\n");
		sup.showDetails();
		System.out.print("Please select an Item ID that the supplier sell :");
		Item.showAllItems(items);
		System.out.print("\n>> ");
		try {
			int itemId = Integer.parseInt(Main.scanner.nextLine().trim());
			if(itemId <= 0 || itemId > Item.getItemCount())
				return false;
			Item item = Item.findId(items,itemId);
			
			double price = 0;
			do {
				System.out.print("Please input Supplier selling price for the Item : RM ");
				price = Double.parseDouble(Main.scanner.nextLine().trim());
			}while(price <= 0);
			
			sup.addItem(item,price);
			
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return true;
	}
	
	private static void itemMenu() {
			
		int select = -1;
		do {
			Main.clear();
			System.out.println("=================== Item ====================\n");
			System.out.println("Please select :\n");
			System.out.println("[0] Back\n"
							 + "[1] Add New Item\n"
							 + "[2] Edit Item\n"
							 + "[3] Purchase Item\n");
			System.out.print(">> ");
			
			try {
				select = Integer.parseInt(Main.scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("[Invalid Input, please enter a number]");
				Main.sleep();
				continue;
			}
			
			switch(select) {
			case 0: break;
			case 1:
				addItem();
				break;
			case 2:
				editItem();
				break;
			case 3:
				purchaseItem();
				break;
			default:
				Main.invalidInput();
			}
		}while(select != 0);
	}
	
	private static void purchaseItem() {
		
		Branch currentBranch = Branch.searchEmp(branches,loggedEmp);
		while(true) {
			clear("============ Purchase Item ============");
			System.out.println("[Showing current Branch Inventory Level (Branch ID: "+currentBranch.getId()+")]");
			currentBranch.showInventoryLevel(items);
			System.out.print("Select The Item ID to purchase: ");
			int itemId;
			try {
				itemId = Integer.parseInt(scanner.nextLine());
			}catch(NumberFormatException ex) {
				System.out.println("[Please input a valid Item ID]");
				sleep();
				continue;
			}
			if(itemId == 0) return;
			Item itemToPurchase = Item.findId(items,itemId);
			if(itemToPurchase == null) {
				System.out.println(RED + "[Item (ID: "+itemId+") did not found]"+ RESET);
				sleep();
				continue;
			}
			clear("============ Purchase Item ============");
			System.out.println("[Suppliers that are selling Item (ID: "+itemToPurchase.getId()+")]");
			if(!ItemSupplier.showSellingItemSup(suppliers, itemToPurchase)) {
				pressEnter();
				continue;
			}
			System.out.print("Please input Supplier ID: ");
			int supId;
			try {
				supId = Integer.parseInt(scanner.nextLine());
			}catch(NumberFormatException ex) {
				System.out.println("[Please input a valid Supplier ID]");
				sleep();
				continue;
			}
			ItemSupplier sup = ItemSupplier.findId(suppliers,supId);
			if(sup == null) continue;
			System.out.print("Please input the Quantity to purchase (Item ID: "+itemToPurchase.getId()+"): ");
			int quantity;
			try {
				quantity = Integer.parseInt(scanner.nextLine());
			}catch(NumberFormatException ex) {
				System.out.println("[Invalid quantity input]");
				sleep();
				continue;
			}
			boolean purchased = sup.purchaseItem(currentBranch, itemToPurchase, quantity);
			if(purchased) 
				System.out.println(GREEN + "[Successfully purchased "+quantity+" of Item(ID: "+itemToPurchase.getId()+")]\n" + RESET);
			else 
				System.out.println(RED+ "[Item was not purchased]\n" + RESET);
			
			pressEnter();
		}
	}
	
	private static void editItem() {
		do {
			clear("============ Edit Item ============");
			if(Item.showAllItems(items)) { //Got item to show
				System.out.print("Input the Item ID :");
				int itemId;
				try {
					itemId = Integer.parseInt(scanner.nextLine());
				}catch(NumberFormatException e) {
					System.out.println("[Invalid Input, please enter a number]");
					Main.sleep();
					continue;
				}
				if(itemId != 0) {
					Item itemToEdit = Item.findId(items,itemId);
					if(itemToEdit != null) {
						int select = -1;
							do {
							clear("============ Edit Item ============");
							System.out.print(itemToEdit);
							System.out.println("Please select :\n");
							System.out.println("[0] Back\n" 
											  +"[1] Name\n"
											  +"[2] Category\n"
											  +"[3] Price\n"
											  +"[4] Volume\n");
							System.out.print(">> ");
							try {
								select = Integer.parseInt(scanner.nextLine());
							}catch(NumberFormatException e) {
								System.out.println("[Invalid Input, please enter a number]");
								Main.sleep();
								continue;
							}
							
							if(select == 0) break;
							else if(select < 0 || select > 4) 
								invalidInput();
							else {
								System.out.println("=======================================");
								switch(select-1) {
									case 0:
										System.out.print("Please input the Item Name : ");
										break;
									case 1:
										System.out.println("Please select the item Category : \n");
										for(int x = 0; x < Item.getCategories().length; x++) {
											System.out.printf("[%d] %s\n",x+1, Item.getCategories()[x]);
										}
										System.out.print("\n>> ");
										break;
									case 2:
										System.out.print("Please input the Item Selling Price : RM ");
										break;
									case 3:
										System.out.print("Please input the roughly volume (cm\u00B3):");
										break;
									default:
										System.out.println("Something went wrong!");
								}
								String input = scanner.nextLine();
								itemToEdit.inputDetails(select-1, input);
							}
						}while(select != 0);
					}
				}else 
					break;
				
			}else {
				System.out.println("[No Item Found]");
				pressEnter();
				break;
			}
		}while(true);
	}
	
	public static void addItem() {
		
		if(Item.getItemCount() >= Item.MAX_ITEM_TYPE) {
			System.out.println("[Reached MAXIMUM number of item created]");
			Main.sleep();
			return;
		}
		
		Item item = new Item();
		for(int i = 0; i < 4; i++) {
			Main.clear("====================== Adding New Item To System ======================\n");
			System.out.print(item);
			System.out.println("=======================================");
			switch(i) {
				case 0:
					System.out.print("Please input the Item Name : ");
					break;
				case 1:
					System.out.println("Please select the item Category : \n");
					for(int x = 0; x < Item.getCategories().length; x++) {
						System.out.printf("[%d] %s\n",x+1, Item.getCategories()[x]);
					}
					System.out.print("\n>> ");
					break;
				case 2:
					System.out.print("Please input the Item Selling Price : RM ");
					break;
				case 3:
					System.out.print("Please input the roughly volume (cm\u00B3):");
					break;
				default:
					System.out.println("Something went wrong!");
			}
			
			String input = scanner.nextLine();
			if(input.equals("0")) {
				Item.cancelCreate();
				return;
			}
			if(!item.inputDetails(i, input))
				i--;
		}
		Main.clear("====================== Adding New Item To System ======================\n");
		System.out.print(item);
		items[Item.getItemCount()-1] = item;
		System.out.println(Main.GREEN + "\n[Successfully added a new Item (ID: "+item.getId()+")]\n" + Main.RESET);
		Main.pressEnter();
	}
	
	private static void logOut() {
		loggedEmp = null;
		System.out.println("[Logging Out]");
		sleep();
	}
	
	private static void profile() {
		while(true) {
			Main.clear("=============== Profile ===============");
			loggedEmp.showDetails(Branch.searchEmp(branches, loggedEmp));
			System.out.println("Please select : \n");
			System.out.println("[0] Back\n"
							 + "[1] Name\n"
							 + "[2] Email\n"
							 + "[3] Contact\n"
							 + "[4] Password\n");
			System.out.print(">> ");
			int select;
			try {
				 select = Integer.parseInt(Main.scanner.nextLine().trim());
				 switch(select) {
					 case 0:
						 return;
					case 1:
						System.out.print("Please input Name : ");
						break;
					case 2:
						System.out.print("Please input Email : ");
						break;
					case 3:
						System.out.print("Please input Contact : ");
						break;
					case 4:
						System.out.print("Please input Password : ");
						break;
					default:
						invalidInput();
						continue;
				 }
				 
				 String input = scanner.nextLine();
				if(input.equals("0")) {
					continue;
				}
				loggedEmp.inputDetails(select-1,input,employees,DEPARTMENTS,branches);
			} catch (NumberFormatException e) {
				System.out.println("[Invalid Input, please input an Integer value]");
				Main.sleep();
				continue;
			}
		}
		
	}
	
	/*
	 * This method is the Head Menu of the sub menus of Employee
	 * Which contains of Add Employee, Edit Employee 
	 * It will loop until the user select back option ( [0] Back ) 
	 */
	private static void empMenu() {

		int select = -1;		
		do {
			Main.clear("=================== Employee ====================");
			System.out.println("Please select :\n");
			System.out.println("[0] Back\n"
							 + "[1] Add Employee\n"
							 + "[2] Edit Employee\n");
			System.out.print(">> ");
			
			try {
				select = Integer.parseInt(Main.scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("[Invalid Input, please enter a number]");
				Main.sleep();
				continue;
			}
			
			switch(select) {
				case 0://Back
					break;
				case 1://Add
					addEmp();
					break;
				case 2://Edit
					Employee empToEdit = selectEmp();
					if(empToEdit!=null) 
						editEmp(empToEdit);
					break;	
				default:
					Main.invalidInput();
			}
		}while(select != 0);
	}

	public static void addEmp() {

		Employee emp = new Employee();
		Branch empBranch = Branch.searchEmp(branches, emp);
		for (int i = 0; i < 8; i++) { //The details to fill in has only 8

			Main.clear("====== Add New Employee ======");
			emp.showDetails(empBranch);
			switch(i) {
				case 0:
					System.out.print("Please input Name : ");
					break;
				case 1:
					System.out.print("Please input Email : ");
					break;
				case 2:
					System.out.print("Please input Contact : ");
					break;
				case 3:
					System.out.print("Please input Password : ");
					break;
				case 4:
					System.out.println("Please select a Department : \n");
					Department.showAllDepartment(DEPARTMENTS);
					System.out.print("\n>> ");
					break;
				case 5:
					System.out.println("Please select the Branch ID : ");
					Branch.showAllBranch(branches);
					System.out.print(">> ");
					break;
				case 6:
					System.out.print("Salary : RM ");
					break;
				case 7:
					System.out.println("Please select the Status : \n\n"
							 + "[0] Pending\n"
							 + "[1] Valid\n"
							 + "[2] Invalid\n");
					System.out.print(">> ");
					try {
						int statusInt = Integer.parseInt(scanner.nextLine());
						if(!emp.inputStatus(statusInt, branches))
							sleep();
					}catch(NumberFormatException ex) {
						System.out.println("[Please input an Integer value]");
						sleep();
					}
					continue;
				default: 
					invalidInput();
					continue;
			}
			
			String input = scanner.nextLine();
			if(input.equals("0")) {
				Employee.cancelRegisterEmp();
				return;
			}
			if(!emp.inputDetails(i,input,employees,DEPARTMENTS,branches))
				i--;
		} 

		clear("====== Add New Employee ======");
		emp.showDetails(empBranch);
		System.out.println("Are you sure you want to create this account? [Y/N]");
		System.out.print(">> ");

		char yesNo = Main.scanner.next().charAt(0);
		if (yesNo == 'Y' || yesNo == 'y') {
			employees[Employee.getCreatedEmp()-1] = emp;
			System.out.println("[The account has SUCCESSFULLY CREATED]");
			System.out.println("[Note] Please ask the employee to confirm the personal details once logged.");
		} else {
			System.out.println("[The account is NOT CREATED]");
			Employee.cancelRegisterEmp();
		}
		Main.pressEnter();
	}

	private static Employee selectEmp() {
		while (true) {

			clear();
			System.out.println("[All Employee Are Listed Here]\n");
			Employee.showAllEmp(branches,employees);

			System.out.print("Please select the employee by using its ID : ");
			int selectedId = -1;
			try {
				selectedId = Integer.parseInt(Main.scanner.nextLine().trim());
				if (selectedId == 0) return null;
				
				Employee empIndex = Employee.findId(employees, selectedId);
				if(empIndex != null)
					return empIndex;
				System.out.println("[The Employee ID (" + selectedId + ") doesn't found. Please try again!]");
				sleep();
			} catch (NumberFormatException e) {
				System.out.println("[Invalid Input, please choose the Employee ID]");
				sleep();
				continue;
			}
		}
	}
	
	private static void editEmp(Employee emp) {
		do {
			clear("=========== Edit Employee ===========");
			Branch empBranch = Branch.searchEmp(branches, emp);
			emp.showDetails(empBranch);
			System.out.println("Select the field to edit:\n");
			System.out.println("[0] Back\n"
							 + "[1] Name\n"
							 + "[2] Email\n"
							 + "[3] Contact\n"
							 + "[4] Password\n"
							 + "[5] Department\n"
							 + "[6] Branch\n"
							 + "[7] Salary\n"
							 + "[8] Status\n");
			System.out.print(">> ");
			
			int choice;
			try {
				choice = Integer.parseInt(Main.scanner.nextLine().trim());
				if (choice == 0) return;
				choice--;
				clear("=========== Edit Employee ===========");
				emp.showDetails(empBranch);
				System.out.println("======================================\n[Editing]\n");
				switch(choice) {
					case 0:
						System.out.print("Please input Name : ");
						break;
					case 1:
						System.out.print("Please input Email : ");
						break;
					case 2:
						System.out.print("Please input Contact : ");
						break;
					case 3:
						System.out.print("Please input Password : ");
						break;
					case 4:
						System.out.println("Please select a Department : \n");
						Department.showAllDepartment(DEPARTMENTS);
						System.out.print("\n>> ");
						break;
					case 5:
						System.out.println("Please select the Branch ID : ");
						Branch.showAllBranch(branches);
						System.out.print(">> ");
						break;
					case 6:
						System.out.print("Salary : RM ");
						break;
					case 7:
						System.out.println("Please select the Status : \n\n"
								 + "[0] Pending\n"
								 + "[1] Valid\n"
								 + "[2] Invalid\n");
						System.out.print(">> ");
						try {
							int statusInt = Integer.parseInt(scanner.nextLine());
							if(!emp.inputStatus(statusInt, branches))
								sleep();
						}catch(NumberFormatException ex) {
							System.out.println("[Please input an Integer value]");
							sleep();
						}
						continue;
					default: 
						invalidInput();
						continue;
				}
				String input = scanner.nextLine();
				emp.inputDetails(choice, input,employees,DEPARTMENTS,branches);
			} catch (NumberFormatException e) {
				System.out.println("[Invalid Input, please enter a number]");
				Main.sleep();
				continue;
			}
		}while(true);
	}

	private static void branchMenu() {

		int select = -1;		
		do {
			Main.clear("=================== Branch ====================");
			System.out.println("[0] Back\n"
							 + "[1] Add Branch\n"
							 + "[2] Edit Branch\n"
							 + "[3] Show All Branch\n"
							 + "[4] Current Branch Details\n");
			System.out.print(">> ");
			
			try {
				select = Integer.parseInt(scanner.nextLine());
			}catch (NumberFormatException e) {
				System.out.println("[Invalid Input, please enter a number]");
				Main.sleep();
				continue;
			}
			
			switch(select) {
				case 0://Back
					break;
				case 1://Add
					addBranch();
					break;
				case 2://Edit
					Branch branchToEdit = selectBranch();
					if(branchToEdit!=null)
						editBranch(branchToEdit);
					break;	
				case 3://Show 
					showBranchesDetails();
					break;
				case 4:
					showCurrentBranchDetails();
					break;
				default:
					Main.invalidInput();
			}
		}while(select != 0);
		
	}

	private static void showCurrentBranchDetails() {
		clear("=========================== Current Branch Details ===========================\n");
		Branch currentBranch = Branch.searchEmp(branches, loggedEmp);
		if(currentBranch == null) { 
			System.out.println(RED + "[Oops! you are currently not in any Branch]\n" + RESET);
			pressEnter();
			return;
		}
		currentBranch.showDetails();
		System.out.println("=============== Employees Under Current Branch ===============");
		currentBranch.showAllEmp();
		System.out.println("=============== Inventory Level ===============");
		currentBranch.showInventoryLevel(items);
		Main.pressEnter();
		
	}
	
	private static void showBranchesDetails() {
	    while (true) {
	      
	        Branch selectedBranch = selectBranch();
	        if (selectedBranch != null) {
	            displayBranchDetails(selectedBranch);
	        } else {
	           break;
	        }
	    }
	}

	private static void displayBranchDetails(Branch branch) {
	    while (true) {
	        Main.clear("================================= Showing Branch " + branch.getComName() + " ==================================");
	        branch.showDetails();
	        System.out.print("Do you want to view more details [Y/N]: ");
	        char yesNo = scanner.nextLine().charAt(0);
	        if (yesNo == 'Y' || yesNo == 'y') {
	            displayWarehouses(branch);
	        } else {
	            return;
	        }
	    }
	}

	private static void displayWarehouses(Branch branch) {
	    while (true) {
	        Main.clear("================================= Warehouses of " + branch.getComName() + " ==================================");
	        branch.showWarehouses();
	        
	        System.out.print("Please input Warehouse ID"+GREEN+"[0 to EXIT]"+RESET+": ");
	        char wHouseId;
	        try{
	        	wHouseId = scanner.nextLine().charAt(0);
	        }catch(Exception ex) {
	        	invalidInput();
	        	continue;
	        }
	        if (wHouseId == '0') return;

	        Warehouse wHouse = branch.findId(wHouseId);
	        if (wHouse != null) {
	            displayWarehouseDetails(wHouse, branch);
	        } else {
	            System.out.println("[Warehouse ID not found, please try again]");
	            Main.sleep();
	        }
	    }
	}

	private static void displayWarehouseDetails(Warehouse wHouse, Branch branch) {
	    while (true) {
	        Main.clear("============================= Warehouses " + wHouse.getWarehouseId() + " in " + branch.getComName() + " ==============================");
	        wHouse.showDetails();
	        
	        int level;
	        System.out.print("Please input Floor Level "+GREEN+"[0 to EXIT]"+RESET+":");
	        try {
	        	level = Integer.parseInt(scanner.nextLine());
	        }catch(NumberFormatException ex) {
	        	System.out.println("[Please input a number Level]");
	        	sleep();
	        	continue;
	        }
	        if (level == 0) return;

	        Floor floor = wHouse.findLevel(level);
	        if (floor != null) {
	            displayFloorDetails(floor);
	        } else {
	            System.out.println("[Floor level not found, please try again]");
	            Main.sleep();
	        }
	    }
	}

	private static void displayFloorDetails(Floor floor) {
		
	    if (floor.getRackCount() == 0) {
	        System.out.println("No racks on this floor.");
	        Main.pressEnter();
	        return;
	    }
	    
	    char rackId = floor.getRacks()[0].getRackId(); // Default Rack [A]
	    while (true) {
	        Main.clear("=============================== Level " + floor.getLevel() + " =================================");
	        floor.showingRackId(rackId);
	        
	        Rack rack = floor.findRack(rackId);
	        if (rack != null) {
	        	System.out.println("[Rack Category: "+rack.getCategory()+"]");
	            rack.showGrid();
	        } else {
	            System.out.println("[Rack ID not found, please try again]");
	            Main.sleep();
	        }
	        
	        System.out.print("Please input another Rack ID "+GREEN+"[0 to EXIT]"+RESET+": ");
	        rackId = Character.toUpperCase(Main.scanner.next().charAt(0));
	        Main.scanner.nextLine();
	        if (rackId == '0') return;
	    }
	}
	
	private static void addBranch() {

		Branch branch = new Branch();
		
		for (int i = 0; i < 6; i++) { // Only 6 details required from user to create a branch
			Main.clear("===== Create Branch =====");
			branch.showDetails();
			switch(i) {
			    case 0:
			        System.out.print("Branch Name: ");
			        break;
			    case 1:
			        System.out.print("Branch Email: ");
			        break;
			    case 2:
			        System.out.print("Branch Contact: ");
			        break;
			    case 3:
			        System.out.print("Branch Address: ");
			        break;
			    case 4:
			        System.out.print("Balance: RM ");
			        break;
			    case 5:
			        System.out.println("Status: \n");
			        System.out.println("[0] Invalid\n"
			        				 + "[1] Valid\n");
			        System.out.print(">> ");
			        break;
			    default:
			        System.out.print("Invalid index");
			}
			String input = scanner.nextLine();
			if(input.equals("0")) {
				Branch.cancelCreateBranch();
				return;
			}
			if(!branch.inputDetails(i,input))
				i--;
		}
		
		Main.clear("===== Create Branch =====");
		branch.showDetails();
		System.out.println("Do you want to continue create the Warehouse for this branch ? [Y/N]");
		System.out.print(">> ");

		char yesNo = Main.scanner.next().charAt(0);
		Main.scanner.nextLine();

		
		//--- Continue create Warehouses ---
		if (yesNo == 'Y' || yesNo == 'y') {
			System.out.print("\nHow many warehouse(s) you wanted to add into this Branch : ");
			try {
				int numOfWarehouse = Integer.parseInt(Main.scanner.nextLine().trim());
				for (int i = 0; i < numOfWarehouse; i++) {
					Main.clear("===== Create Branch =====");
					branch.showDetails();
					System.out.printf("[Create Warehouses (%d/%d)]\n", i + 1, numOfWarehouse);
					Warehouse wHouse = createWarehouse(branch.getWHouseCount());
					branch.addWarehouse(wHouse);
				}
			} catch (NumberFormatException e) {
				System.out.println("[Invalid Input, please input an Integer value]");
				Main.sleep();
			}
		}

		branches[Branch.getBranchCount()-1] = branch;

		Main.clear("===== Create Branch =====");
		branch.showDetails();
		System.out.println("[Successfully created a new Branch]\n");
		Main.pressEnter();
	}
	
	private static Warehouse createWarehouse(int wHouseCount) {
		
		char wHouseId = Warehouse.generateWarehouseId(wHouseCount);
		System.out.println("Warehouse ID: " + wHouseId);
		System.out.print("Please input the Description of the warehouse: ");
		String desc = scanner.nextLine();
		System.out.print("Please input the Number of Floor(s) for the warehouse: ");
		int numOfFloor;
		Floor[] floors = new Floor[Warehouse.MAX_FLOOR];
		try {
			numOfFloor = Integer.parseInt(scanner.nextLine());
			for(int i = 0; i < numOfFloor; i++) {
				floors[i] = new Floor(i+1);
			}
		} catch (NumberFormatException e) {
			System.out.println("[Invalid Input, The floor has been set to ZERO]");
			Main.sleep();
			numOfFloor = 0;
		}
		Warehouse wHouse = new Warehouse(desc, true, wHouseCount, floors, numOfFloor);
		return wHouse;
	}

	private static Branch selectBranch() {
		while (true) {

			 Main.clear("================================= All Branch ==================================\n");
		        
		        // Find the branch of the logged-in employee
		        Branch currentBranch = Branch.searchEmp(branches, loggedEmp);
		        if (currentBranch == null) {
		            System.out.println(RED + "[Oops! you are currently not in any Branch]\n" + RESET);
		            pressEnter();
		            return null;
		        }
		        
		        System.out.println(Main.YELLOW + "[Note] You are currently in Branch (ID: " + currentBranch.getId() + ")" + Main.RESET);
		        
		        // Display all branches
		        if (!Branch.showAllBranch(branches)) {
		            Main.pressEnter();
		            return null;
		        }
		        
		        int branchId;
		        try {
		        	System.out.print("Please input Branch ID "+GREEN+"[0 to EXIT]"+RESET+": ");
		        	branchId = Integer.parseInt(scanner.nextLine());
		        }catch(NumberFormatException ex) {
		        	System.out.println("[Please input a valid Branch ID]");
		        	sleep();
		        	continue;
		        }
		        if (branchId == 0) return null;
		        Branch branch = Branch.selectBranch(branches, branchId);
		        if(branch == null) {
		        	System.out.println("[Branch ID not found]");
		        	sleep();
		        	continue;
		        }
		        return branch;
		}
	}
	
	private static void editBranch(Branch branchToEdit) {
		
		int select = -1;

		do {
			Main.clear("============ Edit Branch ============");
			branchToEdit.showDetails();
			System.out.println("Please select : \n");
			System.out.println("[0] Back\n" + 
							   "[1] Branch Name\n" +
							   "[2] Branch Email\n" +
							   "[3] Branch Contact\n"+ 
							   "[4] Branch Address\n" +
							   "[5] Balance\n" +
							   "[6] Status\n" +
							   "[7] Employee\n" +
							   "[8] Warehouse\n");
			System.out.print(">> ");
			
			try {
				select = scanner.nextInt();
				scanner.nextLine();
			}catch(NumberFormatException e) {
				System.out.println("[Invalid Input, please enter a number]");
				Main.sleep();
				continue;
			}
			if (select >= 0 && select <= 8) {
				
				if (select == 0)
					return;

				Main.clear("============ Edit Branch ============");
				branchToEdit.showDetails();
				System.out.print("=====================================\n");
				System.out.println("[Editing]\n");
				
				switch(select) {
					case 0:
						break;
				    case 1:
				        System.out.print("Branch Name: ");
				        break;
				    case 2:
				        System.out.print("Branch Email: ");
				        break;
				    case 3:
				        System.out.print("Branch Contact: ");
				        break;
				    case 4:
				        System.out.print("Branch Address: ");
				        break;
				    case 5:
				        System.out.print("Balance: RM ");
				        break;
				    case 6:
				        System.out.println("Status: \n");
				        System.out.println("[0] Lock\n"
				        				 + "[1] Valid\n");
				        System.out.print(">> ");
				        break;
				    case 7:
				    	editBranchEmp(branchToEdit);
				    	break;
				    case 8:
				    	editBanchWhouse(branchToEdit);
				    	break;
				    default:
				        System.out.print("Invalid index");
				}
				
				if(select != 7 || select != 8) {
					String input = scanner.nextLine();
					branchToEdit.inputDetails(select-1, input);
				}
				
			} else
				invalidInput();
		} while (select != 0);
		
	}
	
	/*
	 * This method will prompt user to input selection which included Add, Edit, Remove warehouse
	 * After user input the choice it will call the specific method and return true, unless
	 * the user selected [0] only it will return false, so that the loop in the previous method will end
	 */
	private static void editBanchWhouse(Branch branchToEdit) {
		
		int choice = -1;
		do {
			clear("============ Editing Branch Warehouse ============");
			System.out.println("Branch ID : " + branchToEdit.getId() + "\n");
			
			System.out.println("Please select : \n");
			System.out.println("[0] Back\n"
							  +"[1] Add Warehouse\n"
							  +"[2] Edit Warehouse\n"
							  +"[3] Clear Warehouse Items\n");
			System.out.print(">> ");
			
			try {
				choice = Integer.parseInt(scanner.nextLine().trim());
			}catch(NumberFormatException e) {
				System.out.println("[Invalid Input, please enter a number]");
				Main.sleep();
				continue;
			}
			
			if(choice == 0) return;
			else if(choice == 1) {
				Main.clear("========= Add New Warehouse ========");
				System.out.println("Branch ID   : " + branchToEdit.getId());
				System.out.println("Branch Name : " + branchToEdit.getComName() + "\n");
				Warehouse wHouse = createWarehouse(branchToEdit.getWHouseCount());
				branchToEdit.addWarehouse(wHouse);
			}
			else if(choice == 2) {
				
				while(true) {
					Main.clear("============= Edit Warehouse ==============");
					System.out.println("Branch ID   : " + branchToEdit.getId());
					System.out.println("Branch Name : " + branchToEdit.getComName() + "\n");
					branchToEdit.showWarehouses();
					System.out.print("Please select the Warehouse ID: ");
		
					char wHouseId = Character.toUpperCase(scanner.nextLine().charAt(0));
					if(wHouseId == '0') break;

					Warehouse[] warehouses = branchToEdit.getWarehouse();
					for(Warehouse wHouse : warehouses) {
						if(wHouse == null) break;
						if(wHouse.getWarehouseId() == wHouseId)
							editWhouse(wHouse);
					}
				}
			}
			else if(choice == 3) {
				
				while(true){
					Main.clear("============= Clear Warehouse Items ==============");
					System.out.println("Branch ID   : " + branchToEdit.getId());
					System.out.println("Branch Name : " + branchToEdit.getComName() + "\n");
					
					branchToEdit.showWarehouses();
					System.out.print("Please select the Warehouse ID: ");
					char wHouseId = scanner.nextLine().charAt(0);
					
					if(wHouseId == '0') break;
					boolean cleared = false;
					Warehouse[] warehouses = branchToEdit.getWarehouse();
					for(Warehouse wHouse : warehouses) {
						if(wHouse == null) break;
						else if(wHouse.getWarehouseId() == wHouseId) {
							wHouse.clear(branchToEdit.getStoreroom());
							cleared = true;
						}
					}
					
					if(!cleared) {
						System.out.println("[Warehouse ID doesn't found]");
						Main.sleep();
					}
				}
			}
			else
				Main.invalidInput();
		}while(choice != 0);
	}

	private static void editWhouse(Warehouse wHouse) {
		
		while(true) {
			Main.clear("================== Editing Warehouse " + wHouse.getWarehouseId() + " ==================");
			wHouse.showDetails();
			System.out.println("Please select :\n");
			System.out.println("[0] Back\n"
							 + "[1] Add Floor\n"
							 + "[2] Edit Floor\n"
							 + "[3] Lock / Unlock Warehouse\n");
			System.out.print(">> ");
			
			int select;
			try {
				select = Main.scanner.nextInt();
				Main.scanner.nextLine();
			}catch(NumberFormatException e) {
				System.out.println("[Invalid Input, please enter a number]");
				Main.sleep();
				continue;
			}
			
			System.out.println("==================================================");
			
			switch(select) {
				case 0:
					return;
				case 1:
					if(wHouse.addFloor()) 
						System.out.println("[Successfully added a new Floor into Warehouse " + wHouse.getWarehouseId() + "]");
					else 
						System.out.println("[Sorry, this warehouse has created MAX Amount of floors]");
					Main.sleep();
					break;
				case 2:
					System.out.print("Please select the level of Floor : ");
					try {
						int level = scanner.nextInt();
						scanner.nextLine();
						
						Floor floorToEdit = wHouse.findLevel(level);
						if(floorToEdit != null) 
							editFloor(floorToEdit);
						else 
							Main.invalidInput();
					}catch(NumberFormatException e) {
						System.out.println("[Invalid Input, please enter a number]");
						Main.sleep();
					}
					break;
				case 3:
					wHouse.setStatus(!wHouse.getStatus());
					System.out.printf("[You Had Successfully %s the Warehouse %c]", (wHouse.getStatus())?"Unlocked":"Locked", wHouse.getWarehouseId());
					Main.sleep();
					break;
				default:
					Main.invalidInput();
			}
		}
	}

	private static void editFloor(Floor floorToEdit) {
		
		while (true) {
			Main.clear("============= Editing Floor Level " + floorToEdit.getLevel() + " =============");
			
			floorToEdit.showRacks();
			System.out.println("Please select : \n");
			System.out.println("[0] Back \n" + "[1] Add Rack\n" + "[2] Clear Rack\n" + "[3] Edit Rack\n");
			System.out.print(">> ");
			
			int select;
			try {
				select = scanner.nextInt();
				scanner.nextLine();
			}catch(NumberFormatException e) {
				System.out.println("[Invalid Input, please enter a number]");
				Main.sleep();
				continue;
			}
			System.out.println("============================================================================");
			if (select == 0)
				return;
			else if (select == 1) {
				System.out.println("[Adding Rack]");
				Rack rack = createRack(floorToEdit.getRackCount());
				floorToEdit.addRack(rack);
			}
			else if (select == 2) {
				System.out.println("[Clear On Rack Item To Storeroom]");
				System.out.print("Please select the Rack ID: ");
				char rackId = scanner.nextLine().charAt(0);
				Rack rackToClear = floorToEdit.findRack(rackId);
				Storeroom sr = Branch.searchEmp(branches, loggedEmp).getStoreroom();
				
				if(rackToClear != null) {
					rackToClear.storeItemsToSR(sr);
				}
				
			}
			else if (select == 3)
				editRack(floorToEdit);
		}
	}

	private static void editRack(Floor floorToEdit) {
		while (true) {
			Main.clear("============ Edit Rack ============\n");
			floorToEdit.showRacks();
			System.out.print("Please select the Rack ID to edit : ");
			char rackId = scanner.next().trim().charAt(0);
			scanner.nextLine();
			rackId = Character.toUpperCase(rackId);
			
			if (rackId == '0')
				return;
			Rack rack = floorToEdit.findRack(rackId);
			if (rack != null) {
				
				clear("============ Editing Rack "+rack.getRackId()+" ============");
				System.out.println(rack);
				System.out.println("[Editing Rack Category]");
				rack.setCategory(inputRackCategory());
				break;
			} else {
				System.out.println("[Sorry, the Rack ID doesn't found please try again]");
				Main.sleep();
			}
		}
	}
	
	private static Rack createRack(int floorRackCount) {
		
		Rack rack = new Rack(floorRackCount,"-",true); //Default true because not FULL
		System.out.println(rack);
		
		rack.setCategory(inputRackCategory());
		return rack;
	}

	private static String inputRackCategory() {
		
		String[] categories = Item.getCategories();
		System.out.println("Please select the Category for Item that will store on this Rack : \n");
		for(int i = 0; i < categories.length; i++) 
			System.out.printf("[%d] %s\n",i+1, categories[i]);
		System.out.print("\n>> ");
		
		try {
			int index = scanner.nextInt();
			scanner.nextLine();
			
			if(index > categories.length || index <= 0) 
				return categories[categories.length-1];//The last one is "Other"
			else
				return categories[index-1];
		}catch(NumberFormatException ex) {
			System.out.println("[Due to invalid input, the Rack storing category has been set to OTHER.]");
			System.out.println("[Please edit it after if any changes.]");
			pressEnter();
			return categories[categories.length-1];//The last one is "Other"
		}
	}
	
	private static void editBranchEmp(Branch branchToEdit) {
		int choice = -1;
		do {
			clear("============ Editing Branch Employee ============");
			System.out.println("Branch ID : " + branchToEdit.getId() + "\n");
			System.out.println("Please select : \n");
			System.out.println("[0] Back\n"
							  +"[1] Add Employee\n"
							  +"[2] Remove Employee\n");
			System.out.print(">> ");
			try {
			   choice = scanner.nextInt();
			}catch(NumberFormatException e) {
				System.out.println("[Invalid Input, please enter a number]");
				sleep();
				continue;
			}

			if(choice == 1) {
				while(true) {
					clear("========== Adding Employee To Current Branch ==========");
					System.out.println("Branch ID : " + branchToEdit.getId() + "\n");
					System.out.println("[Note] You can only add employee(s) with no Branch.\n");
					Employee[] noBranchEmps = Branch.getNoBranchEmp(branches, employees);
					Employee.showAllEmp(branches,noBranchEmps);
					System.out.print("Please select the Employee ID: ");
					
					try {
						int empId = scanner.nextInt();
						scanner.nextLine();
						
						if(empId == 0) break;
						for(Employee emp : noBranchEmps) {
							if(emp == null) break;
							if(emp.getId() == empId)
								branchToEdit.addEmp(emp);
						}
					}catch(NumberFormatException e) {
						System.out.println("[Invalid Input, please enter a number]");
						sleep();
						continue;
					}
				}
			}
			else if (choice == 2) {
				while(true) {
					clear("========== Removing Employee From Current Branch ==========");
					System.out.println("Branch ID : " + branchToEdit.getId() + "\n");
					System.out.println("[Note] You can only remove employee(s) in current Branch.\n");
					Employee[] currentBranchEmp = branchToEdit.getEmployees();
					Employee.showAllEmp(branches,currentBranchEmp);
					System.out.print("Please select the Employee ID: ");
					
					try {
						int empId = scanner.nextInt();
						scanner.nextLine();
						
						if(empId == 0) break;
						for(Employee emp : currentBranchEmp) {
							if(emp == null) break;
							if(emp.getId() == empId)
								branchToEdit.removeEmp(emp);
						}
					}catch(NumberFormatException e) {
						System.out.println("[Invalid Input, please enter a number]");
						sleep();
						continue;
					}
				}
			}
			else
				Main.invalidInput();
		}while(choice!= 0);
	}

	//This method clear the console output, and also after cleared it shows the company name
	public static void clear() {
		
		for(int i = 0; i < 100; i++) {
			System.out.println();
		}
		System.out.print("\033[H\033[2J");
	    System.out.flush();
	    System.out.println(BG_WHITE+BLACK+COMPANY_NAME + "\n");
	    System.out.print(RESET);
	}

	//This method clear the console out, and shows the company name, then show the String from parameter
	public static void clear(String title) {
		clear();
	    System.out.println(title + "\n");
	}
	
	//This method will prompt the user it is invalid input then call sleep() to delay 
	public static void invalidInput() {
		System.out.println("[Invalid Input]");
		sleep();
	}
	
	//This method used to delay for 2 second
	public static void sleep() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/* 
	 * This method is used stop the application to the next step
	 * And only continue when the user pressed enter
	 */
	public static void pressEnter() {

		System.out.println("[Press ENTER to continue]");
		scanner.nextLine();
	}
}
