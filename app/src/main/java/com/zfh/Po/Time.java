package com.zfh.Po;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Time entity. @author MyEclipse Persistence Tools
 */

public class Time implements java.io.Serializable {
	private String employeeid;
	private String date;
	private String am;
	private String pm;
	private String xingqi;

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAm() {
		return am;
	}

	public void setAm(String am) {
		this.am = am;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getXingqi() {
		return xingqi;
	}

	public void setXingqi(String xingqi) {
		this.xingqi = xingqi;
	}

}