package chapter03.templet_08;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;


public class UserDao {
	/**
	 *  클래스 파일이 많아지는 문제를 해결하는 간단한 방법이 있다.
	 *  StatementStrategy 전략 클래스를 매번 독립된 파일로 만들지 말고 UserDao 클래스 안에 내부 클래스로 정의해버리는 것.
	 *  
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
	
	//내부클래스에서 User 매개변수에 접근해주기 위하여 final을 선언해줌.
	public void add(final User user) throws SQLException {
		
		// 별도의 클래스 파일로 사용했던 클래스를 내부 클래스로 전환하였다.
		class AddStatement implements StatementStrategy{
			/* 별도의 User 생성 필요없이 add 에서 받은 매개변수를 사용할 수 있게되었다.
			User user;
			public AddStatement(User user){
				this.user = user;
			}*/
			
			@Override
			public PreparedStatement makePreparedStatement(Connection c)
					throws SQLException {
				PreparedStatement ps = c.prepareStatement(
						"insert into users(id, name, password) values(?,?,?)");
				ps.setString(1, user.getId());
				ps.setString(2, user.getName());
				ps.setString(3, user.getPassword());
				return ps;
			}
		}
		
		StatementStrategy st = new AddStatement(); //new AddStatement(user);
		jdbcContextWithStatementStarategy(st);
	}
	
	/**
	 * 다음 예제에서는 내부클래스를 익명내부클래스로 전환해보자.
	 */
	
}
