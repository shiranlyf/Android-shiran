package com.car.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.car.service.AddressService;
import com.car.util.StringUtil;

/**
 * �����Ż���ַ����
 * 
 * @author shiran
 * 
 */
public class AddressServlet extends HttpServlet {

	private AddressService addressService = new AddressService();

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String method = request.getParameter("method");
		if ("getPersonAddress".equals(method)) {
			getPersonAddress(request, response);
		}
		// ConstantsUtil.SERVER_URL
		// + "address.do?method=addPersonAddress&uid=" + uid
		// + "&addressNameValue=" + addressNameValue
		// + "&peopleSex=" + peopleSex
		// + "&addressPhoneValue=" + addressPhoneValue
		// + "&preparePhoneValue=" + preparePhoneValue
		// + "&addressDetailValue="+ addressDetailValue
		if ("updatePersonAddress".equals(method)) {
			updatePersonAddress(request, response);
		}
		//address.do?method=deletePersonAddress&addressId
		if ("deletePersonAddress".equals(method)) {
			deletePersonAddress(request, response);
		}
	}

	/**
	 * ����idɾ����ַ
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void deletePersonAddress(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String  addressIdStr = request.getParameter("addressId");
		boolean f = addressService.deletePersonAddressById(addressIdStr);
		//1����ɾ���ɹ�  0����ʧ��
		if (f) {
			out.print("1");
		}else {
			out.print("0");
		}
	}

	/**
	 * �޸��û��ĸ��˵�ַ��Ϣ
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void updatePersonAddress(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		//�õ��û���id
		//String uidStr = request.getParameter("uid");
		String uname = new String(request.getParameter("uname").getBytes("ISO-8859-1"), "utf-8");
		String addressIdStr = request.getParameter("addressId");
		String addressNameValue = request.getParameter("addressNameValue");
		addressNameValue = new String(addressNameValue.getBytes("ISO-8859-1"),
				"utf-8");
		String peopleSex = request.getParameter("peopleSex");
		String addressPhoneValue = request.getParameter("addressPhoneValue");
		String preparePhoneValue = request.getParameter("preparePhoneValue");
		String addressDetailValue = request.getParameter("addressDetailValue");
		String locationCityValue = request.getParameter("locationCityValue");
		locationCityValue = new String(locationCityValue.getBytes("ISO-8859-1"), "utf-8");
		addressDetailValue = new String(addressDetailValue
				.getBytes("ISO-8859-1"), "utf-8");
		String isAddaddress = request.getParameter("isaddaddres");
		//System.out.println(isAddaddress + "�����ж�");
		boolean f = false;
		System.out.println(isAddaddress);
		if (!(isAddaddress == "1" || isAddaddress.equals("1"))) { //�����޸�

			f = addressService.updatePersonAddress(addressIdStr,
					addressNameValue, peopleSex, addressPhoneValue,
					preparePhoneValue, addressDetailValue, locationCityValue);
		} else {  //�������
            f = addressService.addPersonAddress(uname, addressIdStr,
					addressNameValue, peopleSex, addressPhoneValue,
					preparePhoneValue, addressDetailValue, locationCityValue);
		}

		if (f) {
			out.print("1"); // �޸ĳɹ�
		} else {
			out.print("0"); // �޸�ʧ��
		}
	}

	/**
	 * �õ����˵����������ַ
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getPersonAddress(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String addressJson = "";
		PrintWriter out = response.getWriter();
		// �õ��û���id��
		//String uidStr = request.getParameter("uid");
		String uname = request.getParameter("username");
		if (StringUtil.isNotNull(uname) && !"�����¼".equals(uname)) {
			// �õ����и��˵ĵ�ַ
			addressJson = addressService.getPersonAddress(uname);
		}
		out.print(addressJson);
	}

}
