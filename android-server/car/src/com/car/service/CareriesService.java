package com.car.service;

import java.io.File;
import java.util.List;

import org.hibernate.Query;

import com.car.dao.CarseriesDAO;
import com.car.entity.Carseries;
import com.car.util.StringUtil;
import com.google.gson.GsonBuilder;


public class CareriesService {
    
	CarseriesDAO  carseriesDAO = new CarseriesDAO();

	
	/**
	 * �����ҳ�
	 * @return
	 */
	public String getCareriesJson(String  idString) {
		int  id = 0;
		//����Carseries��id�ֶδ�Carlogo�н��в���
		if (StringUtil.isNotNull(idString)) {
			id = Integer.parseInt(idString);
		}
		String  carseriesHql = "from Carseries where id=? ";
		Query query = carseriesDAO.getSession().createQuery(carseriesHql);
		query.setParameter(0, id);
		List<Carseries>  carserList = query.list();
		String  carserieJson = new GsonBuilder().create().toJson(carserList);
		return carserieJson;
	}
	
	
	
	
	
}
