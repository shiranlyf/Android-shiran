package com.car.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.car.dao.NewsimportantDAO;
import com.car.dao.OrderDAO;
import com.car.dao.UserDAO;
import com.car.entity.Newsimportant;
import com.car.entity.Order;
import com.car.entity.ResponseObject;
import com.car.entity.User;
import com.car.util.StringUtil;
import com.google.gson.GsonBuilder;

public class OrderService {

	private OrderDAO orderDAO = new OrderDAO();
	private UserDAO userDAO = new UserDAO();
	private NewsimportantDAO newsimportantDAO = new NewsimportantDAO();

	/**
	 * ��������Ϣ���뵽���ݿ�
	 * 
	 * @param uidStr
	 * @param goodsIdStr
	 * @param addressId
	 * @param payway
	 * @return
	 */
	public String getShowInfoJson(String uidStr, String goodsIdStr,
			String addressId, String payway) {
		Session session = orderDAO.getSession();
		Transaction transaction = session.beginTransaction();
		Order order = new Order();
		order.setAddrId(Integer.parseInt(addressId));
		order.setUserId(Integer.parseInt(uidStr));
		int goodId = Integer.parseInt(goodsIdStr);
		order.setGoodId(goodId);
		order.setOrderTime(new Date());
		order.setIspay(0);
		order.setPayWay(Integer.parseInt(payway)); // 1��ʾ��������
		//��������Ҫ��ʾ���û���Ϣ����Ʒ��Ϣ������ʾ
		
		//������Ʒid�õ���Ʒ����
		String  goodHql = "from Newsimportant ";
		Newsimportant  newsimportant = newsimportantDAO.findById(goodId);
		String goodsImg = newsimportant.getCoverImage();
		String goodsName = newsimportant.getTitle();
		String goodsPrice = newsimportant.getCar_price();
		String show_info = goodsImg + "_" + goodsName + "_" + goodsPrice;
		order.setOrder_show_info(show_info);
		session.save(order);
		transaction.commit();
		session.close(); // ��session���йر�
		// 1��ʾ�µ��ɹ� 0��ʾ�����µ�ʧ��
		if (order != null) {
			return "{" + "isSuccess:" + 1 + "}";
		} else {
			return "{" + "isSuccess:" + 0 + "}";
		}
	}

	/**
	 * �õ�����json
	 * 
	 * @param username
	 * @param pageStr
	 * @param sizeStr
	 * @param orderId 
	 * @return
	 */
	public String getOrderJsonByFenye(String username, String pageStr,
			String sizeStr, String orderId) {
		int page = Integer.parseInt(pageStr);
		int size = Integer.parseInt(sizeStr);
		//���ȸ���id����ɾ��
	    deleteOrderById(orderId);
		List<User> userList = userDAO.findByName(username);
		User user = userList.get(0);
		int uid = user.getUid();
		String uniquHql = "select count(*) from Order ";
		String sumStr = orderDAO.getSession().createQuery(uniquHql)
				.uniqueResult().toString();
		int sum = Integer.parseInt(sumStr);
		int count = sum % size == 0 ? sum / size : sum / size + 1;
		if (page < 1) {
			page = 1;
		}
		if (size > count) {
			page = count;
		}
		String orderHql = "from Order order by orderTime desc ";
		Query orderQuery = orderDAO.getSession().createQuery(orderHql);
		orderQuery.setFirstResult((page - 1) * size).setMaxResults(size);
		List<Order> orderList = orderQuery.list();
		ResponseObject result = null;
		if (orderList != null && orderList.size() > 0) {
             result = new  ResponseObject(1, orderList);
             result.setPage(page);
             result.setSize(size);
             result.setCount(count);
             result.setSum(sum);
             result.setDatas(orderList);
		} else {
            result = new ResponseObject(0, "�ף�����û���µ�Ŷ!");
            result.setDatas(null);
		}
		orderDAO.getSession().flush();
		orderDAO.getSession().clear();
		return new GsonBuilder().create().toJson(result);
	}

	/**
	 * ����idɾ��order
	 * @param orderId
	 * @return
	 */
	public String deleteOrderById(String orderId) {
		String result = "0";
		Session session = orderDAO.getSession();
		Transaction transaction  = session.beginTransaction();
		String hql = "from Order where oid = ? ";
		Query query = session.createQuery(hql);
		query.setParameter(0,  Integer.parseInt(orderId));
		List<Order>  orders =  query.list();
		if (orders != null && orders.size() > 0) {
			session.delete(orders.get(0));
			transaction.commit();
			result = "1";
		}
		session.flush();
		session.clear();
		session.close();
		return  result;
	}

	
}
