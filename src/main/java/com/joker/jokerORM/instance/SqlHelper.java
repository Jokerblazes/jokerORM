package com.joker.jokerORM.instance;

import java.util.List;

import com.joker.jokerORM.util.MethodType;

public interface SqlHelper<V> {
	
	/**
	 * 插入v对象
	 * @param v
	 * @return
	 * @author joker
	 * {@link https://github.com/Jokerblazes/jokerORM.git}
	 */
	@MethodType(value="insert")
	public int insert(V v);
	
	/**
	 * 更新v对象
	 * @param v
	 * @return
	 * @author joker
	 * {@link https://github.com/Jokerblazes/jokerORM.git}
	 */
	@MethodType(value="update")
	public int update(V v);
	
	/**
	 * 删除v对象
	 * @param v
	 * @return
	 * @author joker
	 * {@link https://github.com/Jokerblazes/jokerORM.git}
	 */
	@MethodType(value="delete")
	public int delete(V v);
	
	/**
	 * 查询V List
	 * @param v
	 * @return
	 * @author joker
	 * {@link https://github.com/Jokerblazes/jokerORM.git}
	 */
	@MethodType(value="select")
	public List<V> selectList(V v);
	
	/**
	 * 分页查询V List
	 * @param v
	 * @param page
	 * @return
	 */
	@MethodType(value="select",type = 2)
	public List<V> selectList(V v,Object page);
	
//	@MethodType(value="select")
//	public V selectInfo(V v);
	
	/**
	 * 插入（直接使用sql）
	 * @param sql
	 * @return
	 * @author joker
	 * {@link https://github.com/Jokerblazes/jokerORM.git}
	 */
	@MethodType(value="insert",type=1)
	public int insert(String sql);

	/**
	 * 更新（直接使用sql）
	 * @param sql
	 * @return
	 * @author joker
	 * {@link https://github.com/Jokerblazes/jokerORM.git}
	 */
	@MethodType(value="update",type=1)
	public int update(String sql);

	/**
	 * 删除（直接使用sql）
	 * @param sql
	 * @return
	 * @author joker
	 * {@link https://github.com/Jokerblazes/jokerORM.git}
	 */
	@MethodType(value="delete",type=1)
	public int delete(String sql);

	/**
	 * 查询V List（直接使用Sql）
	 * @param beanClass
	 * @param sql
	 * @return
	 * @author joker
	 * {@link https://github.com/Jokerblazes/jokerORM.git}
	 */
	@MethodType(value="select",type=1)
	public List<V> selectList(Class beanClass,String sql);
	
	/**
	 * 分页查询V List（直接使用Sql）
	 * @param beanClass
	 * @param sql
	 * @return
	 * @author joker
	 * {@link https://github.com/Jokerblazes/jokerORM.git}
	 */
	@MethodType(value="select",type=2)
	public List<V> selectList(Class beanClass,String sql,Object page);
	
//	@MethodType(value="select",type=1)
//	public V selectInfo(String sql);
	
	
}
