package com.car.entity;

import java.io.Serializable;

public class PaymentRequest{
	
	public String channel; // 支付通道
	public int amount; // 支付金额
	
	

	public PaymentRequest() {
		super();
		// TODO Auto-generated constructor stub
	}



	public PaymentRequest(String channel, int amount) {
		this.channel = channel;
		this.amount = amount;
	}



	
	
	
}
