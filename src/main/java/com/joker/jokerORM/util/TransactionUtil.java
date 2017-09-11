package com.joker.jokerORM.util;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionUtil {
	
	/**
	 * 
	 * 维护同一个线程中的Connection连接
	 */
	private static ThreadLocal<Connection> tl =  new ThreadLocal<Connection>();
	
	public static Connection getConnection() {
		Connection conn = (Connection)tl.get();
		return conn;
	}
	
	public static Connection getConnectionOrCreateConnection() {
		Connection conn = (Connection)tl.get();
		if (conn == null) {
			conn = DruidDataSourceFactory.getConnection();
		}
		tl.set(conn);
		return conn;
	}
	
	public static void startTransaction() {
		Connection conn = (Connection) tl.get();
		if(conn==null){
			conn = getConnectionOrCreateConnection();
		}
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void roolback() {
		Connection conn = (Connection) tl.get();
		try {
			conn.rollback();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void commit() {
		Connection conn = (Connection) tl.get();
		try {
			conn.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void release() {
		try {
			Connection conn = (Connection) tl.get();
			if(conn!=null){
				conn.close();
				tl.remove();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
