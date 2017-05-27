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
	 * 会员登录检测
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
					result = new ResponseObject(1, "登录成功");
					result.setDatas(user);
				}
			}else {
				result = new ResponseObject(0, "用户名或密码错误");
			}
		}else {
			result = new ResponseObject(0, "用户名和密码不能为空");
		}
		userDAO.getSession().clear();
		String  json = new GsonBuilder().create().toJson(result);
		return json;
	}

	/**
	 * 会员注册  boolean用于判断是否插入成功
	 * @param userPhone
	 * @param password
	 * @return
	 */
	public String registerUser(String userPhone, String password) {
		ResponseObject  result = null;
		if (StringUtil.isNull(userPhone)&&StringUtil.isNull(password)) {
			//得到session  添加需要提交事务
			Session session = userDAO.getSession();
			Transaction transaction = session.beginTransaction();
			User  user = new User();
			//这里支付密码和联系电话是一样的
			user.setName(userPhone);
			user.setTel(userPhone);
			user.setLoginPwd(password);
			session.save(user);
//			session.merge(user);   进行修改的时候执行的方法
			//提交事务
			transaction.commit();
			//关闭session
			session.close();
		    if (user !=null) {
		    	result = new ResponseObject(1, "注册成功");
				result.setDatas(user);
			}else {
				result = new ResponseObject(0, "注册失败");
			}
		}else {
			result = new ResponseObject(0, "注册用户名或密码为空");
		}
	    //转化成json字符串
		String  json = new GsonBuilder().create().toJson(result);
	    return json;
	}

	/**
	 * 根据用户名得到用户对象
	 * @param username
	 * @return
	 */
	public String getUserByUsername(String username) {
		String result = null;
		//首先得到sessio对象
		Session  session = userDAO.getSession();
		String hql = "from User where name = ? ";
		//通过session对象创建查询
		Query  query = session.createQuery(hql);
		query.setParameter(0, username); //设置查询的时候的参数
		List<User>  userList = query.list();
		
		if (userList != null && userList.size() > 0) {
			User  user = userList.get(0);
			//将user对象转化成Json对象
			result = new GsonBuilder().create().toJson(user);
			//将地址信息进行拼接
			String addHql = "from Address where user = ?";
			Query  query2 = session.createQuery(addHql);
			query2.setParameter(0, user);
			List<Address>  addList = query2.list();
			if(addList != null && addList.size() > 0){
				Address address  = addList.get(0);  //默认获取第一个地址
				//进行地址信息的拼接
				//sb.setCharAt(sb.length() - 1, ']');   这个方法是StringBuffer中
				result = result.substring(1,result.length()-1);
				//得到地址的名称 
				String addName = address.getAddressName();
				//这里拼接的是地址名称
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
