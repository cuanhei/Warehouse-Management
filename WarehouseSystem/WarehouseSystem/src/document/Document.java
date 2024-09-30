package document;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Document {
	
	//Attributes
	private final int ID;
	protected String title;
	private Date date;
	
	//Constructor
	protected Document() {
		this(0," ");
	}
	protected Document(int id, String title) {
		
		this.ID	= id;
		this.title = title;
		this.date = new Date();
	}
	
	
	//Getter & Setter
	public int getId() {return ID;}
	public String getTitle() {return title;}
	public String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(date);
		return formattedDate;
	}
	
	
	@Override
	public String toString() {

	    return String.format(" ===================================================================\n"
	                       + "                               %-7s                              \n"
	                       + " ===================================================================\n"
	                       + "                                                     ID: %-8d\n"
	                       + "                                                     Date:%s\n", 
	                        title, ID, getDate());
	}
	@Override
	public abstract boolean equals(Object obj);
}
