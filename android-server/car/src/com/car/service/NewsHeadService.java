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
 * ���Ż�����service
 * @author Administrator
 *
 */
public class NewsHeadService {

	//����dao
	NewsheadDAO  newsheadDAO = new NewsheadDAO();
	NewsimageitemDAO  newsimageitemDAO = new NewsimageitemDAO();
	
	/**
	 * �õ����Ż�����json
	 * @return
	 */
	public String getNewsHeadJson(String pageString, String sizeString) {
		//��ʼ��ҳ����ÿҳ��С
		int  page = 1;
		int size = 5;
		if (StringUtil.isNotNull(pageString)) {
			page = Integer.parseInt(pageString);
		}
		if (StringUtil.isNotNull(sizeString)) {
			size = Integer.parseInt(sizeString);
		}
		
		//��ѡ����hql
		String  hql = "select count(*) from Newshead";
		//�õ����ݵ�����
		String  sumString = newsheadDAO.getSession().createQuery(hql).uniqueResult().toString();
		int sum = Integer.parseInt(sumString);
		//�õ��ܵ�ҳ��   �����컨��������ҳ���ķ���
		int count= (int) Math.ceil(sum/size);
		
		if (page<1) {
			page = 1;
		}
		
		//��ѯ��¼��hql
		hql = "from Newshead";
		Query  query = newsheadDAO.getSession().createQuery(hql);
		//���з�ҳ  ������ͼƬ���Ʋ�ѯ5��
		query.setFirstResult((page-1)*size).setMaxResults(size);
		List<Newshead>  list = query.list();
		//System.out.println(list.size()+"-------");
		ResponseObject  result = null;
		//ƴ��json�ַ���
		if (list!=null&&list.size()>0) 
		{  
			//��result���г�ʼ��  1��ʾ������
			result = new ResponseObject(1,list);
			//��result����ҳ��
			result.setPage(page);
			//��result����ÿҳ�Ĵ�С
			result.setSize(size);
			//������ҳ��
			result.setCount(count);
			//�����ܵļ�¼��
			result.setSum(sum);
			//����list����Դ
			result.setDatas(list);
			
			//���image_list
			for (Newshead newshead : list) {
				String  itemHql = "from Newsimageitem where nhId=? ";
				Query itemQuery = newsimageitemDAO.getSession().createQuery(itemHql);
			    itemQuery.setParameter(0, newshead.getId());
			    List<Newsimageitem> itemList = itemQuery.list();
			    newshead.setImage_list(new  GsonBuilder().create().toJson(itemList));
			}
		}else 
		{   //���ʾû������
			result =new ResponseObject(0, "����û�����Ż���ͷ");
			//������Դ����Ϊ��
			result.setDatas(null);
		}
		//��google�ṩ��gson���н���
		String   headJson = new  GsonBuilder().create().toJson(result);
		return  headJson;
	}

}
