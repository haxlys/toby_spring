package chapter1._13.user.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import chapter1.user.domain.User;

public class UserDaoTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		/*
		 * XML 파일을 이용한 애플리케이션 컨텍스트
		 * 기존에 애플리케이션 컨텍스트가 어노테이션으로 DaoFactory 클래스를 사용했다면 이번에는 xml 파일을 사용하도록 설정했다.
		 * 생성자에 들어가는 경로는 항상 클래스패스 부터 시작하는데 맨 앞에 '/' 는 생략 가능하다. 어쩄든 클래스 패스 부터 시작한다.
		 * 
		 * 이 예제에서 키 포인트는 applicationContext.xml 에서 userDao Bean을 만들 때이다.
		 * userDao Bean의 property로 name 속성을 connectionMaker 로 주었는데 여기에는 다 이유가 있다.
		 * 스프링에서 수정자 메소드를 통해서 값을 넣어주기 때문이다.
		 * 실제로 UserDao 클래스에는 setCoonectionMaker 라는 수정자 메소드를 보유하고 있다.
		 * name 속성에서 맨앞에 set을 붙여주고 첫글자를 대문자로 하는 수정자 메소드를 호출하여 사용하고 있는 점을 명심해야 한다.
		 */
		//ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		ApplicationContext context = new GenericXmlApplicationContext("chapter1_13/user/dao/applicationContext.xml");
		ApplicationContext context2 = new ClassPathXmlApplicationContext("applicationContext.xml", UserDao.class);
		//ClassPathXmlApplicationContext 는 User.Dao.class 의 기준으로 부터 지정된 xml 파일을 찾는다.
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user = new User();
		user.setId("whiteship5");
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
