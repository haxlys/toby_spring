package chapter01._07.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import chapter01.user.domain.User;

public class UserDao {
	/*
	 * DaoFactory 클래스를 만들어 이전챕터에서 DaoUserTest가 담당하던 DB 커넥션 객체 생성이라는 관심을 넘겨주었다.
	 * 
	 * 이제 N사와 D사에 UserDao를 납품할때 UserDao, ConnectionMaker와 함께 DaoFactory도 제공한다.
	 * UserDao와 달리 DaoFactory는 소스를 제공하여 ConnectionMaker 구현 클래스 변경이 필요하면 DaoFactory를 수정해서
	 * 변경된 클래스를 생성해 설정해주도록 코드를 수정해주면 된다.
	 * 
	 * DaoFactory 분리 시 장점
	 * 애플리케이션의 컴포넌트 역할을 하는 오브젝트와 	// 변경되지 않을 것이라 기대하는 부분
	 * 애플리케이션의 구조를 결정하는 오브젝트를		// 변경될 여지가 충분히 있는 부분
	 * 분리했다는데 가장 큰 의미가 있다.
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
	 * DaoFactory를 좀 더 살펴보자
	 */
}

