package chapter02.test_11;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class UserDaoTest {
	/**
	 * 이번 예제는 스프링 컨테이너를 사용하지 않고 테스트를 만드는 것이다.
	 */
	private UserDao dao;
	
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void setUp(){
		this.user1 = new User("testId01","이름01","pwd01");
		this.user2 = new User("testId02","이름02","pwd02");
		this.user3 = new User("testId03","이름03","pwd03");
		
		// 09 예제와 비교해보면 dao 오브젝트를 직접 DI 해주었다.
		// 오브젝트 생성, 관계설정 등을 모두 직접 해준다.
		dao = new UserDao();
		DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/testdb","root","root",true);
		dao.setDataSource(dataSource);
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
	
	/**
	 * 테스트를 위한 DataSource를 직접 만드는 번거로움은 있지만 애플리케이션 컨텍스트를 아예 사용하지 않으니 코드는 더 단순해지고 이해하기 편해졌다.
	 * 애플리케이션 컨텍스트가 만들어지는 번거로움이 없어졌으니 그만큼 테스트시간도 절약할 수 있다.
	 * 하지만 JUnit은 매번 새로운 테스트 오브젝트를 만들기 때문에 매번 새로운 UserDao 오브젝트가 만들어진다는 단점도 있다.
	 * 
	 * 이 테스트는 지금까지 만들었던 세개의 UserDao 테스트를 완벽하게 통과한다.
	 * UserDao가 스프링 API에 의존하지 않고 자신의 관심에만 집중해서 깔끔하게 만들어진 코드이기 때문에 가능한 일이다.
	 * 바로 이런 가볍고 깔끔한 테스트를 만들 수 있는 이유도 DI를 적용했기 때문이다.
	 * DI는 객체지향 프로그래밍 스타일이다.
	 * 따라서 DI를 위해 컨테이너가 반드시 필요한 것은 아니다.
	 * DI컨테이너나 프레임워크는 DI를 편하게 적용하도록 도움을 줄 뿐, 컨테이너가 DI를 가능하게 해주는 것은 아니다.
	 */
	
}
