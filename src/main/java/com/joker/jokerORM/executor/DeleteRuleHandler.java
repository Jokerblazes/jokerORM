package com.joker.jokerORM.executor;

import java.sql.Connection;

import com.joker.jokerORM.rule.DeleteRule;


public class DeleteRuleHandler extends RuleHandler<Integer> {

	public DeleteRuleHandler() {
		super.rule = new DeleteRule();
	}
	
	@Override
	public Integer doExecute(String sql,Connection connection,Class beanClass) {
		return doSimpleExecute(sql,connection);
	}
	
}
