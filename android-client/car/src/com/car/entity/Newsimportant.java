package com.car.entity;

import java.io.Serializable;

import android.R.string;

/**
 * Newsimportant entity.
 *  新闻模块-咨询
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
	public Shop  shop;
	public String car_detail; //本单详情
	public Integer city_id; //城市id

	// Constructors

	/** default constructor */
	public Newsimportant() {
	}

	/** full constructor */
	public Newsimportant(String nid, String coverImage, String title,
			String type, String cardesc, String car_price, String  car_old_price, Integer shopid,
			Shop shop, String car_detail
			, Integer city_id) {
		this.nid = nid;
		this.coverImage = coverImage;
		this.title = title;
		this.type = type;
		this.cardesc = cardesc;
		this.car_price = car_price;
		this.car_old_price = car_old_price;
		this.shopid = shopid;
		this.shop = shop;
		this.car_detail = car_detail;
		this.city_id = city_id;
	}

	// Property accessors

	public String getCar_detail() {
		return car_detail;
	}

	public void setCar_detail(String car_detail) {
		this.car_detail = car_detail;
	}

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

	public String getCardesc() {
		return cardesc;
	}

	public void setCardesc(String cardesc) {
		this.cardesc = cardesc;
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

	public Integer getCity_id() {
		return city_id;
	}

	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}

}