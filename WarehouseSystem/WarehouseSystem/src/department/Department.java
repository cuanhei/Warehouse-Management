package department;

public abstract class Department {
	//Attributes
	protected String title;
	
	//Constants
	protected final static String[] BASIC_MENU = {"Profile", "Log Out"};
	
	
	//Constructors
	protected Department() {this(" ");}
	protected Department(String title){this.title = title;}

	
	//Getter
	public String getTitle() {return title;}
	
	
	
	  ///////////////////////////////////////////
	 ////////////// Static Methods /////////////
	///////////////////////////////////////////
	
	//This method shows all the department with index
	public static void showAllDepartment(Department[] departments) {
		for(int i = 0; i < departments.length ; i++) {
			System.out.printf("[%d] %s\n", i+1, departments[i]);
		}
	}
	
	//Search the specific department based on the title, if not found will return null
	public static Department searchTitle(String title, Department[] departments) {
		for(Department dep : departments) {
			if(dep.title.equals(title))
				return dep;
		}
		return null;
	}

	
	
	
	
	  ///////////////////////////////////////////
	 ///////////// Instance Methods ////////////
	///////////////////////////////////////////
	
	//Each department have their own count of employee
	abstract public void remDepEmp();
	abstract public void addDepEmp();
	abstract public String[] getMenu();
	
	@Override
	public String toString() {
		return  title;
	}
	
	@Override
	public boolean equals(Object obj) {
			
			Department dep;
			if(obj instanceof Department) {
				 dep = (Department)obj;
				if(this.getTitle() == dep.getTitle())
					return true;
			}	
			return false;
		}
}
