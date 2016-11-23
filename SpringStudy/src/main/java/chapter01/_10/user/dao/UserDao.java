package chapter01._10.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import chapter1.user.domain.User;

public class UserDao {
	
	// 싱글톤을 위한 static 변수
	private static UserDao INSTANCE;
	
	private ConnectionMaker connectionMaker;
	
	public UserDao(ConnectionMaker connectionMaker){
		this.connectionMaker = connectionMaker;
	}
	
	// 싱글톤을 변수를 가져다 쓰기 위한 메소드
	public static synchronized UserDao getInstance() {
		if (INSTANCE == null) INSTANCE = new UserDao(???); //싱글톤 설명을 위한 것이라 ???는 생략함
		return INSTANCE;
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
	 * 싱글톤 패턴의 한계
	 * 1. private 생성자를 갖고 있기 때문에 상속할 수 없다.
	 * 2. 싱글톤은 테스트하기가 힘들다.
	 * 3. 서버환경에서는 싱글톤이 하나만 만들어지는 것을 보장하지 못한다.
	 * 4. 싱글톤의 사용은 전역 상태를 만들 수 있기 때문에 바람직하지 못하다.
	 * 
	 */
}

