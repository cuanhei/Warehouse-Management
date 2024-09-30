package report;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Report {
	
	protected String title;
	private Date date;
	
	protected Report() {this(" ");}
	protected Report(String title) {
		this.title = title;
		this.date = new Date();
	}
	
	public String getTitle() {return title;}
	public String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(date);
		return formattedDate;
	}
	
	
	public abstract void drawReport();
	
	@Override
	public String toString() {
		return String.format(" ====================================================================\n"
							+" %-68s\n"
							+" ====================================================================\n"
							+ " Date Generated: %s\n"
							+ "\n", title,getDate());
	}
	
	//Report is not available to check does it equals
	@Override
	public boolean equals(Object obj) {
		return false;
	}
}
