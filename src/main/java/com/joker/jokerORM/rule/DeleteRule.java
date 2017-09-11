package com.joker.jokerORM.rule;


public class DeleteRule extends AbstractOperateRule {



	@Override
	String createSqlHead() {
		return "DELETE FROM ";
	}


	@Override
	String createSqlAttach(Class objectClass) {
		return "`" + getTableName(objectClass) + "` WHERE ";
	}

	@Override
	String createSqlField(Object value, String name) {
		return "`" + name +"`=" + value + " AND ";
	}

	@Override
	void handleStringBuffer(StringBuffer buffer,String keyName,Object keyValue) {
		if (!isNull(keyValue)) {
			buffer.append("`" + keyName + "`=" + "'" + keyValue + "'");
		} else {
			buffer.delete(buffer.length() - 5, buffer.length());
		}
	}


}
