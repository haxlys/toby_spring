package chapter1._11.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {
	
	@Bean 
	public UserDao userDao(){
		return new UserDao(connectionMaker()); 
	}
	
	@Bean
	public ConnectionMaker connectionMaker() {
		return new CountingConnectionMaker(realConnectionMaker());
	}
	
	public ConnectionMaker realConnectionMaker(){
		return new DConnectionMaker();
	}
	
	// connectionMaker() -> realConnectionMaker() -> DConnectionMaker 객체를 가져온다.
	//																				ㅣ
	//																CountingConnectionMaker 객체에는 접속 정보가 담겨있다. <- DConnectionMaker 객체를 사용하여 DB접속
}
