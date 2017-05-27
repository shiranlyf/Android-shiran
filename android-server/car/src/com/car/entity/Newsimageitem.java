package com.car.entity;

/**
 * Newsimageitem entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Newsimageitem implements java.io.Serializable {

	// Fields

	public Integer id;
	public String title;
	public String url;
	public Integer nhId;

	// Constructors

	/** default constructor */
	public Newsimageitem() {
	}

	/** full constructor */
	public Newsimageitem(String title, String url, Integer nhId) {
		this.title = title;
		this.url = url;
		this.nhId = nhId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getNhId() {
		return this.nhId;
	}

	public void setNhId(Integer nhId) {
		this.nhId = nhId;
	}

}