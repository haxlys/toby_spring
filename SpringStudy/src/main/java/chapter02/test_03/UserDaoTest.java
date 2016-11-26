package chapter02.test_03;

import java.sql.SQLException;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import chapter02.test.domain.User;

public class UserDaoTest {

	/**
	 *	테스트한 후 번거롭게 하는 userId 변경작업을 deleteAll()와 getCount()를 추가하여 자동화시켰다.
	 *	단위 테스트는 코드가 변경되지 않는 한 동일한 테스트 결과를 얻어야 한다.
	 */
	@Test
	public  void addAndGet() throws ClassNotFoundException, SQLException {
		ApplicationContext context = new GenericXmlApplicationContext("chapter02/test_03/applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		// 삭제한 후 dao.getCount()의 리턴값이 0과 같은지 Test
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
}
