package com.joker.jokerORM;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.joker.jokerORM.interceptor.Interceptor;
import com.joker.jokerORM.interceptor.Invocation;
import com.joker.jokerORM.util.MethodType;

public class PageInterceptor implements Interceptor {

	public Object intercept(Invocation invocation) {
//		Method method = invocation.getMethod();
//		MethodType type = method.getAnnotation(MethodType.class);
//		if (type.type() != 2)
//			return null;
		Page page = null;
		Object[] args = invocation.getArgs();
		for (Object o : args)
			if (o instanceof Page) {
				page = (Page) o;
				break;
			}
		String sql = invocation.getSql();
		sql = sql + " LIMIT " + page.getDbIndex() + "," + page.getPageNumber();
		System.out.println(sql);
		String finalSql = "select count(*) from (" + sql + ")a";
		Connection connection = invocation.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(finalSql);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				page.setTotalNumber(set.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
}
