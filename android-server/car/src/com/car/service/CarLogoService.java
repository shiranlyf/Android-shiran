package com.car.service;

import java.util.List;

import com.car.dao.CarlogoDAO;
import com.car.entity.Carlogo;
import com.google.gson.GsonBuilder;

public class CarLogoService {

	private  CarlogoDAO   carlogoDAO = new CarlogoDAO();
	
	/**
	 * �õ�carlogo��json
	 * @return
	 */
	public String getAllCarLogoJson() {
		
		List<Carlogo>  carList = carlogoDAO.findAll();
		//����json����
		String  carJson = new GsonBuilder().create().toJson(carList);
		
		return carJson;
	}

}
