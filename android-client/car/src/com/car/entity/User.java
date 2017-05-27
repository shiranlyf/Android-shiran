package com.car.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * User entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields
	public Integer uid;
	public String  name;
	public String loginPwd;
	public String payPwd;
	public String tel;
	public  Set addresses = new HashSet(0);
	public  Set collects = new HashSet(0);
	
	public Integer isadmin; //判断是否是管理员
	
	public User() {
		
	}

	public User(Integer uid, String name, String loginPwd, String payPwd,
			String tel, Set addresses, Set collects, Integer isadmin) {
		super();
		this.uid = uid;
		this.name = name;
		this.loginPwd = loginPwd;
		this.payPwd = payPwd;
		this.tel = tel;
		this.collects = collects;
		this.addresses = addresses;
		this.isadmin = isadmin;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getPayPwd() {
		return payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Set getCollects() {
		return collects;
	}

	public void setCollects(Set collects) {
		this.collects = collects;
	}

	public Set getAddresses() {
		return addresses;
	}

	public void setAddresses(Set addresses) {
		this.addresses = addresses;
	}

	public Integer getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(Integer isadmin) {
		this.isadmin = isadmin;
	}
	
	
	
}