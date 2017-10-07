package com.joker.jokerORM.rule;


public class InsertRule extends AbstractOperateRule {
	private StringBuffer stringBuffer = null;




	@Override
	String createSqlHead() {
		stringBuffer = new StringBuffer();
		return "INSERT INTO `";
	}




	@Override
	String createSqlAttach(Class objectClass) {
		return getTableName(objectClass) + "` (";
	}



	@Override
	String createSqlField(Object value, String name) {
		stringBuffer.append("'" + value + "',");
		return "`" + name + "`,";
	}



	@Override
	void handleStringBuffer(StringBuffer buffer,String keyName,Object keyValue) {
		buffer.deleteCharAt(buffer.length() - 1);
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		buffer.append(") VALUES (");
		stringBuffer.append(")");
		buffer.append(stringBuffer.toString());
	}







	

}
