package domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KPI {
	private Integer id;
	private Double ratio;
	private Integer positives;
	private Integer negatives;
	private Date reportDate;
	
	
	public KPI(Integer id, Double ratio, Integer positives, Integer negatives, Date reportDate){
		this.id = id;
		this.ratio = ratio;
		this.positives = positives;
		this.negatives = negatives;
		this.reportDate = reportDate;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Double getRatio() {
		return ratio;
	}
	
	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}
	
	public Integer getPositives() {
		return positives;
	}
	
	public void setPositives(Integer positives) {
		this.positives = positives;
	}
	
	public Integer getNegatives() {
		return negatives;
	}
	
	public void setNegatives(Integer negatives) {
		this.negatives = negatives;
	}
	
	public Date getReportDate() {
		return reportDate;
	}
	
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	
	public String toString(){
		String formatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(reportDate);
		return "Ratio: " + ratio + " | Positives: " + positives + " | Negatives: " + negatives
				+ " | Report: " + formatted; 
	}
	
}
