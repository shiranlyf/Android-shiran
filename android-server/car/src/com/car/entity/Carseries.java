package com.car.entity;

/**
 * Carseries entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Carseries implements java.io.Serializable {

	// Fields

	public Integer cid;
	public Integer id;
	public String dealerPrice;
	public String name;
	public String aliasImg;
	public String aliasName;

	// Constructors

	/** default constructor */
	public Carseries() {
	}

	/** full constructor */
	public Carseries(Integer id, String dealerPrice, String name,
			String aliasImg, String aliasName) {
		this.id = id;
		this.dealerPrice = dealerPrice;
		this.name = name;
		this.aliasImg = aliasImg;
		this.aliasName = aliasName;
	}

	// Property accessors

	public Integer getCid() {
		return this.cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDealerPrice() {
		return this.dealerPrice;
	}

	public void setDealerPrice(String dealerPrice) {
		this.dealerPrice = dealerPrice;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAliasImg() {
		return this.aliasImg;
	}

	public void setAliasImg(String aliasImg) {
		this.aliasImg = aliasImg;
	}

	public String getAliasName() {
		return this.aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

}