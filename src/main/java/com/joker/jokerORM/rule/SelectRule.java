package com.joker.jokerORM.rule;


public class SelectRule extends AbstractOperateRule {



	@Override
	String createSqlHead() {
		return "SELECT * FROM ";
	}


	@Override
	String createSqlAttach(Class objectClass) {
		return getTableName(objectClass) + " WHERE ";
	}

	@Override
	String createSqlField(Object value, String name) {
		return "`" + name + "`='" + value + "' AND ";
	}

	@Override
	void handleStringBuffer(StringBuffer buffer,String keyName,Object keyValue) {
		buffer.append("1=1");
		if (isNotNull(keyValue)) 
			buffer.append(" AND " + keyName + "=" + keyValue);
	}


	


}
