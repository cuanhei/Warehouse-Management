package employee;

import company.Branch;
import department.Department;
import item.Item;
import validation.Validation;

public class Employee extends Person{
	
	//Attributes
	private final int ID; // The ID is fixed
	private String password;
	private Department department;
	private double salary;
	private int status;// [0] Pending [1] Valid [2] Invalid
	private Item holdingItem;
	private int holdingQty;
	private static int createdEmp = 0;
	
	
	
	
	//Constructors
	public Employee() {this(null, null, null, null, null, 0.0, 0);}
	public Employee(String name, String email, String contact,
					 String password, Department department, double salary, int status) {
		super(name, email, contact);
		this.ID = ++createdEmp; // The id is automatic generated
		this.password = password;
		this.department = department;
		this.salary = salary;
		this.status = status;
		this.holdingItem = null;
		this.holdingQty = 0;
		if(this.department!=null)
			this.department.addDepEmp();
	}
	
	
	
	
	//Getter & Setter
	public void setPassword(String password) {this.password = password;}
	public void setDepartment(Department department) {this.department = department;}
	public void setSalary(double salary) {this.salary = salary;}
	public void setStatus(int status) {this.status = status;}
	public void setHoldingItem(Item item) {this.holdingItem = item;}
	public void setHoldingQty(int quantity) {this.holdingQty = quantity;}
	public int getId() {return ID;}
	public String getPassword() {return password;}
	public Department getDepartment() {return department;}
	public double getSalary() {return salary;}
	public int getStatus() {return status;}
	public Item getHoldingItem() {return holdingItem;}
	public int getHoldingQty() {return holdingQty;}
	public static int getCreatedEmp() {return createdEmp;}

	
	/*
	 * This method is used to check does the passed email and password is matching
	 * any employee account in the employees Once consists and matched it will check
	 * does the status is valid if the status is valid, it will update the loggedEmp
	 * to the account and return true else it will return false
	 */
	public static Employee validLogIn(Employee[] employees, String email, String password) {
	    email = email.trim(); // Trim the email input
	    for (int i = 0; i < createdEmp ; i++) {
	        if (employees[i].getEmail().equals(email) && 
	        	employees[i].getPassword().equals(password)) {
	            	return employees[i];
	        }
	    }
	    return null;
	}
	
	public static Employee validLogIn(Employee[] employees, int id, String password) {
	    for (int i = 0; i < createdEmp ; i++) {
	        if (employees[i].getId() == id && 
	        	employees[i].getPassword().equals(password)) {
	            
	            	return employees[i];
	        }
	    }
	    return null;
	}
	
	/*
	 *  This method show the details of the employees that passed through the parameter
	 *  With details of their ID, Name, Branch, Department
	 */
	public static void showAllEmp(Branch[] branches, Employee[] employees) {

		System.out.print(
				  "  _____________________________________________________ \n"
				+ " |  ID  |        Name        |  Branch  |  Department  |\n"
				+ " |------|--------------------|----------|--------------|\n");
		for (Employee emp : employees) {
			if(emp == null)
				break;
			System.out.printf(" | %-4d | %-18s | %-8s | %-12s |\n", emp.getId(), emp.getName(), (Branch.searchEmp(branches,emp)!=null)?Branch.searchEmp(branches,emp).getId():"-",
					(emp.department!=null)?emp.department.getTitle():"-");
		}
		System.out.println(" |______|____________________|__________|______________|\n");
	}
	
	public static Employee findId(Employee[] employees, int id) {
		
		for(Employee emp: employees) {
			if(emp == null) break;
			if(emp.getId() == id)
				return emp;
		}
		return null;
	}
	
	public static Employee findId(Employee[] employees,String id) {
		return findId(employees,Integer.parseInt(id));
	}
	
 	public static void cancelRegisterEmp() {
		Employee.createdEmp--;
	}
	
  
	  ///////////////////////////////////////////
	 ///////////// Instance Methods ////////////
	///////////////////////////////////////////
	
	//This method show each instance employee details
	public void showDetails(Branch branch) {
		System.out.printf(
						  " ____________________________________ \n" + 
						  "|          Employee Details          |\n"
						+ "|------------------------------------|\n" + 
						  "|ID        | %-24d|\n"
						+ "|----------|-------------------------|\n" + 
						  "|Name      | %-24s|\n"
						+ "|----------|-------------------------|\n" + 
						  "|Email     | %-24s|\n"
						+ "|----------|-------------------------|\n" + 
						  "|Contact   | %-24s|\n"
						+ "|----------|-------------------------|\n" + 
						  "|Password  | %-24s|\n"
						+ "|----------|-------------------------|\n" + 
						  "|Branch    | %-24s|\n"
						+ "|----------|-------------------------|\n" + 
						  "|Department| %-24s|\n"
						+ "|----------|-------------------------|\n" + 
						  "|Salary    | %-24s|\n"
						+ "|----------|-------------------------|\n" + 
						  "|Status    | %-24s|\n"
						+ "|__________|_________________________|\n\n",
				getId(), 
				(getName() == null) ? "-" : getName(), 
				(getEmail() == null) ? "-" : getEmail(),
				(getContact() == null) ? "-" : getContact(), 
				(getPassword() == null) ? "-" : getPassword(), 
				(branch == null)?"-":String.valueOf(branch.getId()),
				(department == null) ? "-" : department.getTitle(), 
				(getSalary() == 0) ? "-" : ("RM " + getSalary()),
				statusString());
	}
	
	
	public boolean inputDetails(int index, String input, Employee[] employees, Department[] departments, Branch[] branches) {
		
		boolean validInput = false;
		switch (index) {
			case 0: //Name
				validInput = inputName(input); 
				break;
			case 1: //Email
				validInput = inputEmail(employees,input); 
				break;
			case 2: //Contact
				validInput = inputContact(input);
				break;
			case 3://Password
				validInput = inputPassword(input);
				break;
			case 4://Department
				validInput = inputDepartment(departments,input);
				break;
			case 5://Branch
				validInput = selectBranch(branches,input);
				break;
			case 6://Salary
				validInput = inputSalary(input);
				break;
			default:
				System.out.println("[Invalid Input]");
		}
		if(!validInput) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return validInput;
	}
	
