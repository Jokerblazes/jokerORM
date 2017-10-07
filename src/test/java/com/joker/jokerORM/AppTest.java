package com.joker.jokerORM;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;

import com.joker.jokerORM.instance.SqlHelper;
import com.joker.jokerORM.interceptor.Interceptor;
import com.joker.jokerORM.proxy.SqlProxyFactory;
import com.joker.jokerORM.util.BeanId;
import com.joker.support.connection.DataSourceFactory;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	private void setDataSourced() {
		DataSource dataSource = DruidDataSourceFactory.getDataSource();
		DataSourceFactory dataSourceFactory = DataSourceFactory.getInstance();
		dataSourceFactory.setDataSource(dataSource);
	}
	
	@Test
	public void testUpdate() {
		setDataSourced();
		User user = new User();
		user.setUserid(4);
		user.setPassword("2");
		SqlHelper helper =  (SqlHelper) SqlProxyFactory.getInstance().getSession();
		int a = helper.update(user);
		System.out.println(a);
	}
//	
	@Test
	public void testInsert() {
		setDataSourced();
		User user = new User();
		user.setPassword("1");
		SqlHelper helper =  (SqlHelper) SqlProxyFactory.getInstance().getSession();
		int a = helper.insert(user);
		System.out.println(a);
	}
	
	
	@Test
	public void testDelete() {
		setDataSourced();
		User user = new User();
		user.setPassword("1");
		SqlHelper helper =  (SqlHelper) SqlProxyFactory.getInstance().getSession();
		int a = helper.delete(user);
		System.out.println(a);
	}
	
	
	@Test
	public void testSelectList() {
		setDataSourced();
		SqlHelper helper =  (SqlHelper) SqlProxyFactory.getInstance().getSession();
		List<User> users = helper.selectList(new User());
		System.out.println(users.size());
	}
	
	@Test
	public void testSelectListByPage() {
		setDataSourced();
		User user = new User();
//		user.setUserId(3);
		user.setPassword("1234454");
		List<Interceptor> list = new ArrayList();
		list.add(new PageInterceptor());
		SqlProxyFactory.getInstance().setInterceptorBeforeChain(list);
		SqlHelper helper =  (SqlHelper) SqlProxyFactory.getInstance().getSession();
		Page page = new Page();
		List<User> users = helper.selectList(new User(),page);
		System.out.println(page.getTotalNumber());
		System.out.println(users.size());
	}
	
	@Test
	public void selectById() {
		setDataSourced();
		BeanId id = new BeanId(4, "userId", User.class);
		SqlHelper helper =  (SqlHelper) SqlProxyFactory.getInstance().getSession();
		List<User> users = helper.selectV(id);
		System.out.println(users.size());
	}
	
	@Test
	public void testSelectBySql() {
		setDataSourced();
		String sql = "SELECT * FROM `user`";
		SqlHelper helper =  (SqlHelper) SqlProxyFactory.getInstance().getSession();
		List<User> users = helper.selectList(User.class,sql);
		System.out.println(users.size());
	}
	
	@Test
	public void testSelectBySqlWithPage() {
		setDataSourced();
		String sql = "SELECT * FROM `user`";
		List<Interceptor> list = new ArrayList();
		list.add(new PageInterceptor());
		SqlProxyFactory.getInstance().setInterceptorBeforeChain(list);
		SqlHelper helper =  (SqlHelper) SqlProxyFactory.getInstance().getSession();
		Page page = new Page();
		List<User> users = helper.selectList(User.class, sql, page);
		System.out.println(users.size());
		System.out.println(page.getTotalNumber());
	}
	
	
//	@Test
//	public void testFields() throws InstantiationException, IllegalAccessException {
//		Class beanClass = User.class;
//		Field[] fields = beanClass.getDeclaredFields();
//		System.out.println(fields.length);
//		Map<String,Field> fieldMap = new HashMap<>();
//		Map<String,Field> entityMap = new HashMap<>();
//		for (Field f : fields) {
//			if (f.getAnnotation(Entity.class) != null) {
//				entityMap.put(f.getName(), f);
//			} else {
//				fieldMap.put(f.getName(), f);
//			}
//		}
//		Field f = entityMap.get("page");
//		Class class2 = f.getType();
//		System.out.println(class2.newInstance());
//		
//	}
	
}
