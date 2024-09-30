package employee;

import validation.Validation;

public abstract class Person {

	//Attributes
	protected String name;
	protected String email;
	protected String contact;
	
	//Constructor
	protected Person() {this(" "," "," ");}
	protected Person(String name, String email, String contact) {
		this.name = name;
		this.email = email;
		this.contact = contact;
	}
	
	//--- Setter ---
	public void setName(String name) {this.name = name;}
	public void setEmail(String email) {this.email = email;}
	public void setContact(String contact){this.contact = contact;}
	
	//--- Getter ---
	public String getName() {return this.name;}
	public String getEmail() {return this.email;}
	public String getContact() {return this.contact;}
	
	public boolean inputName(String name) {

		if(Validation.validateName(name)) {
			this.name = name;
			return true;
		}else
			System.out.println("[Please input a valid name (Only alphabets & length between 3 ~ 20)]");
		return false;
	}
	
	public boolean inputEmail(String email) {
		
		if(Validation.validateEmail(email)) {
				this.email = email;
				return true;
		}else
			System.out.println("[Please input a valid email (Must contains '@' & '.')]");
		return false;
	}
	
	
	public boolean inputContact(String contact) {

		if(Validation.validateContact(contact)) {
			this.contact = contact;
			return true;
		}else
			System.out.println("[Please input a valid contact (10 or 11 numbers)]");
		return false;
	}
	
	@Override
	public String toString() {
		return "Name: " + name +
				"\nEmail: " + email +
				"\nContact: " + contact + "\n";
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Person) {
			if(((Person)obj).name.equals(this.name) &&
			   ((Person)obj).email.equals(this.email) &&
			   ((Person)obj).contact.equals(this.contact)) 
				return true;
		}
		return false;
	}
}
