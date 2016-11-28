package chapter03.templet_01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

public class UserDao {
	
	/**
	 * 템플릿에 들어가기 앞서 예외 처리에 관하여 살펴보자.
	 * dao 메소드 실행 도중 어떤 이유에서든 문제가 생겨서 제대로 끝마치지 못한다면
	 * 리소스 반환을 하지 못해 리소스가 모자란다는 심각한 오류를 내며 서버가 중단될 수도 있다.
	 * 이를 위한 try/catch/finally 구문을 적용해 보자.
	 */

	@Autowired
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	public void deleteAll() throws ClassNotFoundException, SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		
		try{
			//예외가 발생할 가능성이 있는 코드를 try 블록으로 묶어준다.
			c = dataSource.getConnection();
			ps = c.prepareStatement("delete from users");
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e; // 예외 발생시 부가적인 작업을 해줄수 있도록 catch 블록을 둔다. 아직은 예외를 다시 메소드 밖으로 던지는 것밖에 없다.
		} finally {
			if(ps != null){
				try {
					ps.close();
				} catch(SQLException e) {
					// ps.close() 메소드에서도 SQLException이 발생할 수 있기 때문에 이를 잡아줘야 한다.
					// 그렇지 않으면 Connection을 close() 하지 못하고 메소드를 빠져나갈 수 있다.
				}
			}
			if(c != null){
				try {
					c.close(); // Connection 반환
				} catch (SQLException e) {
				}
			}
		}
		
		ps.close();
		c.close();
	}
	
	public void add(User user) throws ClassNotFoundException, SQLException{
		Connection c = dataSource.getConnection();
		
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
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement(
				"select * from users where id = ?");
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		User user = null;
		if(rs.next()){
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));			
		}
		
		rs.close();
		ps.close();
		c.close();
		
		if(user == null) throw new EmptyResultDataAccessException(1);
		
		return user;
	}
	
	public int getCount() throws ClassNotFoundException, SQLException {
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement("select count(*) from users");
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		
		rs.close();
		ps.close();
		c.close();
		
		return count;
	}
}
