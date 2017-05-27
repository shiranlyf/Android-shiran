package com.car.entity;

/**
 * Shop entity. @author MyEclipse Persistence Tools
 */

public class Shop implements java.io.Serializable {

	// Fields

	private Integer shopId;
	private String shopName;
	private String shopTel;
	private String shopAddress;
	private String shopArea;
	private String shopOpenTime;
	private String shopLon;
	private String shopLat;
	private String shopTrafficInfo;

	// Constructors

	/** default constructor */
	public Shop() {
	}

	/** full constructor */
	public Shop(String shopName, String shopTel, String shopAddress,
			String shopArea, String shopOpenTime, String shopLon,
			String shopLat, String shopTrafficInfo) {
		this.shopName = shopName;
		this.shopTel = shopTel;
		this.shopAddress = shopAddress;
		this.shopArea = shopArea;
		this.shopOpenTime = shopOpenTime;
		this.shopLon = shopLon;
		this.shopLat = shopLat;
		this.shopTrafficInfo = shopTrafficInfo;
	}

	// Property accessors

	public Integer getShopId() {
		return this.shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return this.shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopTel() {
		return this.shopTel;
	}

	public void setShopTel(String shopTel) {
		this.shopTel = shopTel;
	}

	public String getShopAddress() {
		return this.shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getShopArea() {
		return this.shopArea;
	}

	public void setShopArea(String shopArea) {
		this.shopArea = shopArea;
	}

	public String getShopOpenTime() {
		return this.shopOpenTime;
	}

	public void setShopOpenTime(String shopOpenTime) {
		this.shopOpenTime = shopOpenTime;
	}

	public String getShopLon() {
		return this.shopLon;
	}

	public void setShopLon(String shopLon) {
		this.shopLon = shopLon;
	}

	public String getShopLat() {
		return this.shopLat;
	}

	public void setShopLat(String shopLat) {
		this.shopLat = shopLat;
	}

	public String getShopTrafficInfo() {
		return this.shopTrafficInfo;
	}

	public void setShopTrafficInfo(String shopTrafficInfo) {
		this.shopTrafficInfo = shopTrafficInfo;
	}

}