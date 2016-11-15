package chapter1_05.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import spring.user.domain.User;

public class UserDao {
	/*
	 * 전 챕터와 다른점은 인터페이스를 사용했다.
	 * 납품받은 회사는 ConnectionMaker를 구현하여 DConnectionMaker 를 만들어 DB 접속 정보를 자기네 회사 설정에 맞게 작성했다.
	 * 인터페이스를 통해서 메소드명만 공유할 뿐 내용은 각자 알아서 사용한 것이다.
	 */
	
	private ConnectionMaker connectionMaker;
	
	public UserDao(){
		connectionMaker = new DConnectionMaker();
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
	 * 하지만 이번에도 문제가 생겼다.
	 * UserDao 생성자자에 DConnectionMaker 클래스를 사용하다는 것.
	 * 여전히 종속적인 관계.
	 * 여기서 전혀 다른 관심이 하나 생긴 것이다.
	 * UserDao가 어떤 ConnectionMaker 구현 클래스의 오브젝트를 이용하게 할지를 결정하는 관심.
	 * 
	 * 새로 생긴 관심을 클라이언트(사용자)에게 넘겨보자
	 */
}

