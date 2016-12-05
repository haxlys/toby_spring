package chapter03.templet_09;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import chapter03.templet_08.StatementStrategy;


public class UserDao {
	/**
	 *  내부클래스를 익명클래스로 전환해보자.
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
		jdbcContextWithStatementStarategy(new StatementStrategy(){ 
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement("delete from users");
				return ps;
			}
		});
	}
	
	public void add(final User user) throws SQLException {
		/*
		class AddStatement implements StatementStrategy{
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
		
		StatementStrategy st = new AddStatement();
		jdbcContextWithStatementStarategy(st);
		*/

		jdbcContextWithStatementStarategy(new StatementStrategy(){ // 생성자를 마치 클래스 처럼 만들어서 익명클래스를 만듬
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
