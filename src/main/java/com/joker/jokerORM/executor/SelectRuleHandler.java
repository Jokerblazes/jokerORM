package com.joker.jokerORM.executor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.joker.jokerORM.rule.SelectRule;
import com.joker.jokerORM.util.Entity;
import com.sun.tools.doclets.formats.html.SectionName;



public class SelectRuleHandler<V> extends RuleHandler<List<V>> {
	private List<V> vList;
	
	public SelectRuleHandler() {
		super.rule = new SelectRule();
	}
	@Override
	public List<V> doExecute(String sql,Connection connection,Class beanClass) {
		vList = new ArrayList<>();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		//调用数据库操作得到一个RestltSet
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		handleResultSet(rs, beanClass);
		return vList;
	}
	
	/**
	 * 处理ResultSet
	 * @param rs
	 * @param beanClass
	 */
	private void handleResultSet(ResultSet rs,Class beanClass) {
		V v = null;
		ResultSetMetaData meta = null;
		List<String> columnNames = new ArrayList<>();
		Map<String,String> tablesMap = new HashMap<>();
		//1:实例化bean
		try {
			v = (V) beanClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		//2:初始化columnNames，tablesMap
		handleResultSetMetaData(rs, columnNames, tablesMap);
		List<Map<String, Field>> fields = getFields(beanClass.getDeclaredFields());
		//3:ResultSet-bean映射
		try {
			while (rs.next()) {
				try {
					setV(v, rs, columnNames, tablesMap,fields);
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
						| InstantiationException | SQLException e) {
					e.printStackTrace();
				}
				vList.add(v);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 组装ResultSetMetaData
	 * @param rs
	 * @param columnNames
	 * @param tablesMap
	 */
	private void handleResultSetMetaData(ResultSet rs,List<String> columnNames,Map<String,String> tablesMap) {
		try {
			ResultSetMetaData meta = rs.getMetaData();
			for (int i = 0; i < meta.getColumnCount(); i++) {
				columnNames.add(meta.getColumnName(i+1));
				tablesMap.put(meta.getColumnName(i+1), meta.getTableName(i+1));
			}
		} catch (SecurityException | IllegalArgumentException 
				| SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param fields 对象中的fields
	 * @return List<Map<String,Field>> list(0) 存储JDK基础类的field list(1) 存储用户类field
	 */
	private List<Map<String,Field>> getFields(Field[] fields) {
		List<Map<String,Field>> filedList = new ArrayList<>();
		Map<String,Field> fieldMap = new HashMap<>();
		Map<String,Field> entityMap = new HashMap<>();
		for (Field f : fields) {
			Entity entity = f.getAnnotation(Entity.class);
			if (entity != null) {
				entityMap.put(entity.value(), f);
			} else {
				fieldMap.put(f.getName(), f);
			}
		}
		filedList.add(fieldMap);
		filedList.add(entityMap);
		return filedList;
	}
	
	/**
	 * 给bean赋值
	 * @param v
	 * @param rs
	 * @param columnNames
	 * @param tableNames
	 * @param filedList
	 * @throws SQLException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private void setV(V v,ResultSet rs,List<String> columnNames,Map<String,String> tableNames,List<Map<String,Field>> filedList) throws SQLException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		Class beanClass = v.getClass();
		if (filedList == null) {
			Field[] fields = beanClass.getDeclaredFields();
			filedList = getFields(fields);
		}
		Map<String,Field> fieldMap = filedList.get(0);
		Map<String,Field> entityMap = filedList.get(1);
		for (String columnName: columnNames) {
			Field f = fieldMap.get(columnName);
			Field sonField = entityMap.get(tableNames.get(columnName));
			if (f != null) {
				Object value = rs.getObject(columnName);
				f.setAccessible(true);
				f.set(v, value);
			} else if (sonField != null) {
				V sonV =  (V) sonField.getType().newInstance();
				setV(sonV, rs, columnNames, tableNames,null);
				sonField.setAccessible(true);
				sonField.set(v, sonV);
			}
		}
		
	}
	
}
