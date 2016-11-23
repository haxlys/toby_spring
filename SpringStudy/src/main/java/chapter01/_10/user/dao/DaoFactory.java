package chapter01._10.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import chapter01._11.user.dao.ConnectionMaker;
import chapter01._11.user.dao.CountingConnectionMaker;
import chapter01._11.user.dao.DConnectionMaker;
import chapter01._11.user.dao.UserDao;

@Configuration
public class DaoFactory {
	
	@Bean
	public UserDao userDao(){
		return new UserDao(connectionMaker()); 
	}
	
	public UserDao accountDao(){ 
		return new UserDao(connectionMaker()); 
	}
	
	public UserDao messageDao(){
		return new UserDao(connectionMaker()); 
	}
	
	@Bean
	public ConnectionMaker connectionMaker() {
		return new CountingConnectionMaker(realConnectionMaker());
	}
	
	//public ConnectionMaker connectionMaker(){
	public ConnectionMaker realConnectionMaker(){
		return new DConnectionMaker();
	}
	
	/*
	 * 변경된 점은  connectionMaker 메소드 이름이 realConnectionMaker 바뀜
	 */
}
