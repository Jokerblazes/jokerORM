package com.joker.jokerORM.instance;

import java.util.List;

import com.joker.jokerORM.util.MethodType;
import com.joker.jokerORM.util.Param;

public interface SqlHelper<V> {
	
	@MethodType(value="insert")
	public int insert(V v);
	
	@MethodType(value="update")
	public int update(V v);
	
	@MethodType(value="delete")
	public int delete(V v);
	
	
	@MethodType(value="select")
	public List<V> selectList(V v);
	
	@MethodType(value="select",type = 2)
	public List<V> selectList(V v,Object page);
	
//	@MethodType(value="select")
//	public V selectInfo(V v);
	
	@MethodType(value="insert",type=1)
	public int insert(String sql);

	@MethodType(value="update",type=1)
	public int update(String sql);

	@MethodType(value="delete",type=1)
	public int delete(String sql);

	@MethodType(value="select",type=1)
	public List<V> selectList(Class beanClass,String sql);
	
	@MethodType(value="select",type=2)
	public List<V> selectList(Class beanClass,String sql,Object page);
	
//	@MethodType(value="select",type=1)
//	public V selectInfo(String sql);
	
	
}
