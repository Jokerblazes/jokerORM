package com.joker.jokerORM.executor;

import java.sql.Connection;

import com.joker.jokerORM.rule.InsertRule;


public class InsertRuleHandler extends RuleHandler<Integer> {

	public InsertRuleHandler() {
		super.rule = new InsertRule();
	}
	
	@Override
	public Integer doExecute(String sql,Connection connection,Class beanClass) {
		return doSimpleExecute(sql,connection);
	}
	
}
