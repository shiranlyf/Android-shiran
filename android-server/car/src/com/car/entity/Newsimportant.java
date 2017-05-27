package com.car.entity;

import java.io.Serializable;

/**
 * Newsimportant entity.
 * 这是新闻模块的实体
 * @author MyEclipse Persistence Tools
 */

public class Newsimportant implements Serializable{

	// Fields

	public Integer id;
	public String nid;
	public String coverImage;
	public String title;
	public String type;
	public String cardesc;
	public String car_price;
	public String car_old_price;
	public Integer shopid;
	public Shop   shop;  //用来保存商家的对象
	public String car_detail; //本单详情
	public Integer city_id;

	// Constructors

	public String getCardesc() {
		return cardesc;
	}

	public void setCardesc(String cardesc) {
		this.cardesc = cardesc;
	}

	/** default constructor */
	public Newsimportant() {
	}

	/** full constructor */
	public Newsimportant(String nid, String coverImage, String title,
			String type,String cardesc, String car_price, String car_old_price,
			Integer shopid, Shop shop, String  car_detail
			,Integer  city_id ) {
		this.nid = nid;
		this.coverImage = coverImage;
		this.title = title;
		this.type = type;
		this.cardesc = cardesc;
		this.shop = shop;
		this.car_detail = car_detail;
		this.city_id = city_id;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNid() {
		return this.nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getCoverImage() {
		return this.coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCar_price() {
		return car_price;
	}

	public void setCar_price(String car_price) {
		this.car_price = car_price;
	}

	public String getCar_old_price() {
		return car_old_price;
	}

	public void setCar_old_price(String car_old_price) {
		this.car_old_price = car_old_price;
	}

	public Integer getShopid() {
		return shopid;
	}

	public void setShopid(Integer shopid) {
		this.shopid = shopid;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public String getCar_detail() {
		return car_detail;
	}

	public void setCar_detail(String car_detail) {
		this.car_detail = car_detail;
	}

	public Integer getCity_id() {
		return city_id;
	}

	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}

}