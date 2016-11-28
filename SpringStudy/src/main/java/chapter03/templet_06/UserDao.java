package chapter03.templet_06;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;


public class UserDao {
	
	/**
	 *  구체적인 클래스를 지정해주는 역할(책임)은 클라이언트(deleteAll() 메소드)에게 넘겼다.
	 */

	@Autowired
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	public void jdbcContextWithStatementStarategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		
		try{
			c = dataSource.getConnection();
			
			// 전략 패턴에 DI를 적용시킨 구조
			ps = stmt.makePreparedStatement(c);
			
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
	
	public void deleteAll() throws SQLException {
		StatementStrategy st = new DeleteAllStatement(); // 선정한 전략 클래스의 오브젝트 생성
		jdbcContextWithStatementStarategy(st);	// 컨텍스트 호출. 전략 오브젝트 전달
	}
	
	
	
}
