package com.joker.jokerORM.executor;

import java.sql.Connection;

import com.joker.jokerORM.rule.UpdateRule;


public class UpdateRuleHandler extends RuleHandler<Integer> {

	public UpdateRuleHandler() {
		super.rule = new UpdateRule();
	}
	@Override
	public Integer doExecute(String sql,Connection connection,Class beanClass) {
		return doSimpleExecute(sql,connection);
	}
	
}
