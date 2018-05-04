package com.zfh.Po;

import java.util.HashSet;
import java.util.Set;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private String personId;
	private String password;
	private String sex;
	private String phone;
	private String address;
	private String name;
	private Set yuyues = new HashSet(0);
	private Set favourites = new HashSet(0);

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String personId) {
		this.personId = personId;
	}

	/** full constructor */
	public User(String personId, String password, String sex, String phone,
			String address, String name, Set yuyues, Set favourites) {
		this.personId = personId;
		this.password = password;
		this.sex = sex;
		this.phone = phone;
		this.address = address;
		this.name = name;
		this.yuyues = yuyues;
		this.favourites = favourites;
	}

	// Property accessors

	public String getPersonId() {
		return this.personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getYuyues() {
		return this.yuyues;
	}

	public void setYuyues(Set yuyues) {
		this.yuyues = yuyues;
	}

	public Set getFavourites() {
		return this.favourites;
	}

	public void setFavourites(Set favourites) {
		this.favourites = favourites;
	}

}