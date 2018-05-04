package com.zfh.Po;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Yuyue entity. @author MyEclipse Persistence Tools
 */

public class Yuyue implements java.io.Serializable {

	// Fields

	private String yuyuema;
	private String  user;
	private String employee;
	private String time;
	private String keshi;


	public String getKeshi() {
		return keshi;
	}

	public void setKeshi(String keshi) {
		this.keshi = keshi;
	}

	private String xingqi;

	public String getYuyuema() {
		return yuyuema;
	}

	public void setYuyuema(String yuyuema) {
		this.yuyuema = yuyuema;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getXingqi() {
		return xingqi;
	}

	public void setXingqi(String xingqi) {
		this.xingqi = xingqi;
	}
}