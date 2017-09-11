package com.joker.jokerORM.interceptor;

import java.sql.Connection;

public interface Interceptor {
	public Object intercept(Invocation invocation);
}
