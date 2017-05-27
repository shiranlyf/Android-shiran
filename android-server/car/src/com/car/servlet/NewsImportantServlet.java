package com.car.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.car.service.NewsimportantService;

//这是新闻模块
public class NewsImportantServlet extends HttpServlet {
	
	
    //映入service中的新闻模块类
	NewsimportantService    newsimportantService=new NewsimportantService();
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		  request.setCharacterEncoding("utf-8");
		  response.setContentType("text/html;charset=utf-8");
		  String  method=request.getParameter("method");
		  //http://127.0.0.1:8080/car/newsImportant.do?method=getNewsImportant
		  //得到新闻  newsImportant.do?method=getNewsImportant
		  if ("getNewsImportant".equals(method)) {
			  getNewsImportant(request,response);
		 }
		  
	}

    /**
     * 得到新闻模块的json字符串
     * @param request
     * @param response
     * @throws IOException 
     */
	private void getNewsImportant(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
	     request.setCharacterEncoding("utf-8");
		 response.setContentType("text/html;charset=utf-8");
		 String  page=request.getParameter("page");
		 String  size=request.getParameter("size");
		 PrintWriter  out=response.getWriter();
		 //得到newimportant的json
		 String json=newsimportantService.getNewsImportantJson(page,size);
		 System.out.println(json);
		 //将得到json字符串返回
		 out.print(json);
	}
}
