package chapter02.test_08;

import java.sql.SQLException;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/chapter02/test_08/applicationContext.xml")
public class UserDaoTest {
	/**
	 * UserDao 객체도 @Autowired를 사용하여 DI 하였다.
	 * 
	 * 더불어 애플리케이션 컨텍스트에서 만든 dataSource Bean 역시 @Autowired를 사용해서 DI 하였다.
	 * userDao Bean 생성시 수정자 메소드로 넘겨주던 dataSource 객체를 DI로 변경한 것이다.
	 * 따라서 UserDao.setDataSource() 역시 필요없어 졌다.
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
		//@Autowired 를 사용하면서 해당 코드는 필요없어졌다.
		//this.dao = this.context.getBean("userDao", UserDao.class);
		
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
	
	/**
	 * 우리가 항상 인터페이스를 두고 DI를 적용하여 설계해야 하는 이유
	 * 1. 소프트웨어 개발에서 절대로 바뀌지 않는 것은 없기 떄문이다.
	 * 2. 클래스의 구현 방식은 바뀌지 않는다고 하더라도 인터페이스를 두고 DI를 적용하게 해두면 다른 차원의 서비스 기능을 도입할 수 있기 때문이다.
	 *  	1장에서 만들어봤던 DB 커넥션의 개수를 카운팅하는 부가 기능이 그런 예다. UserDao와 ConnectionMaker 사이에 자연스럽게 부가기능을 추가 할 수 있었던 건 DI를
	 *  	적용했었기 때문이다.
	 * 3. 테스트 때문이다. DI는 테스트가 작은 단위의 대상에 대해 독립적으로 만들어지고 실행되게 하는 데 중요한 역할을 한다.
	 */
	
	
}
