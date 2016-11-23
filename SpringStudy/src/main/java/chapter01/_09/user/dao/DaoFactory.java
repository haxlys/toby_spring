package chapter01._09.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
public class DaoFactory {
	
	@Bean // 오브젝트 생성을 담당하는 IoC용 메소드라는 표시
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
	public ConnectionMaker connectionMaker(){
		return new DConnectionMaker();
	}
	
	/*
	 * DB 접속 정보가 바뀐다면 connectionMaker() 의 리턴값만 변경해 주면된다.
	 */
}
