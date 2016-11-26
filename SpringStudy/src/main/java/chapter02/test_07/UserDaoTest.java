package chapter02.test_07;

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

@RunWith(SpringJUnit4ClassRunner.class) // 스프링의 테스트 컨텍스트 프레임워크의 JUnit확장기능 지정
@ContextConfiguration(locations="/chapter02/test_07/applicationContext.xml") // 애플리케이션 컨텍스트 위치 지정, !! 여기서는 앞에 '/' 붙여야만 클래스패스 설정됨
public class UserDaoTest {
	/**
	 * 이전 예제에서는 각 Test 실행시 마다 ApplicationContext를 생성했었다.
	 * 지금은 예제가 단순해서 시간이 별로 안걸리지만 실무에서는 ApplicationContext 생성때마다 상당한 시간이 걸릴 수 있다.
	 * 
	 * 하지만 테스트에서 필요로 하는 애플리케이션 컨텍스트를 만들어서 모든 테스트가 공유하게 할 수 있다.
	 * 한번만 생성해서 Test 모두가 공유하는 것이다.
	 * 
	 * 실제로 Test를 적용할때는 
	 * 이번 예제는 스프링의 테스트 컨텍스트 프레임워크를 적용한 것이다.
	 */
	@Autowired // 테스트 컨텍스트가 스프링 컨텍스트에 의해 자동으로 값이 주입된다.
	private ApplicationContext context;
	
	private UserDao dao;
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void setUp(){
		this.dao = this.context.getBean("userDao", UserDao.class);
		
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
	 * Test 클래스의 컨텍스트 공유
	 * 여러 클래스에서 Test를 하게 될 때에도 어플리케이션 컨텍스트는 하나만 생성하여 공유한다.
	 * @RunWith(SpringJUnit4ClassRunner.class)
	 * @ContextConfiguration(locations="/chapter02/test_07/applicationContext.xml") 
	 * public class UserDaoTest {...}
	 * 
	 * @RunWith(SpringJUnit4ClassRunner.class)
	 * @ContextConfiguration(locations="/chapter02/test_07/applicationContext.xml") 
	 * public class GroupDaoTest {...}
	 * 
	 * 위의 두 클래스 모두 /chapter02/test_07/applicationContext.xml 에서 만들어진 하나의 애플리케이션 컨텍스트만 참조하여 공유하는 것이다.
	 * 
	 * @Autowired가 붙은 인스턴스 변수가 있으면, 테스트 컨텍스트 프레임워크는 변수 타입과 일치하는 컨텍스트 내의 빈을 찾는다.
	 * 타입이 일치하는 빈이 있으면 인스턴스 변수에 주입해준다.
	 * 일반적으로는 주입을 위해서 생성자나 수정자 메소드 같은 메소드가 필요하지만, 이 경우에는 메소드가 없어도 주입이 가능하다. 또 별도의 DI 설정 없이 필드의 타입정보를
	 * 이용해 빈을 자동으로 가져올 수 있는데, 이런 방법을 타입에 의한 자동와이어링이라고 한다.
	 * 
	 * 그렇다면 이상한 점이 있다. applicationContext.xml 파일에서 정의한 ApplicationContext 빈은 없었다.
	 * 그것은 자기 자신도 빈으로 등록하기 때문이다. 따라서 애플리케이션 콘텍스트 안에는 ApplicationContext 타입의 빈이 존재하는 셈이다.
	 * <bean id="applicationContext" class="/chapter02/test_07/applicationContext.xml"></bean> 이런 셈이랄까?
	 * 
	 * 그렇다면 ApplicationContext의 getBean을 쓰지 않고 @Autowired만 사용해서 UserDao를 사용할 수 있지 않을까?
	 */
	
}
