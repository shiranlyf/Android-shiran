package com.car.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.service.OrderService;

public class OrderServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OrderService orderService = new OrderService();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");
		//order.do?method=confirmOrder&uid=" + uid +"&goodsId=" + goodsId
		String method = request.getParameter("method");
		if ("confirmOrder".equals(method)) {
			confirmOrder(request, response);
		}
		//������ҳ��ѯ
		if("getOrderByfenye".equals(method)){
			getOrderByfenye(request, response);
		}
		
		
	}

	/**
	 * ����idɾ������
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void deleteOrderById(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
	     PrintWriter out = response.getWriter();
	     String orderId = request.getParameter("orderId");
	     String status = orderService.deleteOrderById(orderId);
	     //System.out.println(status + "---------dingdan-----------");
	     out.print(status);
	}

	/**
	 * ������ҳ��ѯ
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void getOrderByfenye(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		//�õ��û���
		String username = new String(request.getParameter("uname").getBytes("ISO-8859-1"), "utf-8");
		String pageStr = request.getParameter("page");
		String sizeStr = request.getParameter("size");
		String orderId = request.getParameter("orderId");
		String orderJson = orderService.getOrderJsonByFenye(username, pageStr, sizeStr, orderId); 
		out.print(orderJson);
		out.close();
		out.flush();
	}

	/**
	 * ȷ�϶���
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void confirmOrder(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		 //ctrl + alt + L
		PrintWriter out = response.getWriter();
		String uidStr = request.getParameter("uid");
		String goodsIdStr = request.getParameter("goodsId");
		String addressId = request.getParameter("addressId");
		//֧����ʽ
		String payway = request.getParameter("payway");
		//����Ҫ��ʾ����Ϣ����
		String isSuccess = orderService.getShowInfoJson(uidStr, goodsIdStr, addressId, payway);
		out.print(isSuccess);
		out.close();
	}

	
           
	

}
