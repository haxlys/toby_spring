package chapter1._03.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import chapter1.user.domain.User;

public abstract class UserDao {
	/*
	 * 추상 클래스로 수정되었다.
	 * 당연히 추상 메소드가 존재하기 때문이다.
	 * getConnection() 메소드는 추상메소드로 바뀌어 납품 될 회사를 맞이 할 준비가 되었다.
	 * 
	 * N사에 남품된 코드는 NUserDao라는 클래스로 작성되어 해당 UserDao를 상속받고 커넥션 정보는 자기네들 회사에 맞춰 작성되기만 하면된다.
	 * UserDao의 핵심기능이 add와 get은 우리 회사가 개발한 그대로 사용만 하면 된다. 
	 * D사에 납품된 코드역시 동일하다.
	 * 
	 * 이렇게 슈퍼클래스에 기본적인 로직 흐름을 만들고 그 기능의 일부를 추상 메소드나 오버라이딩 가능한 protected 메소드 등으로 만든 뒤
	 * 서브클래스에서 이런 메소드를 필요에 맞게 구현해서 사용하도록 하는 방법을 디자인 패턴에서
	 * 템플릿 메소드 패턴 이라고 한다.
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		UserDao dao = new NUserDao();
		
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
		Connection c = getConnection();
		
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
		Connection c = getConnection();
		
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
	
	public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
	
	/*
	 * 하지만 이 코드 역시 단점이 있다.
	 * 1. 자바는 다중 상속을 허용하지 않으므로 커넥션 객체를 가져오는 방법을 분리하기 위해 상속구조로 만들어버리면
	 * 		후에 다른 목적으로 UserDao에 상속을 적용하기 힘들다.
	 * 2. 또 다른 문제는 상속을 통한 상하위 클래스의 관계는 생각보다 밀접하다는 점이다.
	 * 3. 확장된 기능인 DB커넥션을 생성하는 코드를 다른 DAO클래스에 적용할 수 없다는 것도 큰 단점이다.
	 * 
	 * 이번에는 아예 상속관계도 아닌 완전히 독립적인 클래스로 만들어 보겠다.
	 */
}

