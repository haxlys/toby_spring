package chapter03.templet_05;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;


public class UserDao {
	
	/**
	 *  전략 패턴으로 바꾼 구조다.
	 *  하지만 전략 패턴은 필요에 따라 컨텍스트는 그대로 유지되면서(OCP의 폐쇄 원칙)
	 *  전략을 바꿔 쓸 수 있다(OCP의 개방 우너칙)는 것인데,
	 *  이렇게 컨텍스트 안에서 이미 구체적인 전략 클래스인 DeleteAllStatement를 사용하도록 고정되어 있다면 뭔가 이상하다.
	 *  컨텍스트가 StatementStrategy 인터페이스 뿐 아니라 특정 구현 클래스인 DeleteAllStatement를 직접 알고 있다는 건
	 *  전략 패턴에도 OCP에도 잘 들어맞는다고 볼 수 없기 때문이다.
	 *  
	 *  이 문제를 앞서 배웠던 DI 구조를 적용시켜보자.
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
			
			// 전략 패턴을 따라 적용된 코드
			StatementStrategy strategy = new DeleteAllStatement();
			ps = strategy.makePreparedStatement(c);
			
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
