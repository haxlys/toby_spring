package chapter1._14.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import chapter1._14.user.dao.UserDao;

import javax.sql.DataSource;

public class DaoFactory {
	
	@Bean
	public UserDao userDao(){
		UserDao userDao = new UserDao();
		userDao.setConnectionMaker(dataSource());
		return userDao;
	}
	/*
	 @Bean
	 public UserDao userDao(){
		UserDao userDao = new UserDao();
		userDao.setConnectionMaker(connectionMaker());
		return userDao;
	}*/
	
	@Bean
	public DataSource dataSource(){
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
		dataSource.setUrl("jdbc:mysql://localhost/test");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		
		return dataSource;
	}
	/*
	@Bean
	public ConnectionMaker connectionMaker(){
		return new DConnectionMaker();
	}*/
	
	
	
	
}
