package company;

import employee.Employee;
import item.Item;
import warehouse.Warehouse;

public class Branch extends Company{
	//Attributes
	private double balance;
	private boolean status;
	private Storeroom storeroom;
	private Employee[] employees; //Each branch contains a few Employee
	private Warehouse[] warehouses; // Each branch contains a few warehouse
	private int empCount;
	private int wHouseCount;
	
	private static int branchCount;
	
	//Constants
	public static final int WAREHOUSE_COUNT_PER_BRANCH = 10;
	public static final int EMPLOYEE_COUNT_PER_BRANCH = 100;
	
	
	

	//Constructors
	public Branch() {
		this(null, null, null, null, 0.0, false);
	}
	public Branch(String companyName, String email, String contact, String address, // Super()
			double balance, boolean status) {
		super(++branchCount, companyName, email, contact, address);
		this.balance = balance;
		this.status = status;
		this.storeroom = new Storeroom();
		this.employees = new Employee[EMPLOYEE_COUNT_PER_BRANCH];
		this.warehouses = new Warehouse[WAREHOUSE_COUNT_PER_BRANCH];
		this.empCount = 0;
		this.wHouseCount = 0;
	}


	
	//Getter & Setter
	public void setStatus(boolean status) {this.status = status;}
	public double getBalance() {return balance;}
	public boolean getStatus() {return status;}
	public int getEmpCount() {return empCount;}
	public int getWHouseCount() {return wHouseCount;}
	public static int getBranchCount() {return branchCount;}
	public Storeroom getStoreroom() {return storeroom;}
	public Employee[] getEmployees() {return employees;}
	public Warehouse[] getWarehouse() {return warehouses;}
	
	
	
	
	  ///////////////////////////////////////////
	 ////////////// Static Methods /////////////
	///////////////////////////////////////////
	
	/*
	 * This method search through the branches passed in and then 
	 * check that does the branch contains the emp, if it contains
	 * then return the branch
	 */
	public static Branch searchEmp(Branch[] branches, Employee emp) {
		for (int i = 0; i < branchCount ; i++) {
			if (branches[i].containsEmp(emp))
				return branches[i];
		}
		return null;
	}
	
	/*
	 * This method used to show all the branches in this system, including some basic detials
	 * which are ID, Name, Balance, Number of Warehouse, Number of Employee, Status
	 */
	public static boolean showAllBranch(Branch[] branches) {
		if (branchCount > 0) {
			System.out.print( "  ____________________________________________________________________________ \n"
							+ " | ID |    Company Name    |  Balance (RM)  | Warehouse | Employee |  Status  |\n"
							+ " |----|--------------------|----------------|-----------|----------|----------|\n");

			for (int i = 0; i < branchCount; i++) {
				System.out.printf(" | %-2d | %-18s | %-14.2f | %-9d | %-8d | %-8s |\n", branches[i].getId(),
						branches[i].getComName(), branches[i].getBalance(), branches[i].getWHouseCount(),
						branches[i].getEmpCount(), (branches[i].getStatus()) ? "Valid" : "Invalid");
			}
			System.out.println(" |____|____________________|________________|___________|__________|__________|\n");
			return true;
		} else {
			System.out.println("------------------------");
			System.out.println("  (ZERO Branch Record)  ");
			System.out.println("------------------------\n\n");
			return false;
		}
	}
	
	/*
	 * This method required to pass the branch ID
	 * Then it will filter among all the branches in this system
	 * And return the Branch which match with the Branch ID passed
	 * If no branch match with the Branch ID it will return a null
	 */
	public static Branch selectBranch(Branch[] branches, int branchId) {
		
		for (Branch branch : branches) {
			if(branch == null) break;
			if (branch.getId() == branchId)
				return branch;
		}
		return null;
	}
	
	/*
	 * This method will filter from each branches and check does the branch contains
	 * the employee, if not it will put the employee into the noBranchEmp array
	 * and then after that return the array
	 */
	public static Employee[] getNoBranchEmp(Branch[] branches, Employee[] employees){
		
		Employee[] noBranchEmp = new Employee[100];
		int count = 0;
		
		for(Employee emp : employees) {
			if(emp == null)
				break;
			if(Branch.searchEmp(branches,emp) == null)
				noBranchEmp[count++] = emp;
		}
		return noBranchEmp;
	}
	
	/*
	 * This method used to convert the int passed then 
	 * return a boolean value, if the user passed [1] then it will 
	 * return true, else it return false
	 */
	private static boolean statusConvert(int selectedStatus) {
		if (selectedStatus == 1)
			return true;
		return false;
	}
	
