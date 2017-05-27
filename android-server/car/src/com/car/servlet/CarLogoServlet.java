package com.car.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.Request;

import com.car.service.CarLogoService;

//Carlogo��ʾ
public class CarLogoServlet extends HttpServlet {
	
	private  CarLogoService  carLogoService = new CarLogoService();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		    request.setCharacterEncoding("utf-8");
		    response.setContentType("text/html;charset=utf-8");
		    
		    String   method = request.getParameter("method");
		    //�õ����е�carlogojson  carlogo.do?method=getAllCarLogoJson
		    if ("getAllCarLogoJson".equals(method)) {
		    	getAllCarLogoJson(request , response);
			}
	}
	
	/**
	 * �õ�����carlogo��json
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void getAllCarLogoJson(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		  request.setCharacterEncoding("utf-8");
		  response.setContentType("text/html;charset=utf-8");
		  PrintWriter  out = response.getWriter();
		  
		  //�õ�carlogo��json  
		  String   carlogJson = carLogoService.getAllCarLogoJson();
		  out.print(carlogJson);
		  out.close();
		  
	}

	
	
	
	
}
