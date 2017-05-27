package com.car.entity;

import java.util.ArrayList;
import java.util.List;

import com.car.R.string;

/**
 * Newshead entity.
 * 新闻滑动模块
 * @author MyEclipse Persistence Tools
 */

public class Newshead implements java.io.Serializable {

	// Fields

	public Integer id;
	public String nid;
	public String converimage;
	public String title;
	public String type;
	//public List<Newsimageitem>  image_list = new ArrayList<Newsimageitem>();
	public String image_list;

	// Constructors

	/** default constructor */
	public Newshead() {
	}


	/** full constructor */
	public Newshead(String nid, String converimage, String title, String type) {
		this.nid = nid;
		this.converimage = converimage;
		this.title = title;
		this.type = type;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNid() {
		return this.nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getConverimage() {
		return this.converimage;
	}

	public void setConverimage(String converimage) {
		this.converimage = converimage;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getImage_list() {
		return image_list;
	}


	public void setImage_list(String image_list) {
		this.image_list = image_list;
	}
    
	


	

}