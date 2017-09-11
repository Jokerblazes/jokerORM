package com.joker.jokerORM.rule;


public class UpdateRule extends AbstractOperateRule {
	


	@Override
	String createSqlHead() {
		return "UPDATE ";
	}


	@Override
	String createSqlAttach(Class objectClass) {
		return "`" + getTableName(objectClass) + "` SET ";
	}

	@Override
	String createSqlField(Object value, String name) {
		return "`" + name + "` ='" + value + "' AND ";
	}

	@Override
	void handleStringBuffer(StringBuffer buffer,String keyName,Object keyValue) {
		buffer.delete(buffer.length() - 4, buffer.length());
		buffer.append("WHERE ");
		buffer.append("`"+ keyName + "`=" + keyValue);
	}


}
