package chapter03.templet_10;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import chapter03.templet_08.StatementStrategy;

public class JdbcContext {
	
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	public void workWithStatementStarategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		
		try{
			c = this.dataSource.getConnection();
			
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
	
}
