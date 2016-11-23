package chapter01._12.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import chapter01._12.user.dao.ConnectionMaker;
import chapter01.user.domain.User;

public class UserDao {
	
	private ConnectionMaker connectionMaker;
	
	/*
	 * 수정자 메소드를 이용한 주입
	 * 수정자 메소드는 항상 set으로 시작하며 외부에서 내부의 애트리뷰트 값을 변경하려는 용도로 주로 사용한다.
	 * 스프링은 전통적으로 메소드를 이용한 DI 방법 중에서 수정자 메소드를 가장 많이 사용해왔다.
	 * 이전 예제와 다르게 생성자가가 아닌 수정자 메소드를 통한 의존관계 주입(DI)를 적용 하였다.
	 * DaoFactory역시 수정되었다.
	 */
	public void setConnectionMaker(ConnectionMaker connectionMaker){
		this.connectionMaker = connectionMaker;
	}
	/*
	public UserDao(ConnectionMaker connectionMaker){
		this.connectionMaker = connectionMaker;
	}*/
	
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
}

