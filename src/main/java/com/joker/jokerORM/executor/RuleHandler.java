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

	//给更新、删除、添加使用
	int doSimpleExecute(String sql,Connection connection) {
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
