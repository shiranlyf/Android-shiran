package com.car.servlet.alipay;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.entity.PaymentRequest;
import com.google.gson.Gson;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.Charge;


/**
 * 
 * @author LYF
 * ��ʾ����ѡ��+shift+alt+L
 *
 */
public class OrderPayServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderPayServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	          throws ServletException, IOException {
		doPost(request, response);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		Pingpp.apiKey = "sk_test_GafrHCLqnnvLmPSSq98C8iPC";
		
		//��ȡ�ͻ��˲�����amount channel �ͻ����������ķ�ʽ���������  ����Ҫ�����ķ�ʽ���ж�ȡ  
		ServletInputStream in = request.getInputStream();
		byte[] bytes = new byte[512];
		int len = -1;
		StringBuffer  buf = new StringBuffer();
		while ((len = in.read(bytes)) != -1) {
			 //bytes - Ҫ����Ϊ�ַ����ֽ�
			 //offset - Ҫ��������ֽڵ�����
			 //length - Ҫ������ֽ��� 
			 buf.append(new String(bytes, 0, len));
		}
		
		//��jsonת��ָ���Ķ���
		Gson  gson = new Gson();
		PaymentRequest pay = gson.fromJson(buf.toString(), PaymentRequest.class);
		
		//����charge����   
		Map<String, Object> chargeMap = new HashMap<String, Object>();
		// ĳЩ������Ҫ���extra�����������������ӿ��ĵ�
		chargeMap.put("amount", pay.amount);  //�������
		chargeMap.put("currency", "cny");   //��ʾ�����
		chargeMap.put("subject", "�������"); //��������
		chargeMap.put("body", "Your Body");  //��ϸ��������
		chargeMap.put("order_no", "1234567890");
		chargeMap.put("channel", pay.channel);  //ͨ��
		chargeMap.put("client_ip", request.getRemoteAddr()); //�ͻ��˵�ַ
		//System.out.println(request.getRemoteAddr());
		Map<String, String> app = new HashMap<String, String>();
		app.put("id", "app_rf18mTPqXnf19q1a");
		chargeMap.put("app", app);
		    //��json�ĸ�ʽ���ظ��ͻ���
		try {
		    //����������
		    Charge charge = Charge.create(chargeMap);
		    out.write(charge.toString());
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	

}
