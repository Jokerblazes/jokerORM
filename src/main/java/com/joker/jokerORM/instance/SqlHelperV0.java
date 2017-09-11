package com.joker.jokerORM.instance;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joker.jokerORM.rule.AbstractOperateRule;
import com.joker.jokerORM.rule.DeleteRule;
import com.joker.jokerORM.rule.InsertRule;
import com.joker.jokerORM.rule.SelectRule;
import com.joker.jokerORM.rule.UpdateRule;
import com.joker.jokerORM.util.DruidDataSourceFactory;

public class SqlHelperV0<V> {
	//静态内部类实现单例（实现lazy加载）
	private SqlHelperV0() {
		ruleMap = new HashMap<>();
		ruleMap.put("insert", new InsertRule());
		ruleMap.put("deletes", new DeleteRule());
		ruleMap.put("update", new UpdateRule());
		ruleMap.put("select", new SelectRule());
	};
	private static class SqlHelperHolder {
		private static final SqlHelperV0 sqlHelper = new SqlHelperV0();
	}
	
	public final static SqlHelperV0 getInstance() {
		return SqlHelperHolder.sqlHelper;
	}
	private Map<String,AbstractOperateRule> ruleMap;
	private Connection conn;
	
	
	public int insertSql(V object) {
		Connection con = DruidDataSourceFactory.getConnection();
		String sql = ruleMap.get("insert").createSql(object);
		System.out.println(sql);
		return executeUpdateSql(con, sql);
	}
	
	
	public int updatesSql(V object) {
		Connection con = DruidDataSourceFactory.getConnection();
		String sql = ruleMap.get("update").createSql(object);
		System.out.println(sql);
		return executeUpdateSql(con, sql);
		//调用数据库操作得到一个RestltSet
	}
	
	public int deleteSql(V object) {
		Connection con = DruidDataSourceFactory.getConnection();
		String sql = ruleMap.get("delete").createSql(object);
		System.out.println(sql);
		return executeUpdateSql(con, sql);
	}
	
	private int executeUpdateSql(Connection conn,String sql) {
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			return ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<V> selectList(V object) {
		Connection con = DruidDataSourceFactory.getConnection();
		String sql = ruleMap.get("select").createSql(object);
		System.out.println(sql);
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//调用数据库操作得到一个RestltSet
		ArrayList<V> list = new ArrayList<>();
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			while (rs.next()) {
				Class resultClass = object.getClass();
				V v = (V) resultClass.newInstance();
				ResultSetMetaData meta = rs.getMetaData();
				for (int i = 0; i < meta.getColumnCount(); i++) {
					String columnName = meta.getColumnName(i);
					Object value = rs.getObject(i);
					Field field = resultClass.getDeclaredField(columnName);
					field.setAccessible(true);
					field.set(v, value);
				}
				list.add(v);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return list;
	}
	
	public V selectInfo(V object) {
		Connection con = DruidDataSourceFactory.getConnection();
		String sql = ruleMap.get("select").createSql(object);
		System.out.println(sql);
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//调用数据库操作得到一个RestltSet
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		V v = null;
		try {
			while (rs.next()) {
				Class resultClass = object.getClass();
				v = (V) resultClass.newInstance();
				ResultSetMetaData meta = rs.getMetaData();
				for (int i = 0; i < meta.getColumnCount(); i++) {
					String columnName = meta.getColumnName(i+1);
					Object value = rs.getObject(i+1);
					Field field = resultClass.getDeclaredField(columnName);
					field.setAccessible(true);
					field.set(v, value);
				}
				break;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return v;
	}
	
	
	public V doScriptSqlInfo(String sql,Class beanClass) {
		Connection con = DruidDataSourceFactory.getConnection();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		V v = null;
		try {
			while (rs.next()) {
				v = (V) beanClass.newInstance();
				ResultSetMetaData meta = rs.getMetaData();
				for (int i = 0; i < meta.getColumnCount(); i++) {
					String columnName = meta.getColumnName(i);
					Object value = rs.getObject(i);
					Field field = beanClass.getDeclaredField(columnName);
					field.setAccessible(true);
					field.set(v, value);
				}
				break;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return v;
	}
	
	public List<V> doScriptSqlList(String sql,Class beanClass) {
		List<V> list = new ArrayList<>();
		Connection con = DruidDataSourceFactory.getConnection();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			while (rs.next()) {
				V v = (V) beanClass.newInstance();
				ResultSetMetaData meta = rs.getMetaData();
				for (int i = 0; i < meta.getColumnCount(); i++) {
					String columnName = meta.getColumnName(i);
					Object value = rs.getObject(i);
					Field field = beanClass.getDeclaredField(columnName);
					field.setAccessible(true);
					field.set(v, value);
				}
				list.add(v);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	
//	private void sqlToNodeList(String sql) {
//		StringBuffer buffer = new StringBuffer();
//		Document doc = null;
//		try {
//			doc = DocumentHelper.parseText(sql);
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Element rootElt = doc.getRootElement();
//		buffer.append(rootElt.getText());
//		getElement(rootElt);
//	}
//	private void getElement(Element parentElement) {  
//		List<Element> sonElemetList = parentElement.elements();
//        for (Element sonElement : sonElemetList) {  
//                if (sonElement.elements().size() != 0) {  
//                    System.out.println(sonElement.getName() + ":");  
//                    getElement(sonElement);  
//                }else{  
//                Attribute attribute = sonElement.attribute(0);
//                System.out.println(attribute.getValue() + ":" + attribute.getName());
//                    System.out.println(sonElement.getName() + ":"+ sonElement.getText());  
//                }  
//  
//        }  
//    }  
	
	
}
