package chapter03.templet_10;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import chapter03.templet_06.UserDao;

public class UserDaoTest {
	
	@Autowired
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

		User user = new User();
		user.setId("chapter03");
		user.setName("백기선");
		user.setPassword("married");
		dao.add(user);
	}
}
