package chapter02.test_06;

import java.sql.SQLException;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

public class UserDaoTest {
	/*
	 * Test 메소드에서 중복된 코드를 setUp에서 설정, 관리할 수 있게 코드 변경을 해주었다.
	 */
	private UserDao dao;
	private User user1; // 테스트를 수행할 때 필요한 정보나 오브젝트를 픽스처(fixture)라고 한다.
	private User user2;
	private User user3;
	
	@Before
	public void setUp(){
		ApplicationContext context = new GenericXmlApplicationContext("chapter02/test_06/applicationContext.xml");
		this.dao = context.getBean("userDao", UserDao.class);
		
		this.user1 = new User("testId01","이름01","pwd01");
		this.user2 = new User("testId02","이름02","pwd02");
		this.user3 = new User("testId03","이름03","pwd03");
	}

	@Test
	public  void addAndGet() throws ClassNotFoundException, SQLException {
		dao.deleteAll();
		Assert.assertThat(dao.getCount(), CoreMatchers.is(0));
		
		User user = new User();
		user.setId("test2_03");
		user.setName("백기선");
		user.setPassword("married");
		
		dao.add(user);
		
		User user2 = dao.get(user.getId());
		
		Assert.assertThat(user2.getName(), CoreMatchers.is(user.getName()));
		Assert.assertThat(user2.getPassword(), CoreMatchers.is(user.getPassword()));
	}
	
	@Test
	public  void count() throws ClassNotFoundException, SQLException {
		
		dao.deleteAll();
		Assert.assertThat(dao.getCount(), CoreMatchers.is(0));
		
		dao.add(user1);
		Assert.assertThat(dao.getCount(), CoreMatchers.is(1));
		
		dao.add(user2);
		Assert.assertThat(dao.getCount(), CoreMatchers.is(2));
		
		dao.add(user3);
		Assert.assertThat(dao.getCount(), CoreMatchers.is(3));
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFilure() throws SQLException, ClassNotFoundException {
		dao.deleteAll();
		Assert.assertThat(dao.getCount(), CoreMatchers.is(0));
		
		dao.get("unkown_id");
	}
	
}
