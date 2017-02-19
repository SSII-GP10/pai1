package domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Incident {

	private Integer id;
	private String message;
	private Date date;
	private String file;
	
	public Incident(Integer id, String message, Date date, String file) {
		this.id = id;
		this.message = message;
		this.date = date;
		this.file = file;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getFile() {
		return file;
	}
	
	public void setFile(String file) {
		this.file = file;
	}

	public String toString(){
		String formatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		return "File: " + file + " | Message: " + message + " | Date: " + formatted; 
	}
	
}
