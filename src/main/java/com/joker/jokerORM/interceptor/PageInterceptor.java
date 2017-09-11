package com.joker.jokerORM.interceptor;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.joker.jokerORM.Page;
import com.joker.jokerORM.util.MethodType;

public class PageInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) {
		Method method = invocation.getMethod();
		MethodType type = method.getAnnotation(MethodType.class);
		if (type.type() != 2)
			return null;
		Page page = null;
		Object[] args = invocation.getArgs();
		for (Object o : args)
			if (o instanceof Page) {
				page = (Page) o;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
}
