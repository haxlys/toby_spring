package chapter02.test_05;

import java.sql.SQLException;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

public class UserDaoTest {

	/**
	 * UserDao의 get()를 메소드에 전달된 id 값에 해당하는 사용자 정보가 없다면 어떻게 될까?
	 * Test 도중 설정된 예외처리가 발생해야 Test에 성공하는 경우를 만들어 보자
	 * 일부러 예외를 던지게끔 UserDao의 get()을 코드를 수정하였다.
	 * 
	 * 실행시 @Test인자에 expected 속성에 설정된 예외클래스에 해당하는 예외가 오면 성공적으로 테스트를 마친 것이다.
	 * 
	 * 테스트를 작성할 떄 부정적인 케이스를 먼저 만드는 습고나을 들이는 게 좋다.
	 * get()의 경우라면, 존재하는 id가 주어졌을 때 해당 레코드를 정확히 가져오는가를 테스트하는 것도 중요하지만, 존재하지 않는 id가 주어졌을 때는
	 * 어떻게 반응할지를 먼저 결정하고, 이를 확인할 수 있는 테스트를 먼저 만들려고 한다면 예외적인 상황을 빠뜨리지 않는 꼼꼼한 개발이 가능하다.
	 */
	
	@Test(expected=EmptyResultDataAccessException.class) // 테스트 중에 발생할 것으로 기대하는 예외 클래스를 지정해준다.
	public void getUserFilure() throws SQLException, ClassNotFoundException {
		ApplicationContext context = new GenericXmlApplicationContext("chapter02/test_05/applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		dao.deleteAll();
		Assert.assertThat(dao.getCount(), CoreMatchers.is(0));
		
		dao.get("unkown_id"); // 이 메소드 실행 중에 예외가 발생해야 한다. 예외가 발생하지 않으면 테스트가 실패한다.
	}
	
	/**
	 * 이번 예제에서 했던 작업을 돌이켜 보자.
	 * 1. 가장 먼저 존재하지 않는 id로 get() 메소드를 실행하면 특정한 예외가 던져져야 한다는 식으로 만들어야 할 기능을 정함
	 * 2. UserDao 코드를 수정하는 대신 getUserFailure() 테스트를 먼저 만들었다.
	 * 테스트할 코드도 없는데 어떻게 테스트를 만들 수 있었을까?
	 * 그것은 만들어진 코드를 보고 이것을 어떻게 테스트할까라고 생각하면서 getUserFailure()를 만든 것이 아니라,
	 * 추가하고 싶은 기능을 코드로 표현하려고 했기 떄문에 가능하다.
	 * 
	 * 			단	계 					내	용											코	드
	 * 조건	어떤 조건을 가지고		가져올 사용자 정보가 존재하지 않는 경우에		dao.deleteAll(); Assert.assertThat(dao.getCount(), CoreMatchers.is(0));
	 * 행위	무엇을 할 때			존재하지 않는 id로 get()을 실행하면			get("unkown_id");
	 * 결과	어떤 결과가 나온다		특별한 예외가 던져진다							@Test(expected=EmptyResultDataAccessException.class) 
	 * 
	 * 이렇게 비교해보면 이 테스트 코드는 마치 잘 작성된 하나의 기능정의서처럼 보인다. 그래서 보통 기능설계, 구현, 테스트라는 일반적인 개발 흐름의 기능설계에
	 * 해당하는 부분을 이 테스트 코드가 일부분 담당하고 있다고 볼 수 있다. 이런 식으로 추가하고 싶은 기능을 일반 언어가 아닌 테스트 코드로 표현해서
	 * 마치 코드로 된 설계문서 처럼 만들어 놓은 것이라고 생각해보자. 그러고 나서 실제 기능을 가진 애플리케이션 코드를 만들고 나면, 바로 이 테스트를 실행해서 설계한 대로
	 * 코드가 동작하는지를 빠르게 검증할 수 있다.
	 * 만약 테스트가 실패하면 이때는 설계한 대로 코드가 만들어지지 않았음을 바로 알 수 있다. 그리고 문제가 되는 부분이 무엇인지에 대한 정보도 테스트 결과를 통해 얻을 수 있다.
	 * 다시 코드를 수정하고 테스트를 수행해서 테스트가 성공하도록 애플리케이션 코드를 계속 다듬어간다. 결국 테스트가 성공한다면, 그 순간 코드 구현과 테스트라는 두 가지 작업이
	 * 동시에 끝나는 것이다.
	 * 
	 * TDD
	 * 만들고자 하는 기능과 그것을 검증을 할 수 있는 테스트 코드를 먼저 만들고 테스트를 성공하게 해주는 코드를 작성하는 방식의 개발을 Test Driven Development라 한다. 
	 * TFD(Test First Development)라고도 한다.
	 * "실패한 테스트를 성공시키기 위한 목적이 아닌 코드는 만들지 않는다"는 것이 TDD의 기본 원칙.
	 * 이 원칙을 따랐다면 모든 코드는 빠짐없이 테스트로 검증된 것이라고 볼 수 있다.
	 * 
	 */
	
}
