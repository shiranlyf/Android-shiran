package com.car.entity;

import java.util.Date;

/**
 * Order entity. @author MyEclipse Persistence Tools
 */

public class Order implements java.io.Serializable {

	// Fields

	public Integer oid;
	public Integer goodId;
	public Integer addrId;
	public Date orderTime;
	public Integer ispay;   //0��ʾ��δ֧��  1��ʾ֧��
	public Integer payWay;  //��ʾ����֧���ķ�ʽ  0��ʾ���ǻ�������
	public Integer userId;  //������û�id
	public String order_show_info;  //�����ʱ�򶩵���ʾƴ�ӵ��ַ�

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