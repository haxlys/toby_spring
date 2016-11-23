package chapter01._04.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import chapter01.user.domain.User;

public class UserDao {
	/*
	 * chapter1_3 UserDao는 추상메소드 였지만 다시 일반 클래스로 돌아왔다.
	 * 당연히 추상메소드도 사라졌다.
	 * 대신 커녁션을 담당하는 별도의 클래스가 하나 생겼다. SimpleConnectionMaker.
	 */
	
	private SimpleConnectionMaker simpleConnectionMaker;
	
	public UserDao(){
		simpleConnectionMaker = new SimpleConnectionMaker();
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		UserDao dao = new UserDao();
		
		User user = new User();
		user.setId("whiteship2");
		user.setName("백기선");
		user.setPassword("married");
		
		dao.add(user);
		
		System.out.println(user.getId() + " 등록 성공");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
		
		System.out.println(user2.getId() + " 조회 성공");
	}
	
	public void add(User user) throws ClassNotFoundException, SQLException{
		Connection c = simpleConnectionMaker.getConnection();
		
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
		Connection c = simpleConnectionMaker.getConnection();
		
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
	 * 하지만 이번에도 문제가 생겼다.
	 * 
	 * 문제 2가지
	 * 1. SimpleConnectionMaker 클래스의 메소드 문제.
	 * 		N사는 makeNewConnection() 으로 가져오지만 D사는 openConnection() 이란 이름으로 가져온다면 수정하는 번거로움이 생긴다.
	 * 2. DB 커넥션을 제공하는 클래스가 어떤 것인지를 UserDao가 구체적으로 알고 있어야 한다는 문제.
	 * 		납품한 회사에서 다른 클래스를 구현한다면 UserDao 자체를 다시 수정해야한다.
	 * 
	 * 인터페이스 사용으로 넘어가자.
	 */
}

