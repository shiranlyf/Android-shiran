package com.car.test;

import java.awt.TextArea;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.crypto.Data;

public class Test {
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date date = new Date();
        System.out.println(sdf.format(date.toString()));
	}
}