	public void showHoldingItemAndQty() {
		if(holdingItem != null) {
		System.out.printf(" _____________________ \n"
						+ "| Item ID  | %-9d|\n"
						+ "|==========|==========|\n"
						+ "| Category | %-9s|\n"
						+ "|==========|==========|\n"
						+ "| Quantity | %-9d|\n"
						+ "|__________|__________|\n\n", holdingItem.getId(), holdingItem.getCategory(),holdingQty);
		}else {
			System.out.println("[You are currently holding nothing]\n");
		}
	}
	public boolean inputEmail(Employee[] employees,String email) {
		
		if(Validation.validateEmail(email)) {
			if(!duplicatedEmail(employees,email)) {
				this.email = email;
				return true;
			}else 
				System.out.println("[This email already registered in other Employee account, please try other email]");
		}else
			System.out.println("[Please input a valid email (Must contains '@' & '.')]");
		return false;
	}
	
	public boolean duplicatedEmail(Employee[] employees, String email) {
		for(Employee emp : employees) {
			if(emp == null)
				break;
			if(emp.email.equals(email))
				return true;
		}
		return false;
	}
	
	
	public boolean inputPassword(String password) {
		
		if(Validation.validPassword(password)) {
			this.password = password;
			return true;
		}else
			System.out.println("[Invalid Password Format (Length must between 8 ~ 16 and at least 1 special character)]");
		return false;
	}
	
	public boolean inputDepartment(Department[] departments, String index) {
			
		try {
			if(this.department != null)
				this.department.remDepEmp();
			
			int departmentIndex = Integer.parseInt(index);
			if(departmentIndex > 0 && departmentIndex <= departments.length) {
				setDepartment(departments[departmentIndex-1]);
				this.department.addDepEmp();
				return true;
			}
			else 
				System.out.println("[Please input the correct INDEX of the department]");
		} catch (NumberFormatException e) {
			System.out.println("[Invalid Input, please input an Integer value]");
		}
		return false;
	}
	
	private boolean selectBranch(Branch[] branches, String branchIdStr) {

		try {
			int branchId = Integer.parseInt(branchIdStr);
			for(Branch branch : branches) {
				if(branch == null) {
					break;
				}
				if(branch.getId() == branchId) {
					branch.addEmp(this);
					return true;
				}
			}
			System.out.println("[Invalid Branch ID]");
		} catch (NumberFormatException e) {
			System.out.println("[Invalid Input, please input an Integer value]");
		}
		return false;
	}
	
	private boolean inputSalary(String salaryStr) {

		try {
			double salary = Double.parseDouble(salaryStr);
			if(salary >= 1500) {
				this.salary = salary;
				return true;
			}else 
				System.out.println("[Please input a valid Salary (Minimum RM 1500.00)]");
		} catch (NumberFormatException e) {
			System.out.println("[Invalid Input, please input a Double value]");
		}
		return false;
	}
	
	public String statusString() {
		if(status == 0)
			return "Pending";
		else if(status == 1)
			return "Valid";
		else
			return "Invalid";
	}
	
	public boolean inputStatus(int status, Branch[] branches) {
		
		try {
			if(status <0 || status > 2)
				System.out.println("[Please select the status based on the INDEX]");
			else {
				if(status == 1) {
					if(this.department == null 		||
					   this.salary <1500 	   		||
					   Branch.searchEmp(branches,this) == null) {
						System.out.println("[Only Department, Branch, Salary has been setted can only set the account valid]");
					}else {
						this.status = status;
						return true;
					}
				}else {
					this.status = status;
					return true;
				}
			}
		} catch (NumberFormatException e) {
			System.out.println("[Invalid Input, please input an Integer value]");
		}
		return false;
	}
	
	public boolean checkStatus() {
		
		if(this.status == 1) 
			return true;
		else if(status == 0) 
			System.out.println("[Account is now under Pending, please be try again later.]");
		else 
			System.out.println("\u001B[31m[Oops! Account ("+ name +") is currently locked, please contact admin for more information]\u001B[0m");
		return false;
	}
	@Override
	public String toString() {
		return super.toString() + 
				"ID: " + ID + 
				"\nPassword: " + password + 
				"\nDepartment: " + (department)!= null?department.getTitle():"-" + 
				"\nSalary: " + salary + 
				"\nStatus: " + statusString() + 
				"\nHolding Item: " + holdingItem.getId() + 
				"\nHolding Quantity: " + holdingQty + "\n";
	}
	
	@Override
	public boolean equals(Object obj) {
		
		Employee emp;
		if(obj instanceof Employee) {
			emp = (Employee)obj;
			if(this.ID == emp.ID)
				return true;
		}
		return false;
	}
}
