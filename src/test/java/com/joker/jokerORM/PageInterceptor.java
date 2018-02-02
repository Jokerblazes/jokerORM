package com.joker.jokerORM;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.joker.jokerORM.interceptor.Interceptor;
import com.joker.jokerORM.interceptor.Invocation;
import com.joker.jokerORM.util.MethodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageInterceptor implements Interceptor {
	private final static Logger logger = LoggerFactory.getLogger(PageInterceptor.class);

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
		logger.info("分页Sql {}",sql);
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
