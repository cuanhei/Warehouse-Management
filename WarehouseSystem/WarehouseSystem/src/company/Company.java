package company;

import validation.Validation;

public abstract class Company {
	
	//Attributes
	private   final int ID; //ID is unchangeable
	protected String companyName;
	protected String companyEmail;
	protected String companyContact;
	protected String address;
	
	
	//Constructors
	protected Company() {
		this(0," "," "," "," ");
	}
	protected Company(int id, String companyName, String email, String contact, String address){
		
		this.ID = id;
		this.companyName = companyName;
		this.companyEmail = email;
		this.companyContact = contact;
		this.address = address;
	}
	
	
	//Getter & Setter 
	public void setComName(String companyName) {this.companyName = companyName;}
	public void setComEmail(String email) {this.companyEmail = email;}
	public void setComContact(String contact) {this.companyContact = contact;}
	public void setAddress(String address) {this.address = address;}
	public int getId() {return ID;}
	public String getComName() {return companyName;}
	public String getComEmail() {return companyEmail;}
	public String getComContact() {return companyContact;}
	public String getAddress() {return address;}
	
	
	
	public boolean inputAddress(String comAddress) {
		if(Validation.validateAddress(comAddress)) {
			setAddress(comAddress);
			return true;
		}
		return false;
	}
	public boolean inputContact(String comContact) {
		if(Validation.validateContact(comContact)) {
			setComContact(comContact);
			return true;
		}
		return false;
	}
	public boolean inputEmail(String comEmail) {
		if(Validation.validateEmail(comEmail)) {
			setComEmail(comEmail);
			return true;
		}
		return false;
	}
	public boolean inputName(String comName) {
		if(Validation.validateComName(comName)) {
			setComName(comName);
			return true;
		}
		return false;
	}
	
	
	@Override
	public String toString() {
		
		return "Company Name: " + companyName +
			 "\nEmail       : " + companyEmail + 
			 "\nContact     : " + companyContact + 
			 "\nAddress     : " + address + "\n";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Company) {
			Company com = (Company)obj;
			if(com.ID == this.ID &&
			   com.companyName.equals(this.companyName) && 
			   com.companyEmail.equals(this.companyEmail) && 
			   com.companyContact.equals(this.companyContact)) {
				return true;
			}
		}
		return false;
	}
}
