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
 * 提示键：选中+shift+alt+L
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
		
		//获取客户端参数：amount channel 客户端是以流的方式传输过来的  所以要以流的方式进行读取  
		ServletInputStream in = request.getInputStream();
		byte[] bytes = new byte[512];
		int len = -1;
		StringBuffer  buf = new StringBuffer();
		while ((len = in.read(bytes)) != -1) {
			 //bytes - 要解码为字符的字节
			 //offset - 要解码的首字节的索引
			 //length - 要解码的字节数 
			 buf.append(new String(bytes, 0, len));
		}
		
		//将json转成指定的对象
		Gson  gson = new Gson();
		PaymentRequest pay = gson.fromJson(buf.toString(), PaymentRequest.class);
		
		//创建charge对象   
		Map<String, Object> chargeMap = new HashMap<String, Object>();
		// 某些渠道需要添加extra参数，具体参数详见接口文档
		chargeMap.put("amount", pay.amount);  //订单金额
		chargeMap.put("currency", "cny");   //表示人民币
		chargeMap.put("subject", "购买测试"); //购买主题
		chargeMap.put("body", "Your Body");  //详细主体内容
		chargeMap.put("order_no", "1234567890");
		chargeMap.put("channel", pay.channel);  //通道
		chargeMap.put("client_ip", request.getRemoteAddr()); //客户端地址
		//System.out.println(request.getRemoteAddr());
		Map<String, String> app = new HashMap<String, String>();
		app.put("id", "app_rf18mTPqXnf19q1a");
		chargeMap.put("app", app);
		    //以json的格式返回给客户端
		try {
		    //发起交易请求
		    Charge charge = Charge.create(chargeMap);
		    out.write(charge.toString());
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	

}
