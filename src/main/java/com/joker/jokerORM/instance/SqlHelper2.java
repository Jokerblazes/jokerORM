package com.joker.jokerORM.instance;

import com.joker.jokerORM.util.MethodType;

public interface SqlHelper2<V> {
	
	@MethodType(value="insert")
	public String insertSql(V object);
	
	@MethodType(value="update")
	public String updatesSql(V object);
	
	@MethodType(value="delete")
	public String deleteSql(V object);
	
	@MethodType(value="select")
	public String selectList(V object);
	
	@MethodType(value="select")
	public String selectInfo(V object);
	
	@MethodType(value="delete")
	public String doScriptSqlInfo();
	
	@MethodType(value="delete")
	public String doScriptSqlList();

	
	
}
