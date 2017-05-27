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
	 * 得到新闻模块的json字符串
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
		//得到总的条数
	    int sum=Integer.parseInt(newsimportantDAO.getSession().createQuery(hql).uniqueResult().toString());
	    //第一种求总页数的方法  这是一种求天花板的方法
	    //int count=(int) Math.ceil(sum/size);
	    //这是第二种求总页数的方法  
	    int count = sum%size==0 ? sum/size : sum/size+1;
	    if (page<1) 
	    	page=1;
	    hql="from Newsimportant";
	    Query  query=newsimportantDAO.getSession().createQuery(hql);
	    query.setFirstResult((page-1)*size).setMaxResults(size);
	    List<Newsimportant>   list=query.list();
	    JsonConfig  config=new JsonConfig();
	    //这是有对象的情况
	    //config.setExcludes(new String[]{"brand","kind2","goodsimages","carts","ordersdetails","timeses","","favorites"});
	    ResponseObject  result=null;
	    if (list!=null&&list.size()>0) {
			//表明有新闻纪录  1表示有新闻纪录
	    	result=new  ResponseObject(1,list);
	    	//设置页数
	    	result.setPage(page);
	    	//设置每页的大小
	    	result.setSize(size);
	        //这是另外一种求页数的方法
	    	result.setCount(count);
	    	//这里表示的总的数据条数
	    	result.setSum(sum);
	    	//数据集
	    	result.setDatas(list);
	    	//将商家信息传入
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
            //表明没有新闻纪录  0表示失败  没有新闻纪录
		    result=new ResponseObject(0,"暂时还没有新闻纪录");
		    result.setDatas(null);
		}
	    //这是第一种json解析的方法
	    //String  json=JSONArray.fromObject(result, config).toString();
	    //这是第二种json解析的方法  这是Google退出的一种方法
	    String json=new GsonBuilder().create().toJson(result);
		return  json;
	}

}
