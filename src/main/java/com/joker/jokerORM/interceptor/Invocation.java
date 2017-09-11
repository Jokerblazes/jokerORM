package com.joker.jokerORM.interceptor;

import java.lang.reflect.Method;
import java.sql.Connection;


public class Invocation {
	private Object[] args;
	private Connection connection;
	private Method method;
	private String sql;
	public Invocation(Object[] args, Connection connection, Method method,String sql) {
		super();
		this.args = args;
		this.connection = connection;
		this.method = method;
		this.sql = sql;
	}
	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	
	
}
