package com.car.entity;

import java.io.Serializable;

public class PaymentRequest{
	
	public String channel; // ֧��ͨ��
	public int amount; // ֧�����
	
	

	public PaymentRequest() {
		super();
		// TODO Auto-generated constructor stub
	}



	public PaymentRequest(String channel, int amount) {
		this.channel = channel;
		this.amount = amount;
	}



	
	
	
}
