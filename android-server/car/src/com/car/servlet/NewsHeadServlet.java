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
 * 这是新闻头
 */
public class NewsHeadServlet extends HttpServlet {
	
	//新闻滑动的service
	NewsHeadService    headService= new NewsHeadService();
	
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	  request.setCharacterEncoding("utf-8");
    	  response.setContentType("text/html/charset=utf-8");
          
    	  String method=request.getParameter("method");
    	  //得到新闻的头head    newshead.do?method=getNewsHead
    	  if ("getNewsHead".equals(method)) {
    		  getNewsHead(request,response);
		}
    }

    /**
     * 得到新闻模块的头head
     * @param request
     * @param response
     * @throws IOException 
     */
	private void getNewsHead(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//得到传过来的页数
		String  page = request.getParameter("page");
		String  size = request.getParameter("size");
		PrintWriter   out = response.getWriter();
		
		//得到新闻滑动的json
		String  headJson = headService.getNewsHeadJson(page, size);
		
		//将数据返回
		out.print(headJson);
		//System.out.println(headJson);
		out.close();
	}

}
