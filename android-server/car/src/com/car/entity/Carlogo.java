package com.car.entity;

/**
 * Carlogo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Carlogo implements java.io.Serializable {

	// Fields

	public Integer id;
	public String namespell;
	public String name;
	public Integer cid;
	public String carimg;

	// Constructors

	/** default constructor */
	public Carlogo() {
	}

	/** full constructor */
	public Carlogo(String namespell, String name, Integer cid, String carimg) {
		this.namespell = namespell;
		this.name = name;
		this.cid = cid;
		this.carimg = carimg;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNamespell() {
		return this.namespell;
	}

	public void setNamespell(String namespell) {
		this.namespell = namespell;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCid() {
		return this.cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCarimg() {
		return this.carimg;
	}

	public void setCarimg(String carimg) {
		this.carimg = carimg;
	}

}