package chapter01._11.user.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import chapter01.user.domain.User;

/**
 * @author bhb
 *
 */
public class UserDaoConnectionCountingTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		//
		// DAO 사용 코드
		//
		CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
		User user1 = dao.get("whiteship");
		User user2 = dao.get("whiteship2");
		User user3 = dao.get("whiteship3");
		System.out.println(user1.getId() + " " + user2.getId() + " "  + user3.getId());
		
		System.out.println("Connection counter : " + ccm.getCounter());
	}
	/*
	 * 이번 예제 설명
	 * 이번 예제는 DI(의존관계 주입)에 관한 설명이 나온 뒤에 나오는 예제로써 DI를 통해서 기능을 간단히 추가 하는 예제이다.
	 * DB에 연결하는 횟수를 카운트해주는 기능을 추가하자.
	 * 
	 * UserDao 객체의 메소드 안에는
	 * Connection c = connectionMaker.makeConnection(); 와 같이 DB 접속 로직이 있다.
	 * 이 부분을 호출할 때마다 새로 만들어진 CountingConnectionMaker 객체의 makeConnection() 메소드가 호출된다.
	 * makeConnection() 메소드는 카운트 한 후 
	 * UserDao dao = context.getBean("userDao", UserDao.class); 에서 생성되어 만들어 둔 DConnection 객체를 되돌려 준다.
	 * 
	 * 이 예제 에서 중요한 점은 기존 핵심 로직의 수정없이 기존 로직 사이에 다른 기능을 추가했다는 것이다.
	 * 이런 작업이 가능했던 이유는 인터페이스를 사용하였기에 가능하다.
	 * AImpl 를 구현한 BClass 로 클라이언트 요청을 처리하는 로직이 있다고 해보자.
	 * 요청 중간에 어떤 기능을 넣고 싶다면 AImpl을 구현한 CClass를 만들고 기존 클라이언트와 BClass 로직 사이에 CClass를 만들어 주면 된다.
	 * CClass는 단지 추가적으로 BClass의 요청을 받아서 그대로 클라이언트에게 돌려주기만 하면된다.
	 * 
	 * DaoFactory 클래스 조차 수정하고 싶지 않다면 아예 새로 Factory 클래스를 만들고 ApplicationContext에 새로 만들어준 class 타입만 명시해 주면된다.
	 * 
	 * DI 기술이기에 가능한 작업이다.
	 */
	
	/*
	 * 의존관계 주입이란 다음 세 가지 조건을 충족하는 작업을 말한다.
	 * 1. 클래스 모델이나 코드에는 런타임 시점의 의존관계가 드러나지 않는다. 그러기 위해서는 인터페이스에만 의존하고 있어야 한다.
	 * 2. 런타임 시점의 외존관계는 컨테이너나 팩토리 같은 제3의 존재가 결정한다.
	 * 3. 의존관계는 사용할 오브젝트에 대한 레퍼런스를 외부에서 제공(주입)해줌으로써 만들어진다
	 * 
	 * 예제에서는 DaoFactory, DaoFactory와 같은 작업을 일반화해서 만들어 졌다는 스프링의 애플리케이션 컨텍스트, 빈팩토리, IoC 컨테이너 등이 
	 * 모두 외부에서 오브젝트 사이의 런타임 관계를 맺어주는 책임을 지닌 제3의 존재라고 볼 수 있다.
	 */

}
