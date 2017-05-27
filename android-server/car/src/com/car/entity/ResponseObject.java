package com.car.entity;

public class ResponseObject {
	private String msg;
	private int state = 1;//0：失败；1：成功
	private Object datas;//存放我真正需要解析的数据
	private int page;
	private int size;
	private int count;  //总共的页数
	private int sum;    //总共的数据条数
	
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public ResponseObject(int state,String msg) {
		this.state = state;
		this.msg = msg;
	}
	public ResponseObject(int state,Object datas) {
		this.state = state;
		this.datas = datas;
	}
	public ResponseObject(int state,String msg,Object datas) {
		this.state = state;
		this.datas = datas;
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Object getDatas() {
		return datas;
	}
	public void setDatas(Object datas) {
		this.datas = datas;
	}
}
