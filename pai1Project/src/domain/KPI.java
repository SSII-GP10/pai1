package domain;

import java.util.Date;

public class KPI {
	private Integer id;
	private Double ratio;
	private Integer positives;
	private Integer negatives;
	private Date ReportDate;
	
	
	public KPI(Integer id, Double ratio, Integer positives, Integer negatives, Date reportDate) {
		super();
		this.id = id;
		this.ratio = ratio;
		this.positives = positives;
		this.negatives = negatives;
		ReportDate = reportDate;
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
		return ReportDate;
	}
	public void setReportDate(Date reportDate) {
		ReportDate = reportDate;
	}
	
}
