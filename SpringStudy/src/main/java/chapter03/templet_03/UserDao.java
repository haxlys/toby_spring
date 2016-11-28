package chapter03.templet_03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;


public class UserDao {
	
	/**
	 * 변하는 부분과 변하지 않는 부분으로 나누어 makeStatement() 메소드라는 부분을 만들었다.
	 * 하지만 뭔가 이상하다. 따로 떼낸 부분을 재활용할 수 없어 보이기 때문이다.
	 * 
	 * 다음 템플릿 메소드 패턴을 적용해 보자.
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
			c = dataSource.getConnection();
			ps = makeStatement(c); // Dao 메소드의 내용중 변하는 부분만 추출해서 따로 메소드로 만들었다.
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if(ps != null){
				try {
					ps.close();
				} catch(SQLException e) {
				}
			}
			if(c != null){
				try {
					c.close();
				} catch (SQLException e) {
				}
			}
		}
		
		ps.close();
		c.close();
	}
	
	public PreparedStatement makeStatement(Connection c) throws SQLException {
		PreparedStatement ps;
		ps = c.prepareStatement("delete from users");
		return ps;
	}
	
}
