package com.car.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.jaxen.function.CeilingFunction;

import com.car.dao.NewsheadDAO;
import com.car.dao.NewsimageitemDAO;
import com.car.dao.NewsimportantDAO;
import com.car.entity.Newshead;
import com.car.entity.Newsimageitem;
import com.car.entity.Newsimportant;
import com.car.entity.ResponseObject;
import com.car.util.StringUtil;
import com.google.gson.GsonBuilder;

/**
 * 新闻滑动的service
 * @author Administrator
 *
 */
public class NewsHeadService {

	//引入dao
	NewsheadDAO  newsheadDAO = new NewsheadDAO();
	NewsimageitemDAO  newsimageitemDAO = new NewsimageitemDAO();
	
	/**
	 * 得到新闻滑动的json
	 * @return
	 */
	public String getNewsHeadJson(String pageString, String sizeString) {
		//初始化页数和每页大小
		int  page = 1;
		int size = 5;
		if (StringUtil.isNotNull(pageString)) {
			page = Integer.parseInt(pageString);
		}
		if (StringUtil.isNotNull(sizeString)) {
			size = Integer.parseInt(sizeString);
		}
		
		//挑选行数hql
		String  hql = "select count(*) from Newshead";
		//得到数据的条数
		String  sumString = newsheadDAO.getSession().createQuery(hql).uniqueResult().toString();
		int sum = Integer.parseInt(sumString);
		//得到总的页数   这是天花板数求总页数的方法
		int count= (int) Math.ceil(sum/size);
		
		if (page<1) {
			page = 1;
		}
		
		//查询记录的hql
		hql = "from Newshead";
		Query  query = newsheadDAO.getSession().createQuery(hql);
		//进行分页  滑动的图片限制查询5条
		query.setFirstResult((page-1)*size).setMaxResults(size);
		List<Newshead>  list = query.list();
		//System.out.println(list.size()+"-------");
		ResponseObject  result = null;
		//拼接json字符串
		if (list!=null&&list.size()>0) 
		{  
			//对result进行初始化  1表示有数据
			result = new ResponseObject(1,list);
			//给result设置页数
			result.setPage(page);
			//给result设置每页的大小
			result.setSize(size);
			//设置总页数
			result.setCount(count);
			//设置总的记录数
			result.setSum(sum);
			//设置list数据源
			result.setDatas(list);
			
			//填充image_list
			for (Newshead newshead : list) {
				String  itemHql = "from Newsimageitem where nhId=? ";
				Query itemQuery = newsimageitemDAO.getSession().createQuery(itemHql);
			    itemQuery.setParameter(0, newshead.getId());
			    List<Newsimageitem> itemList = itemQuery.list();
			    newshead.setImage_list(new  GsonBuilder().create().toJson(itemList));
			}
		}else 
		{   //这表示没有数据
			result =new ResponseObject(0, "这里没有新闻滑动头");
			//给数据源设置为空
			result.setDatas(null);
		}
		//用google提供的gson进行解析
		String   headJson = new  GsonBuilder().create().toJson(result);
		return  headJson;
	}

}
