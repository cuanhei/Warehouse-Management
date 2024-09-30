package validation;

public abstract class Validation {

	//This method validate does the name contains only alphabet and the length of the name 
	public static boolean validateName(String name){
		return (name.matches("[a-zA-Z\\s]+") &&
				name.length() >0             &&
				name.length() <20
				)?true:false;
	}
	
	//This method validate does the email contains @ and . 
	public static boolean validateEmail(String email){
		return (email.contains("@") && 
				email.contains(".") && 
				email.length()>= 5  //@ and .com is 5 characters
				)?true:false;
	}

	// This method validate does the contact length is correct 
	public static boolean validateContact(String contact){
		boolean validContact = false;
		if(contact.length() == 10 || contact.length() == 11) {
			if(contact.matches("\\d+"))
				validContact = true;
		}
		return validContact;
	}
	
	/*
	 * This method used to check does the actual parameter is in length between 8 to
	 * 16 and also does it contain any special characters If it does, it will return
	 * true else it return false
	 */
	public static boolean validPassword(String password) {
		// Check for length
		if (password.length() >= 8 && password.length() <= 16) {
			// Check for special symbols
			for (char i : password.toCharArray()) {
				if ((int) i < (int) 'A' || (int) i > (int) 'z') 
					return true;
			}
		}
		return false;
	}
	
	//This method validate does the name contains only alphabet and the length of the name 
	public static boolean validateComName(String name){
		return (name.length() >0  &&
				name.length() <20
				)?true:false;
	}

	public static boolean validateAddress(String comAddress) {
		return (comAddress.length() >0  &&
				comAddress.length() <25
				)?true:false;
	}
}
