package chapter02.test_04;

import java.sql.SQLException;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class UserDaoTest {

	/**
	 *	User 도메인에 필드값을 인자로 갖는 생성자를 추가하였다.
	 * count() 라는 메소드를 추가하여 addAndGet()가 한건의 데이터만 처리한 것과 달리 여러건의 데이터를 처리해서
	 * Dao의 getCount() 메소드도 Test하고자 만들었다.
	 * 
	 * 이 떄 주의 할 점은 두 개의 메소드가 어떤 순서로 실행될지는 알 수 없다는 것이다.
	 * JUnit은 특정한 테스트 메소드의 실행 순서를 보장해주지 않는다.
	 * 테스트의 결과가 테스트의 실행 순서에 영향을 받는다면 테스트를 잘못 만든 것이다.
	 * 예를 들어 addAndGet()에서 등록한 사용자 정보를 count() 테스트에서 활용하는 식으로 테스트를 만들면 안된다.
	 */
	
	@Test
	public  void addAndGet() throws ClassNotFoundException, SQLException {
		ApplicationContext context = new GenericXmlApplicationContext("chapter02/test_04/applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		
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
		ApplicationContext context = new GenericXmlApplicationContext("chapter02/test_04/applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user1 = new User("id01", "이름01", "pwd01");
		User user2 = new User("id02", "이름02", "pwd02");
		User user3 = new User("id03", "이름03", "pwd03");
		
		dao.deleteAll();
		Assert.assertThat(dao.getCount(), CoreMatchers.is(0));
		
		dao.add(user1);
		Assert.assertThat(dao.getCount(), CoreMatchers.is(1));
		
		dao.add(user2);
		Assert.assertThat(dao.getCount(), CoreMatchers.is(2));
		
		dao.add(user3);
		Assert.assertThat(dao.getCount(), CoreMatchers.is(3));
	}
}
