package chapter03.templet_10;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import chapter03.templet_08.StatementStrategy;


public class UserDao {
	
	private JdbcContext jdbcContext;
	
	public void setJdbcContext(JdbcContext jdbcContext){
		this.jdbcContext = jdbcContext;
	}
	
	public void deleteAll() throws SQLException {
		this.jdbcContext.workWithStatementStarategy(new StatementStrategy(){ 
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement("delete from users");
				return ps;
			}
		});
	}
	
	public void add(final User user) throws SQLException {
		this.jdbcContext.workWithStatementStarategy(new StatementStrategy(){ // 생성자를 마치 클래스 처럼 만들어서 익명클래스를 만듬
			public PreparedStatement makePreparedStatement(Connection c)
					throws SQLException {
				PreparedStatement ps = c.prepareStatement(
						"insert into users(id, name, password) values(?,?,?)");
				ps.setString(1, user.getId());
				ps.setString(2, user.getName());
				ps.setString(3, user.getPassword());
				return ps;
			}
		});
	}
	
}
