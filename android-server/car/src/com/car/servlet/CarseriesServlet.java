package com.car.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.service.CareriesService;

public class CarseriesServlet extends HttpServlet {
	
	private  CareriesService   careriesService = new CareriesService();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String method = request.getParameter("method");
		// careries.do?method=getCareriesJson
		if ("getCareriesJson".equals(method)) {
			getCareriesJson(request, response);
		}
	}

	/**
	 * 二级车的分类
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void getCareriesJson(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
	    PrintWriter  out = response.getWriter();
	    //根据一级分类的id查找二级
	    String  carLogoId = request.getParameter("id");
	    String  careriesJson = careriesService.getCareriesJson(carLogoId);
	    
	    out.print(careriesJson);
	    out.close();
	}
}
