package com.joker.jokerORM.proxy;

import java.lang.reflect.Proxy;

import com.joker.jokerORM.instance.SqlHelper;

public class SqlProxyFactory {
	public static SqlHelper getSession() {
		SqlInvokeHandler handler = SqlInvokeHandler.getInstance();
//		return (SqlHelper2) Proxy.newProxyInstance(SqlhelperImpl.class.getClassLoader(),
//				SqlhelperImpl.class.getInterfaces(), handler);
		return (SqlHelper) Proxy.newProxyInstance(SqlHelper.class.getClassLoader(),
				new Class[] {SqlHelper.class}, handler);
	}
}
