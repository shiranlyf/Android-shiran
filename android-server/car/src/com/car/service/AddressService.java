package com.car.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.car.dao.AddressDAO;
import com.car.dao.UserDAO;
import com.car.entity.Address;
import com.car.entity.User;
import com.car.util.StringUtil;
import com.google.gson.GsonBuilder;

public class AddressService {

	private AddressDAO addressDAO = new AddressDAO();
	private UserDAO   userDAO = new UserDAO();

	
	/**
	 * �����û���id�õ��û������е�ַ
	 * @param uidStr
	 * @return
	 */
	public String getPersonAddress(String uname) {
		List<Address>  addressList = new ArrayList<Address>();
		//int uid = Integer.parseInt(uidStr);
		User user = (User) userDAO.findByName(uname).get(0);
		addressList = addressDAO.findByProperty("user", user);
		String  addressJson = new GsonBuilder().create().toJson(addressList);
		addressJson = "{" + "data:" + addressJson + "}";
		//����Ҫ����sessonһ�������ˢ�º͹ر�
		userDAO.getSession().flush();
		userDAO.getSession().close();
		return addressJson;
	}

    /**
     * �޸ĵ�ַ��Ϣ
     * @param uidStr
     * @param addressNameValue
     * @param peopleSex
     * @param addressPhoneValue
     * @param preparePhoneValue
     * @param addressDetailValue
     * @param locationCityValue 
     * @return
     */
	public boolean updatePersonAddress(String addressIdStr, String addressNameValue,
			String peopleSex, String addressPhoneValue,
			String preparePhoneValue, String addressDetailValue, String locationCityValue) {
		int addressId = Integer.parseInt(addressIdStr);
		Address address = addressDAO.findById(addressId);
		if (address != null) {
			address.setAddressName(addressNameValue);
			address.setPeopleSex(peopleSex);
			address.setAddressTel(addressPhoneValue);
			address.setPreparePhone(preparePhoneValue);
			address.setAddressDetail(addressDetailValue);
			address.setAddressCity(locationCityValue);
		}
		Transaction transaction = addressDAO.getSession().beginTransaction();
		addressDAO.save(address);
		//��ˢ���û�ȡ��ַ��ʱ��ͻ�֮ǰ������
		addressDAO.getSession().flush();
		transaction.commit();
		return  true;
	}

	/**
	 * ���˵�ַ�����
	 * @param uname �û�����
	 * @param addressIdStr
	 * @param addressNameValue
	 * @param peopleSex
	 * @param addressPhoneValue
	 * @param preparePhoneValue
	 * @param addressDetailValue
	 * @param locationCityValue 
	 * @return
	 */
	public boolean addPersonAddress(String uname, String addressIdStr,
			String addressNameValue, String peopleSex,
			String addressPhoneValue, String preparePhoneValue,
			String addressDetailValue, String locationCityValue) {
		Transaction  transaction = addressDAO.getSession().beginTransaction();
		int uid = 0;
		Address  address = new Address();
		
		User  user = (User) userDAO.findByName(uname);
		address.setUser(user);
		//System.out.println("--------" + user.getName());
		address.setAddressName(addressNameValue);
		address.setPeopleSex(peopleSex);
		address.setPreparePhone(addressPhoneValue);
		address.setPreparePhone(preparePhoneValue);
		address.setAddressDetail(addressDetailValue);
		address.setAddressCity(locationCityValue);
		addressDAO.getSession().save(address);
		transaction.commit();
		userDAO.getSession().close();
		addressDAO.getSession().close();
		return true;
	}

	/**
	 * ����idɾ����ַ
	 * @param addressIdStr
	 * @return
	 */
	public boolean deletePersonAddressById(String addressIdStr) {
		System.out.println(addressIdStr);
		int addressId = 0;
		if (StringUtil.isNotNull(addressIdStr)) {
			addressId = Integer.parseInt(addressIdStr);
			Session  session = addressDAO.getSession();
			Transaction transaction = session.beginTransaction();
			Address address = addressDAO.findById(addressId);
			addressDAO.delete(address);
			transaction.commit();
			session.close();
			return true;
		}else {
			return false;
		}
	}
	
}
