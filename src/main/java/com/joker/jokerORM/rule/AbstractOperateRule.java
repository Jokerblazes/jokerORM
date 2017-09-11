package com.joker.jokerORM.rule;

import java.lang.reflect.Field;

import com.joker.jokerORM.util.MajorKey;

public abstract class AbstractOperateRule {
	
	
	String getTableName(Class objectClass) {
		return objectClass.getSimpleName();
	}
	
	boolean isNull(Object object) {
		if (object == null) 
			return false;
		else {
			if (object instanceof Number && object.equals("0"))
				return false;
		}
		return true;
	}
	
	
	abstract String createSqlHead();
	abstract String createSqlAttach(Class objectClass);
	abstract String createSqlField(Object value,String name);
	abstract void handleStringBuffer(StringBuffer buffer,String keyName,Object keyValue);
	
	public String createSql(Object object) {
		String head = createSqlHead();
		StringBuffer stringBuffer = new StringBuffer();
		Class objectClass = object.getClass();
		String attach = createSqlAttach(objectClass);
		Field[] fields = objectClass.getDeclaredFields();
		String keyName = null;
		Object keyValue = null;
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				MajorKey key = field.getAnnotation(MajorKey.class);
				if (key != null) {
					keyName = key.value();
					keyValue = field.get(object);
					continue;
				}
				Object value = field.get(object);
				String name = field.getName();
				if (isNull(value))
					stringBuffer.append(createSqlField(value, name));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		handleStringBuffer(stringBuffer, keyName, keyValue);
		String tail = stringBuffer.toString();
		return head + attach + tail;
	}
}
