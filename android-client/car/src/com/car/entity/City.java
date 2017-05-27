package com.car.entity;

/**
 * City entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class City implements java.io.Serializable {

	// Fields

	private Integer cityId;
	private String cityName;
	private String citySortkey;

	@Override
	public String toString() {
		return "City [cityId=" + cityId + ", cityName=" + cityName
				+ ", citySortkey=" + citySortkey + ", getCityId()="
				+ getCityId() + ", getCityName()=" + getCityName()
				+ ", getCitySortkey()=" + getCitySortkey() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	// Constructors

	/** default constructor */
	public City() {
	}

	/** full constructor */
	public City(String cityName, String citySortkey) {
		this.cityName = cityName;
		this.citySortkey = citySortkey;
	}

	// Property accessors

	public Integer getCityId() {
		return this.cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCitySortkey() {
		return this.citySortkey;
	}

	public void setCitySortkey(String citySortkey) {
		this.citySortkey = citySortkey;
	}

}