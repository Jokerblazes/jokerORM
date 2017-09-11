package com.joker.jokerORM.executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.joker.jokerORM.rule.AbstractOperateRule;

public abstract class RuleHandler<V> {
	AbstractOperateRule rule;
	
	public abstract V doExecute(String sql,Connection connection,Class beanClass);
	
	
	public AbstractOperateRule getRule() {
		return rule;
	}
	
	public String createSql(Object object) {
		return rule.createSql(object);
	}
	
	int doSimpleExecute(String sql,Connection connection) {
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			return ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
