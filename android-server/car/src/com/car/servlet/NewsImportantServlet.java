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

//��������ģ��
public class NewsImportantServlet extends HttpServlet {
	
	
    //ӳ��service�е�����ģ����
	NewsimportantService    newsimportantService=new NewsimportantService();
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		  request.setCharacterEncoding("utf-8");
		  response.setContentType("text/html;charset=utf-8");
		  String  method=request.getParameter("method");
		  //http://127.0.0.1:8080/car/newsImportant.do?method=getNewsImportant
		  //�õ�����  newsImportant.do?method=getNewsImportant
		  if ("getNewsImportant".equals(method)) {
			  getNewsImportant(request,response);
		 }
		  
	}

    /**
     * �õ�����ģ���json�ַ���
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
		 //�õ�newimportant��json
		 String json=newsimportantService.getNewsImportantJson(page,size);
		 System.out.println(json);
		 //���õ�json�ַ�������
		 out.print(json);
	}
}
