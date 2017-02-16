package domain;

import java.util.Date;

public class Incident {

	private Integer id;
	private String message;
	private Date date;
	private Integer fileId;
	
	
	public Incident(Integer id, String message, Date date, Integer fileId) {
		super();
		this.id = id;
		this.message = message;
		this.date = date;
		this.fileId = fileId;
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
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	
}
