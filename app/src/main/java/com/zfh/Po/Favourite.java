package com.zfh.Po;

/**
 * Favourite entity. @author MyEclipse Persistence Tools
 */

public class Favourite implements java.io.Serializable {
	private String emploimage;
	private String keshi;
	private String employeename;
	private String employeeid;

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getEmploimage() {
		return emploimage;
	}

	public void setEmploimage(String emploimage) {
		this.emploimage = emploimage;
	}

	public String getKeshi() {
		return keshi;
	}

	public void setKeshi(String keshi) {
		this.keshi = keshi;
	}

	public String getEmployeename() {
		return employeename;
	}

	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}
}