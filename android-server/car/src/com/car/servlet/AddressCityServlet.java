package com.car.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.car.service.CityService;

public class AddressCityServlet extends HttpServlet {
	
	private CityService cityService = new CityService();

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("getAllCity".equals(method)) {
			getAllCity(request, response);
		}
	}

	/**
	 * 得到所有城市的json
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getAllCity(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String  cityJson = cityService.getAllCityJson();
		out.print(cityJson);
		out.flush();
		out.close();
	}
}
