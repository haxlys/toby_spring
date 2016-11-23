package chapter01._14.user.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import chapter01.user.domain.User;

public class UserDaoTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		/*
		 * 자바 코드를 이용한 설정 방식으로 DataSource 인터페이스를 적용 했다.
		 * 기존 예제에서 ConnectionMaker를 사용하여 IoC와 DI의 개념을 설명하기 위해 직접 이 인터페이스를 정희하고 사용했지만
		 * 사실 자바에서는 DB 커넥션을 가져오는 오브젝트의 기능을 추상화해서 비슷한 용도로 사용할 수 있게 만들어진 DataSource 라는 인터페이스가 이미 존재한다.
		 * 일반적으로 DataSource를 구현해서 DB 커넥션을 제공하는 클래스를 만들일은 거의 없다.
		 * 이미 다양한 방법으로 DB 연결과 Pooling 기능을 갖춘 많은 DataSource 구현 클래스가 존재하고, 이를 가져다 쓰면 충분하기 떄문이다.
		 */
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user = new User();
		user.setId("whiteship7");
		user.setName("백기선");
		user.setPassword("married");
		
		dao.add(user);
		
		System.out.println(user.getId() + " 등록 성공");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
		
		System.out.println(user2.getId() + " 조회 성공");
	}
}
