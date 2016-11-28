package chapter03.templet_03;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class UserDaoTest {
	private UserDao dao;
	
	@Before
	public void setUp(){
		dao = new UserDao();
		DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/testdb","root","root",true);
		dao.setDataSource(dataSource);
	}

	@Test
	public  void test() throws ClassNotFoundException, SQLException {
		dao.deleteAll();
	}
}
