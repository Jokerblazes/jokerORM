package com.joker.jokerORM;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidDataSourceFactory {
	private static  DruidDataSource dataSource; 

	static {
		dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver"); 
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf-8"); 
        dataSource.setUsername("root"); 
        dataSource.setPassword("123456"); 
        dataSource.setInitialSize(5);
        dataSource.setMaxActive(20);
        dataSource.setMinIdle(5);
        
	}
	

	public static DruidDataSource getDataSource() {
		return dataSource;
	}


}
