package com.car.entity;

/**
 * Collect entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Collect implements java.io.Serializable {

	// Fields

	public Integer cid;
	public User user;
	public String logo;

	// Constructors

	/** default constructor */
	public Collect() {
	}

	/** full constructor */
	public Collect(User user, String logo) {
		this.user = user;
		this.logo = logo;
	}

	// Property accessors

	public Integer getCid() {
		return this.cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

}