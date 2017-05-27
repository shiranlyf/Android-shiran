package com.car.entity;

import java.util.Date;

/**
 * Order entity. @author MyEclipse Persistence Tools
 */

public class Order implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Integer goodId;
	private Integer addrId;
	private Date orderTime;
	private Integer ispay;   //0表示的未支付  1表示支付
	private Integer payWay;  //表示的是支付的方式  0表示的是货到付款
	private Integer userId;  //这个是用户id
	private String order_show_info;  //保存的时候订单显示拼接的字符串

	// Constructors

	/** default constructor */
	public Order() {
	}

	/** full constructor */
	public Order(Integer goodId, Integer addrId, Date orderTime, Integer ispay,
			Integer payWay, Integer userId, String order_show_info) {
		this.goodId = goodId;
		this.addrId = addrId;
		this.orderTime = orderTime;
		this.ispay = ispay;
		this.payWay = payWay;
		this.userId = userId;
		this.order_show_info = order_show_info;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getGoodId() {
		return this.goodId;
	}

	public String getOrder_show_info() {
		return order_show_info;
	}

	public void setOrder_show_info(String order_show_info) {
		this.order_show_info = order_show_info;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}

	public Integer getAddrId() {
		return this.addrId;
	}

	public void setAddrId(Integer addrId) {
		this.addrId = addrId;
	}

	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getIspay() {
		return this.ispay;
	}

	public void setIspay(Integer ispay) {
		this.ispay = ispay;
	}

	public Integer getPayWay() {
		return this.payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

}