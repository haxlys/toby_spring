package chapter03.templet_04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;


public abstract class UserDao {
	
	/**
	 *  추상메소드로 바꾸고 이를 상속해서 서브클래스에서 메소드를 구현하는 템플릿 메소드 패턴으로 바꾼다.
	 *  
	 *  하지만 이것 역시 문제가 있다.
	 *  DAO 로직마다 상속을 통해 새로운 클래스를 만들어야 한다는 것.
	 *  확장구조가 이미 클래스를 설계하는 시점에서 고정되어 버린다는 것.
	 *  
	 *  다음으로 전략 패턴을 적용해보자.
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
	
	abstract protected PreparedStatement makeStatement(Connection c) throws SQLException;
	
}
