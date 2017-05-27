package com.car.service;

import java.util.List;

import org.hibernate.Query;

import com.car.dao.CityDAO;
import com.car.entity.City;
import com.car.entity.ResponseObject;
import com.google.gson.GsonBuilder;

public class CityService {

	private CityDAO cityDAO = new CityDAO();

	
	/**
	 * �õ����г��е�json
	 * @return
	 */
	public String getAllCityJson() {
		ResponseObject result = null;
		String  citysql = "from City order by citySortkey ";
	    Query query = cityDAO.getSession().createQuery(citysql);
	    List<City>  cityList = query.list();
	    if (cityList != null && cityList.size() >0) {
			result = new ResponseObject(1, cityList);
		}else {
			result = new ResponseObject(0, "��ȡ����ʧ��");
		}
	    //���session �� �ر�
	    cityDAO.getSession().clear();
	    cityDAO.getSession().close();
	    String  cityJosn = new GsonBuilder().create().toJson(result);
		return cityJosn;
	}
	
}