	/*
	 * This method used to cancel creating branches
	 * it will deduct the branch count by 1 so that
	 * the next branch created will still use the Branch ID 
	 * without any skipping
	 */
	public static void cancelCreateBranch() {
		branchCount--;
	} 
	
	
	
	
	  ///////////////////////////////////////////
	 ///////////// Instance Methods ////////////
	///////////////////////////////////////////
	
	/*
	 * This method is used to input/edit the details of the branch Based on the
	 * index given, the index represent which details the branch wanted to be
	 * edit/input The following DETAILS_INDEX represent the index (the index start
	 * from 0)
	 */
	public boolean inputDetails(int index, String input) {
		
		boolean valid = false;
		switch (index) {
		case 0: //Branch Name
			valid = inputName(input);
			break;
		case 1://Branch Email
			valid = inputEmail(input);
			break;
		case 2: //Branch Contact 
			valid = inputContact(input);
			break;
		case 3: //Branch Address
			valid = inputAddress(input);
			break;
		case 4: //Branch Balance
			try {
				balance = Double.parseDouble(input);
				if(balance >= 0)
					valid = true;
			}catch(Exception ex) {
				balance = 0;
			}
			break;
		case 5: //Branch Status
			status = statusConvert(Integer.parseInt(input));
			valid = true;
			break;
		default:
			System.out.println("[Invalid Input]");
		}
		return valid;
	}
	
	/*
	 * This method will check the status if 
	 * the status is false it return locked else return valid
	 */
	public String statusString() {
		return (getStatus()) ? "Valid" : "Locked";
	}
	
	/*
	 * This method used to show the details of the Branch
	 * Which included ID, Name, Email, Contact, Address, Balance, Status, Number of Employee, Number of Branch
	 */
	public void showDetails() {
		System.out.printf(
						  " ____________________________________ \n" 
						+ "|           Branch Details           |\n"
						+ "|------------------------------------|\n" 
						+ "| ID        | %-22d |\n"
						+ "|-----------|------------------------|\n" 
						+ "| Name      | %-22s |\n"
						+ "|-----------|------------------------|\n" 
						+ "| Email     | %-22s |\n"
						+ "|-----------|------------------------|\n" 
						+ "| Contact   | %-22s |\n"
						+ "|-----------|------------------------|\n" 
						+ "| Address   | %-22s |\n"
						+ "|-----------|------------------------|\n" 
						+ "| Balance   | RM %-19.2f |\n"
						+ "|-----------|------------------------|\n" 
						+ "| Status    | %-22s |\n"
						+ "|-----------|------------------------|\n" 
						+ "| Employee  | %-22d |\n"
						+ "|-----------|------------------------|\n" 
						+ "| Warehouse | %-22d |\n"
						+ "|___________|________________________|\n\n",
				super.getId(), (super.getComName() == null) ? " " : super.getComName(),
				(super.getComEmail() == null) ? " " : super.getComEmail(),
				(super.getComContact() == null) ? " " : super.getComContact(),
				(super.getAddress() == null) ? " " : super.getAddress(), getBalance(),
				statusString(), empCount, wHouseCount);
	}
	
	/*
	 * This method cannot used directly to set the balance It can only set the
	 * balance by adding them
	 */
	public void addBalance(double amount) {
		balance += amount;
	}
	
	/*
	 * This method is used to deduct the balance of the branch Eg. when the employee
	 * purchased product from supplier this method would be used
	 */
	public void deductBalance(double amount) {
		balance -= amount;
	}

	/*
	 * This method used to add the employee into the branch It will remove the
	 * employee from other branch before it add the employee to the branch (If the
	 * employee exists in other branch)
	 */
	public void addEmp(Employee emp) {
		removeEmp(emp);
		employees[empCount] = emp;
		empCount++;
	}
	
	/*
	 * This method used to remove the employee It is static method so it will find
	 * from all the Branches and remove the employee if it exists in the branch
	 */
	public void removeEmp(Employee emp) {
		if (containsEmp(emp)) 
			shiftEmp(emp);
	}
	
	/*
	 * This method will check from the employees in the branch
	 * and then check for the emp does it equals to any employees
	 * if equals then return true
	 */
	private boolean containsEmp(Employee emp) {
		
		for(int i = 0; i < empCount; i++) {
			if(employees[i].equals(emp))
				return true;
		}
		return false;
	}
	
	/*
	 * This method is used to rearrange the index of
	 * the employees array so when removed the employee from branch
	 * the other employee will replace its index by shifting up
	 */
	private void shiftEmp(Employee emp) {
		
		int shiftIndex = -1;
		for(int i = 0; i < empCount; i++) {
			if(employees[i].equals(emp)) {
				shiftIndex = i;
				break;
			}
		}
		if(shiftIndex != -1) {
			for(int i = shiftIndex; i < empCount; i++) 
				employees[i] = employees[i+1];	
		}
		empCount--;
	}
	
