package com.car.service;

import java.util.List;

import javax.xml.ws.Response;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.car.dao.UserDAO;
import com.car.entity.Address;
import com.car.entity.ResponseObject;
import com.car.entity.User;
import com.car.util.StringUtil;
import com.google.gson.GsonBuilder;

public class UserService {
   
	private  UserDAO   userDAO = new UserDAO();
	
	/**
	 * ��Ա��¼���
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public String getUserLoginJson(String userName, String passWord) {
		ResponseObject  result = null;
		if (StringUtil.isNotNull(userName) && StringUtil.isNotNull(passWord)) {
			String  hql = "from User where name=? and loginPwd=? ";
			Query  query = userDAO.getSession().createQuery(hql);
			query.setParameter(0, userName);
			query.setParameter(1, passWord);
			List<User>  list = query.list();
			if (list != null && list.size() > 0) {
				User user = list.get(0);
				if (user != null) {
					result = new ResponseObject(1, "��¼�ɹ�");
					result.setDatas(user);
				}
			}else {
				result = new ResponseObject(0, "�û������������");
			}
		}else {
			result = new ResponseObject(0, "�û��������벻��Ϊ��");
		}
		userDAO.getSession().clear();
		String  json = new GsonBuilder().create().toJson(result);
		return json;
	}

	/**
	 * ��Աע��  boolean�����ж��Ƿ����ɹ�
	 * @param userPhone
	 * @param password
	 * @return
	 */
	public String registerUser(String userPhone, String password) {
		ResponseObject  result = null;
		if (StringUtil.isNull(userPhone)&&StringUtil.isNull(password)) {
			//�õ�session  �����Ҫ�ύ����
			Session session = userDAO.getSession();
			Transaction transaction = session.beginTransaction();
			User  user = new User();
			//����֧���������ϵ�绰��һ����
			user.setName(userPhone);
			user.setTel(userPhone);
			user.setLoginPwd(password);
			session.save(user);
//			session.merge(user);   �����޸ĵ�ʱ��ִ�еķ���
			//�ύ����
			transaction.commit();
			//�ر�session
			session.close();
		    if (user !=null) {
		    	result = new ResponseObject(1, "ע��ɹ�");
				result.setDatas(user);
			}else {
				result = new ResponseObject(0, "ע��ʧ��");
			}
		}else {
			result = new ResponseObject(0, "ע���û���������Ϊ��");
		}
	    //ת����json�ַ���
		String  json = new GsonBuilder().create().toJson(result);
	    return json;
	}

	/**
	 * �����û����õ��û�����
	 * @param username
	 * @return
	 */
	public String getUserByUsername(String username) {
		String result = null;
		//���ȵõ�sessio����
		Session  session = userDAO.getSession();
		String hql = "from User where name = ? ";
		//ͨ��session���󴴽���ѯ
		Query  query = session.createQuery(hql);
		query.setParameter(0, username); //���ò�ѯ��ʱ��Ĳ���
		List<User>  userList = query.list();
		
		if (userList != null && userList.size() > 0) {
			User  user = userList.get(0);
			//��user����ת����Json����
			result = new GsonBuilder().create().toJson(user);
			//����ַ��Ϣ����ƴ��
			String addHql = "from Address where user = ?";
			Query  query2 = session.createQuery(addHql);
			query2.setParameter(0, user);
			List<Address>  addList = query2.list();
			if(addList != null && addList.size() > 0){
				Address address  = addList.get(0);  //Ĭ�ϻ�ȡ��һ����ַ
				//���е�ַ��Ϣ��ƴ��
				//sb.setCharAt(sb.length() - 1, ']');   ���������StringBuffer��
				result = result.substring(1,result.length()-1);
				//�õ���ַ������ 
				String addName = address.getAddressName();
				//����ƴ�ӵ��ǵ�ַ����
				result = result + "," +'"' + "addressName" + '"' + ':' + '"' + addName + '"';
			    result = result + "," +'"' + "addressId" + '"' + ':' + '"' + address.getAddressId() + '"';
			}else {
				result = result.substring(1,result.length()-1);
				result = result + "," +'"' + "addressName" + '"' + ':' + '"' + null + '"';
				result = result + "," +'"' + "addressId" + '"' + ':' + '"' + null + '"';
			}
		}
		result = "{" + result + "}";
		return result;
	}

}
