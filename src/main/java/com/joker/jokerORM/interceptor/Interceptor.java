package com.joker.jokerORM.interceptor;

/**
 * 过滤器接口(用户可实现)
 * @author joker
 * @author joker
 * {@link https://github.com/Jokerblazes/jokerORM.git}
 */
public interface Interceptor {
	/**
	 * 过滤实现
	 * @param invocation
	 * @return
	 * @author joker
	 * {@link https://github.com/Jokerblazes/jokerORM.git}
	 */
	Object intercept(Invocation invocation);
}
