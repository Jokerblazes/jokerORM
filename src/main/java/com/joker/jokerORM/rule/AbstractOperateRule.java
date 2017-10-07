package com.joker.jokerORM.rule;

import java.lang.reflect.Field;

import com.joker.jokerORM.util.BeanId;
import com.joker.jokerORM.util.MajorKey;

public abstract class AbstractOperateRule {
	
	
	String getTableName(Class objectClass) {
		return objectClass.getSimpleName();
	}
	
	boolean isNotNull(Object object) {
		if (object == null) 
			return false;
		else 
			if (object instanceof Number && object.equals(0))
				return false;
		return true;
	}
	
	
	abstract String createSqlHead();
	abstract String createSqlAttach(Class objectClass);
	abstract String createSqlField(Object value,String name);
	abstract void handleStringBuffer(StringBuffer buffer,String keyName,Object keyValue);
	public void createSqlbyId(BeanId beanId,StringBuffer stringBuffer) {
		int id = beanId.getId();
		String name = beanId.getIdName();
		Class clazz = beanId.getBeanClass();
		stringBuffer.append(createSqlHead());
		stringBuffer.append(createSqlAttach(clazz));
		handleStringBuffer(stringBuffer, name, id);
	}
	
	public String createSql(Object object) {
		StringBuffer stringBuffer = new StringBuffer();
		if (object instanceof BeanId) {
			createSqlbyId((BeanId)object,stringBuffer);
			return stringBuffer.toString();
		}
		String head = createSqlHead();
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
				if (isNotNull(value))
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
