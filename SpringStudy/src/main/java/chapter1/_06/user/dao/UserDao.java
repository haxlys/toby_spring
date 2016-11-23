package chapter1._06.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import chapter1.user.domain.User;

public class UserDao {
	/*
	 * Main 메소드를 가지고 있는 클래스(UserDaoTest)를 따로 만들어 그 클래스(클라이언트)에게 책임을 부여했다.
	 * UserDao 생성자는 이제 클라이언트가 주는 커넥션 객체를 가지고 작업하면 된다.
	 */
	
	private ConnectionMaker connectionMaker;
	
	public UserDao(ConnectionMaker connectionMaker){
		this.connectionMaker = connectionMaker;
	}
	
	public void add(User user) throws ClassNotFoundException, SQLException{
		Connection c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement(
				"insert into users(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement(
				"select * from users where id = ?");
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		
		rs.close();
		ps.close();
		c.close();
		
		return user;
	}
	
	/* 
	 * 하지만 main 역시 테스트라는 관심을 위해 만들어둔 것이므로
	 * ConnectionMaker 구현 클래스 사용 관심까지 가지고 있는 것은 문제가 있다.
	 * 
	 * 이것도 분리하자.
	 */
	
	/*
	 * 개방 폐쇄 원칙(열닫원칙)
	 * 클래스나 모듈은 확장이 가능해야하고 변경에는 폐쇄적이야 한다.
	 * 
	 * SOLID 원칙 추천
	 * Java 프로그래머를 위한 UML 실전에서는 이것만 쓴다(UML for Java Programmer) - 밥 마틴
	 * 소프트웨어 개발의 지혜: 원칙, 디자인 패턴, 실천 방법(Agile Software Development, Principles, Patterns, and Practices) - 밥 마틴
	 * 
	 * SRP(The Single Respnsibility Priciple): 단일 책임 원칙
	 * OCP(The Open Closed Pricciple): 개방 폐쇄 원칙
	 * LSP(The Liskov Substitution Principle): 리스코프 치환 원칙
	 * ISP(The Interface Segregation Principle): 인터페이스 분리 원칙
	 * DIP(The Dependency Inversion Principle): 의존관계 역전 원칙
	 */
}

