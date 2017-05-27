package com.car.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.entity.User;
import com.car.service.UserService;
import com.car.util.StringUtil;

public class UserServlet extends HttpServlet {

	private UserService userService = new UserService();

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// user.do?method=checkUser&userName=1&passWord=1
		String method = request.getParameter("method");
		if ("checkUser".equals(method)) {
			checkUser(request, response);
		}
		// method=userRegister&userPhone="+phoneName+"&password="+passwordName
		if ("userRegister".equals(method)) {
			registerUser(request, response);
		}
		
		if("getUserByUsername".equals(method)){
			getUserByUsername(request, response);
		}

	}

	/**
	 * �����û����õ��û�����
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void getUserByUsername(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		PrintWriter  out = response.getWriter();
		//�õ��õ��û�������
		String username = request.getParameter("username");
		if (StringUtil.isNull(username)) {
			out.print("");
		}
		//�û���json�ַ���
		String userJson = userService.getUserByUsername(username);
		out.print(userJson);
		out.close();
	}

	/**
	 * �û�ע��
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void registerUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String userPhone = request.getParameter("userPhone");
		String password = request.getParameter("password");
		String  registerJson = userService.registerUser(userPhone, password);
		out.print(registerJson);
		out.close();
	}

	/**
	 * ��Ա��¼���
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void checkUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");
		String json = userService.getUserLoginJson(userName, passWord);
		out.print(json);
		System.out.println("�û�ע��" + json);
		out.close();
	}

}
