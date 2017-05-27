package com.car.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.service.NewsHeadService;

/**
 * ��������ͷ
 */
public class NewsHeadServlet extends HttpServlet {
	
	//���Ż�����service
	NewsHeadService    headService= new NewsHeadService();
	
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	  request.setCharacterEncoding("utf-8");
    	  response.setContentType("text/html/charset=utf-8");
          
    	  String method=request.getParameter("method");
    	  //�õ����ŵ�ͷhead    newshead.do?method=getNewsHead
    	  if ("getNewsHead".equals(method)) {
    		  getNewsHead(request,response);
		}
    }

    /**
     * �õ�����ģ���ͷhead
     * @param request
     * @param response
     * @throws IOException 
     */
	private void getNewsHead(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//�õ���������ҳ��
		String  page = request.getParameter("page");
		String  size = request.getParameter("size");
		PrintWriter   out = response.getWriter();
		
		//�õ����Ż�����json
		String  headJson = headService.getNewsHeadJson(page, size);
		
		//�����ݷ���
		out.print(headJson);
		//System.out.println(headJson);
		out.close();
	}

}
