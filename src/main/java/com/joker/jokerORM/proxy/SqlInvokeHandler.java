package com.joker.jokerORM.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joker.jokerORM.executor.DeleteRuleHandler;
import com.joker.jokerORM.executor.InsertRuleHandler;
import com.joker.jokerORM.executor.RuleHandler;
import com.joker.jokerORM.executor.SelectRuleHandler;
import com.joker.jokerORM.executor.UpdateRuleHandler;
import com.joker.jokerORM.interceptor.Interceptor;
import com.joker.jokerORM.interceptor.Invocation;
import com.joker.jokerORM.interceptor.PageInterceptor;
import com.joker.jokerORM.util.DruidDataSourceFactory;
import com.joker.jokerORM.util.MethodType;
import com.joker.jokerORM.util.TransactionUtil;

public class SqlInvokeHandler implements InvocationHandler{
	private static final Logger logger = LoggerFactory.getLogger(SqlInvokeHandler.class); 
	private List<Interceptor> interceptorBeforeChain;
	private List<Interceptor> interceptorAfterChain;
	private Map<String,RuleHandler> ruleHandlerMap;
	
	
	
	private SqlInvokeHandler() {
		logger.info("实例化 {}" , "开启代理！");
		ruleHandlerMap = new HashMap<>();
		ruleHandlerMap.put("insert", new InsertRuleHandler());
		ruleHandlerMap.put("delete", new DeleteRuleHandler());
		ruleHandlerMap.put("update", new UpdateRuleHandler());
		ruleHandlerMap.put("select", new SelectRuleHandler());
		interceptorAfterChain = new ArrayList<>();
		interceptorBeforeChain = new ArrayList<>();
		interceptorBeforeChain.add(new PageInterceptor());
	}
	
	private static class SqlHandlerHolder {
		private static final SqlInvokeHandler handler = new SqlInvokeHandler();
	}
	
	public final static SqlInvokeHandler getInstance() {
		return SqlHandlerHolder.handler;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object[] results = new Object[1];
		boolean flag = true;//判断是否事务
		Connection connection = null;
		try {
			//1:动态生成sql
			Class beanClass = null;
			MethodType type = method.getAnnotation(MethodType.class);
			RuleHandler handler = ruleHandlerMap.get(type.value());
			String sql = null;
			if (type.type() != 1) {
				beanClass = args[0].getClass();
				sql = handler.createSql(args[0]);
			} else {
				beanClass = (Class) args[0];
				sql = (String) args[1];
			}
			connection = TransactionUtil.getConnection();
			if (connection == null) {
				connection = DruidDataSourceFactory.getConnection();
				flag = false;
			}
			//2：执行前置过滤器链
			for (Interceptor interceptor : interceptorBeforeChain) {
				interceptor.intercept(new Invocation(args, connection, method,sql));
			}
			logger.info("Sql {}" , sql);
			//3:动态执行
			//先从事务中getConnection如果没有连接，证明当前不存在这事务操作
			//则直接从datasource中获取connection
			//若有则中事务中获取connection
			logger.info("执行sql语句 {}", "开始！");
			results[0] = handler.doExecute(sql, connection,beanClass);
			logger.info("执行sql语句 {}", "结束！");
			logger.info("结果 {}", results[0]);
			return results[0];
		}
		finally {
			if (flag == false) {
				connection.close();
				logger.info("关闭 {}" , "自动关闭数据库连接！");
			}
			//4:调用后置过滤器
			for (Interceptor interceptor : interceptorAfterChain) {
				interceptor.intercept(new Invocation(results, null, null,null));
			}
			logger.info("后置过滤器 {}" , "调用结束！");
		}
		
	}
	
	

}