	/*
	 * This method used to clear all items in the warehouse in this Branch
	 * And also it will store those cleared items into current Branch storeroom
	 */
	public void clearWhouse(Warehouse warehouse) {
		
		for(Warehouse wHouse : warehouses) {
			if(wHouse.equals(warehouse)) {
				wHouse.clear(storeroom); //clear all items into Storeroom
			}
		}
	}
	
	/*
	 * This method is used to find the Warehouse ID Eg. A,B,C... 
	 * It will return the warehouse that match the Warehouse ID which match within the Branch's Warehouses
	 * within the passed Warehouses Array
	 */
	public Warehouse findId(char wHouseId) {
		wHouseId = Character.toUpperCase(wHouseId);
		for(Warehouse wHouse : warehouses) {
			if(wHouse == null) break;
			if(wHouse.getWarehouseId() == wHouseId)
				return wHouse;
		}
		return null;
	}
	
	/*
	 * This method used to show all the employee that passed with the parameters
	 * This method used when to show the employees in the specific branch
	 * It show only the employee ID, Name, Department, Salary & Status
	 */
	public void showAllEmp() {
		
		System.out.print(
				  "  __________________________________________________________________\n"
				+ " |  ID  |        Name        |  Department  |   Salary   |  Status  |\n"
				+ " |------|--------------------|--------------|------------|----------|\n");
		for (int i = 0; i < empCount ; i++)
			System.out.printf(" | %-4d | %-18s | %-12s | RM%-8.2f | %-8s |\n", employees[i].getId(), employees[i].getName(), 
					(employees[i].getDepartment()!=null)?employees[i].getDepartment().getTitle():"-", employees[i].getSalary(), employees[i].statusString());

		System.out.println(" |______|____________________|______________|____________|__________|\n");
	}
	
	/* This method is used to add warehouse in the Branch */
	public void addWarehouse(Warehouse warehouse) {
		if(wHouseCount >= 10) {
			System.out.println("[Reached MAX warehouse to add]");
			return;
		}
		warehouses[wHouseCount] = warehouse;
		wHouseCount++;
	}
	
	/*
	 * This method will show the warehouses in the branch
	 * included warehouse ID, description, number of floor and status
	 */
	public void showWarehouses() {
		
		if(wHouseCount > 0) {
			System.out.print( "  ________________________________________________________________  \n"
							+ " |                          Warehouse(s)                          | \n"
							+ " |________________________________________________________________| \n"
							+ " | ID | Description                            | Floor |  Status  | \n"
							+ " |----|----------------------------------------|-------|----------| \n");
			for(Warehouse wHouse : warehouses) {
				if(wHouse!=null)
					System.out.printf(" | %-2c | %-38s | %-5d |  %-7s | \n", wHouse.getWarehouseId(), wHouse.getDesc(), wHouse.getFloorCount(), (wHouse.getStatus())?"Valid":"Locked");
			}
			System.out.println(" |____|________________________________________|_______|__________| \n");
		}else {
			System.out.println();
			System.out.println("---------------------------");
			System.out.println("  (ZERO Warehouse Record)  ");
			System.out.println("---------------------------\n\n");
		}
	}
	
	/*
	 * This method will based on the items and check from each warehouses
	 * each floors in each warehouse and each racks in each floor and each
	 * item in each rack and get the total quantity of the items for each item 
	 * in current branch
	 */
	public void showInventoryLevel(Item[] items) {
		
		int[] quantity = new int[items.length];

		for(int x = 0; x < items.length; x++) {
			quantity[x] += storeroom.getItemCount(items[x]);
			for(int i = 0; i < wHouseCount; i++) {
				quantity[x] += warehouses[i].getItemCount(items[x]);
			}                                    
		}
		
		Item.showItemQty(items,quantity);
	}
	
	/*
	 * This method used when purchase items from supplier
	 * and the supplier sent the item to the branch and those items
	 * will kept in storeroom so that the warehouse employee
	 * can manage the items to the rack
	 */
	public void addStoreroomItem(Item item, int quantity) {
		storeroom.addItem(item, quantity);
	}
	

	
	@Override
	public String toString() {
		return super.toString() + 
				"Balance : RM " + balance +
				"\nStatus: " + statusString() + 
				"\nStoreroom ID: " + storeroom.getId() + 
				"\nEmployee Count: " + empCount + 
				"\nWarehouse Count: " + wHouseCount + "\n";
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Branch) {
			Branch branch = (Branch)obj;
			if(this.getId() == branch.getId())
				return true;
		}
		return false;
	}

}
