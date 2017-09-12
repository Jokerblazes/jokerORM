package com.joker.jokerORM.proxy;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import com.joker.jokerORM.instance.SqlHelper;
import com.joker.jokerORM.interceptor.Interceptor;

public class SqlProxyFactory {
	private SqlProxyFactory() {
		interceptorAfterChain = new ArrayList<>();
		interceptorBeforeChain = new ArrayList<>();
	};
	
	private static SqlProxyFactory factory = new SqlProxyFactory();
	
	
	private List<Interceptor> interceptorBeforeChain;
	private List<Interceptor> interceptorAfterChain;
	
	public static SqlProxyFactory getInstance() {
		return factory;
	}
	
	public void setInterceptorBeforeChain(List<Interceptor> interceptorBeforeChain) {
		this.interceptorBeforeChain = interceptorBeforeChain;
	}

	public void setInterceptorAfterChain(List<Interceptor> interceptorAfterChain) {
		this.interceptorAfterChain = interceptorAfterChain;
	}

	public SqlHelper getSession() {
		SqlInvokeHandler handler = SqlInvokeHandler.getInstance();
		handler.setInterceptorAfterChain(interceptorAfterChain);
		handler.setInterceptorBeforeChain(interceptorBeforeChain);
//		return (SqlHelper2) Proxy.newProxyInstance(SqlhelperImpl.class.getClassLoader(),
//				SqlhelperImpl.class.getInterfaces(), handler);
		return (SqlHelper) Proxy.newProxyInstance(SqlHelper.class.getClassLoader(),
				new Class[] {SqlHelper.class}, handler);
	}
}
