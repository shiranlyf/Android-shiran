package com.car.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.hibernate.Query;

import com.car.dao.NewsimportantDAO;
import com.car.dao.ShopDAO;
import com.car.entity.Newsimportant;
import com.car.entity.ResponseObject;
import com.car.entity.Shop;
import com.car.util.StringUtil;
import com.google.gson.GsonBuilder;

public class NewsimportantService {

	private  NewsimportantDAO newsimportantDAO=new NewsimportantDAO();
	private ShopDAO   shopDAO = new ShopDAO();
	
	/**
	 * �õ�����ģ���json�ַ���
	 * @return
	 */
	public String  getNewsImportantJson(String pageString,String sizeString){
		String  hql="select count(*) from Newsimportant";
		int page=1;
		int size=20;
		if (StringUtil.isNotNull(pageString)) {
		   page=Integer.parseInt(pageString);
		}
		if (StringUtil.isNotNull(sizeString)) {
			size=Integer.parseInt(sizeString);
		}
		//�õ��ܵ�����
	    int sum=Integer.parseInt(newsimportantDAO.getSession().createQuery(hql).uniqueResult().toString());
	    //��һ������ҳ���ķ���  ����һ�����컨��ķ���
	    //int count=(int) Math.ceil(sum/size);
	    //���ǵڶ�������ҳ���ķ���  
	    int count = sum%size==0 ? sum/size : sum/size+1;
	    if (page<1) 
	    	page=1;
	    hql="from Newsimportant";
	    Query  query=newsimportantDAO.getSession().createQuery(hql);
	    query.setFirstResult((page-1)*size).setMaxResults(size);
	    List<Newsimportant>   list=query.list();
	    JsonConfig  config=new JsonConfig();
	    //�����ж�������
	    //config.setExcludes(new String[]{"brand","kind2","goodsimages","carts","ordersdetails","timeses","","favorites"});
	    ResponseObject  result=null;
	    if (list!=null&&list.size()>0) {
			//���������ż�¼  1��ʾ�����ż�¼
	    	result=new  ResponseObject(1,list);
	    	//����ҳ��
	    	result.setPage(page);
	    	//����ÿҳ�Ĵ�С
	    	result.setSize(size);
	        //��������һ����ҳ���ķ���
	    	result.setCount(count);
	    	//�����ʾ���ܵ���������
	    	result.setSum(sum);
	    	//���ݼ�
	    	result.setDatas(list);
	    	//���̼���Ϣ����
	    	for (Newsimportant newsimportant : list) {
	    		Shop  shop = null;
	    		String  shopSql = "from Shop where shopId=? ";
	    		Query  shopQuery = shopDAO.getSession().createQuery(shopSql);
	    		shopQuery.setParameter(0, newsimportant.getShopid());
	    		List<Shop>  shopList = shopQuery.list();
	    		if (shopList != null && shopList.size() >0) {
					shop = shopList.get(0);
				}
	    		newsimportant.setShop(shop);
			}
		} else {
            //����û�����ż�¼  0��ʾʧ��  û�����ż�¼
		    result=new ResponseObject(0,"��ʱ��û�����ż�¼");
		    result.setDatas(null);
		}
	    //���ǵ�һ��json�����ķ���
	    //String  json=JSONArray.fromObject(result, config).toString();
	    //���ǵڶ���json�����ķ���  ����Google�˳���һ�ַ���
	    String json=new GsonBuilder().create().toJson(result);
		return  json;
	}

}
