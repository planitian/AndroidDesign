package com.zfh.Po;

/**
 * Empjian entity. @author MyEclipse Persistence Tools
 */

public class Empjian implements java.io.Serializable {

	// Fields

	private String id;
	private Employee employee;
	private String jianjie;

	// Constructors

	/** default constructor */
	public Empjian() {
	}

	/** full constructor */
	public Empjian(Employee employee, String jianjie) {
		this.employee = employee;
		this.jianjie = jianjie;
	}

	// Property accessors

	

	public Employee getEmployee() {
		return this.employee;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getJianjie() {
		return this.jianjie;
	}

	public void setJianjie(String jianjie) {
		this.jianjie = jianjie;
	}

}