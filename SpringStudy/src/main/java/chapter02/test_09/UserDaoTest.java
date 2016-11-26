package chapter02.test_09;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/chapter02/test_09/applicationContext.xml")
@DirtiesContext // Test 메소드에서 애플리케이션 컨텍스트의 구성이나 상태를 변경한다는 것을 테스트 컨텍스트에 알려준다.
public class UserDaoTest {
	/**
	 * setUp() 에서 강제로 applicationContext.xml 설정부분을 변경해 주었다.(dataSource)
	 * 하지만 이 방식은 매우 주의해서 사용해야 한다.
	 * 애플리케이션 컨텍스트의 구성이나 상태를 테스트 내에서 변경하지 않는 것이 원칙이다.
	 * 그런데 테스트 코드는 애플리케이션 컨텍스트에서 가져온 UserDao 빈의 의존관계를 강제로 변경한다.
	 * 한 번 변경하면 나머지 모든 테스트를 수행하는 동안 변경된 애플리케이션 컨텍스트가 계속 사용될 것이다. 이는 별로 바람직하지 않다.
	 * 
	 * 그래서 @DirtiesContext 어노테이션을 사용했다.
	 * @DirtiesContext 어노테이션을 사용하면 애플리케이션 컨텍스트를 공유하여 사용하지 않고 별도로 하나 만들어 사용하겠다는 의미다.
	 * 여러 메소드를 Test 할 때 각 Test 메소드 마다 개별적인 애플리케이션을 사용한다는 것이다.
	 * 클래스에 적용하면 해당 클래스의 Test 하는 메소드들은 메소드마다 개별적인 애플리케이션 컨텍스트를 갖는다.
	 * 메소드에 적용하면 해당 메소드만 개별적인 애플리케이션 컨텍스트를 갖는다.
	 * 
	 * @DirtiesContext 를 사용하면 Test에서 빈의 의존관계를 강제로 DI 하는 방법을 사용했을 때 문제는 피할 수 있다.
	 * 하지만 이 때문에 애플리케이션 컨텍스트를 매번 만드는 건... 쫌..
	 */
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private UserDao dao;
	
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void setUp(){
		this.user1 = new User("testId01","이름01","pwd01");
		this.user2 = new User("testId02","이름02","pwd02");
		this.user3 = new User("testId03","이름03","pwd03");
		
		//applicationContext.xml 에서 dataSource Bean을 만들었지만 그 dataSource가 아닌 이곳 dataSource를 적용한다.
		DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/testdb","root","root",true);
		dao.setDataSource(dataSource); // 코드에 의한 수동 DI
	}

	@Test
	public  void addAndGet() throws ClassNotFoundException, SQLException {
		dao.deleteAll();
		Assert.assertThat(dao.getCount(), CoreMatchers.is(0));
		
		User user = new User();
		user.setId("test2_05");
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
