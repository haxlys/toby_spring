package chapter1_12.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
	
	@Bean
	public UserDao userDao(){
		UserDao userDao = new UserDao();
		userDao.setConnectionMaker(connectionMaker()); // 수정자 메소드를 통한 DI
		return userDao;
	}
	/*
	public UserDao userDao(){
		return new UserDao(connectionMaker());
	}*/
	
	@Bean
	public ConnectionMaker connectionMaker(){
		return new DConnectionMaker();
	}
}
