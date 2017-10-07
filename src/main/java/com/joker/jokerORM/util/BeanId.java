package com.joker.jokerORM.util;

public class BeanId {
	private int id;
	private String idName;
	private Class beanClass;
	
	public BeanId(int id, String idName, Class beanClass) {
		super();
		this.id = id;
		this.idName = idName;
		this.beanClass = beanClass;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIdName() {
		return idName;
	}
	public void setIdName(String idName) {
		this.idName = idName;
	}
	public Class getBeanClass() {
		return beanClass;
	}
	public void setBeanClass(Class beanClass) {
		this.beanClass = beanClass;
	}
	
	
}
