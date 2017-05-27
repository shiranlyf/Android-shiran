package com.car.entity;

/**
 * Address entity. @author MyEclipse Persistence Tools
 */

public class Address implements java.io.Serializable {

	// Fields

	public Integer addressId;
	public User user;
	public String addressName;
	public String addressTel;
	public String addressProvice;
	public String addressDetail;
	public Integer addressCode;
	public String peopleSex;  //1表示男 0表示女
	public String preparePhone;
	public String addressCity;
	

	// Constructors

	/** default constructor */
	public Address() {
	}

	/** full constructor */
	public Address(User user, String addressName, String addressTel,
			String addressProvice, String addressDetail, Integer addressCode
			, String peopleSex, String preparePhone, String addressCity) {
		this.user = user;
		this.addressName = addressName;
		this.addressTel = addressTel;
		this.addressProvice = addressProvice;
		this.addressDetail = addressDetail;
		this.addressCode = addressCode;
		this.peopleSex = peopleSex; 
		this.preparePhone = preparePhone;
		this.addressCity = addressCity;
	}

	// Property accessors

	public Integer getAddressId() {
		return this.addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAddressName() {
		return this.addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getAddressTel() {
		return this.addressTel;
	}

	public void setAddressTel(String addressTel) {
		this.addressTel = addressTel;
	}

	public String getAddressProvice() {
		return this.addressProvice;
	}

	public void setAddressProvice(String addressProvice) {
		this.addressProvice = addressProvice;
	}

	public String getAddressDetail() {
		return this.addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public Integer getAddressCode() {
		return this.addressCode;
	}

	public void setAddressCode(Integer addressCode) {
		this.addressCode = addressCode;
	}

	public String getPeopleSex() {
		return peopleSex;
	}

	public void setPeopleSex(String peopleSex) {
		this.peopleSex = peopleSex;
	}

	public String getPreparePhone() {
		return preparePhone;
	}

	public void setPreparePhone(String preparePhone) {
		this.preparePhone = preparePhone;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

}