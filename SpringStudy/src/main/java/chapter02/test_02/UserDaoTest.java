package chapter02.test_02;

import java.sql.SQLException;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import chapter01.user.domain.User;

public class UserDaoTest {

	/**
	 * JUnit에게 테스트용이란 메소드임을 알려주는 @Test
	 * JUnit 메소드는 반드시 public으로 선언돼야 한다.
	 * 
	 * 예제와는 다르게 junit 라이브러리가 sts IDE에서 제공되는 라이브러리랑 다른건지 적용방식이 약간 다르며
	 * 표현되는 방식또한 다르므로 예제 실행시 주의가 필요.
	 * main() 메소드 필요없이 @test 적용되어 있는 클래스에서 바로 test 실행가능
	 * 이 부분은 바로 다음 예제에서 설명하고 있다. s
	 * 
	 * 163장 까지 예제
	 */
	@Test
	public  void addAndGet() throws ClassNotFoundException, SQLException {
		ApplicationContext context = new GenericXmlApplicationContext("chapter02/test_02/applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		
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
