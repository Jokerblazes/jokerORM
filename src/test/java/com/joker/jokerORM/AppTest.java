package com.joker.jokerORM;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.joker.jokerORM.instance.SqlHelper;
import com.joker.jokerORM.proxy.SqlProxyFactory;
import com.joker.jokerORM.util.Entity;

/**
 * Unit test for simple App.
 */
public class AppTest {
	@Test
	public void testUpdate() {
		User user = new User();
		user.setUserId(1);
		user.setPassword("1");
		SqlHelper helper =  (SqlHelper) SqlProxyFactory.getSession();
		int a = helper.update(user);
		System.out.println(a);
	}
	
	@Test
	public void testInsert() {
		User user = new User();
		user.setPassword("1");
		SqlHelper helper =  (SqlHelper) SqlProxyFactory.getSession();
		int a = helper.insert(user);
		System.out.println(a);
	}
	
	
	@Test
	public void testDelete() {
		User user = new User();
		user.setPassword("123456");
		SqlHelper helper =  (SqlHelper) SqlProxyFactory.getSession();
		int a = helper.delete(user);
		System.out.println(a);
	}
	
	
	@Test
	public void testSelectList() {
		SqlHelper helper =  (SqlHelper) SqlProxyFactory.getSession();
		List<User> users = helper.selectList(new User());
		System.out.println(users.size());
	}
	
	@Test
	public void testSelectListByPage() {
		User user = new User();
		user.setUserId(3);
		user.setPassword("1234454");
		SqlHelper helper =  (SqlHelper) SqlProxyFactory.getSession();
		Page page = new Page();
		List<User> users = helper.selectList(new User(),page);
		System.out.println(page.getTotalNumber());
		System.out.println(users.size());
	}
	
	@Test
	public void testSelectBySql() {
		String sql = "SELECT * FROM `user`,hotInfo";
		SqlHelper helper =  (SqlHelper) SqlProxyFactory.getSession();
		List<User> users = helper.selectList(User.class,sql);
		System.out.println(users.get(0).getHotInfo().getHotId());
//		System.out.println(users.size());
	}
	
	@Test
	public void testFields() throws InstantiationException, IllegalAccessException {
		Class beanClass = User.class;
		Field[] fields = beanClass.getDeclaredFields();
		System.out.println(fields.length);
		Map<String,Field> fieldMap = new HashMap<>();
		Map<String,Field> entityMap = new HashMap<>();
		for (Field f : fields) {
			if (f.getAnnotation(Entity.class) != null) {
				entityMap.put(f.getName(), f);
			} else {
				fieldMap.put(f.getName(), f);
			}
		}
		Field f = entityMap.get("page");
		Class class2 = f.getType();
		System.out.println(class2.newInstance());
		
	}
	
}
