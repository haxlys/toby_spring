package chapter03.templet_07;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;


public class UserDao {
	/**
	 * AddStatement 클래스를 추가 시킴.
	 * 이런식으로 관심사를 통해 분리 시켰다. 이전 DAO보다 소스양더 훨씬 줄어서 간결해졌다.
	 * 
	 * 하지만 아직도 개선점이 많다.
	 * 1. DAO 메소드마다 새로운 StatementStrategy 구현 클래스를 만들어야 한다는 점
	 * 2. DAO 메소드에서 StatementStrategy에 전달할 User와 같은 부가적인 정보가 있는 경우 
	 * 		이를 위해 오브젝트를 전달받는 생성자와 이를 저장해둘 인스턴스 변수를 번거롭게 만들어야 한다는 점
	 * 
	 * 이 부분을 다음 예제에서 수정해 보자
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
		StatementStrategy st = new DeleteAllStatement();
		jdbcContextWithStatementStarategy(st);
	}
	
	public void add(User user) throws SQLException {
		StatementStrategy st = new AddStatement(user);
		jdbcContextWithStatementStarategy(st);
	}
	
}
