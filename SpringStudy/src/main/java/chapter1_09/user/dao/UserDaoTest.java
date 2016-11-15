package chapter1_09.user.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import spring.user.domain.User;

public class UserDaoTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		
		// getBean 첫번째 인자는 @Bean에 등록된 메소드 명, 두번째 인자는 리턴해줄 타입
		// DaoFactory 클래스의 userDao 메소드를 사용해 UserDao 타입으로 리턴해준다는 설정
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user = new User();
		user.setId("whiteship3");
		user.setName("백기선");
		user.setPassword("married");
		
		dao.add(user);
		
		System.out.println(user.getId() + " 등록 성공");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
		
		System.out.println(user2.getId() + " 조회 성공");
	}
	
	/**
	 * 제어의 역전이라 함은 
	 * 처음에서 시작해서 차례 차례 시작되는 운영과 달리.
	 * 미리 무엇인가 작업을 해둔디 요청에 따라 미리 작업해 둔 것을 꺼내어 쓴다는 개념인 것 같다.
	 * 
	 * 마치 자동차 주문이 들어왔을 때 부품을 만들고 조립을 차례 차례 하는 것과
	 * 미리 부품들을 만들어 놓고 주문이 들어왔을 때 만들어 둔 부품들을 꺼내어 조립하는 것과 같다는 생각.
	 */
	
	/*
	 * 애플리케이션 컨텍스트는 DaoFactory 클래스를 설정정보로 등록해두고 @Bean이 붙은 메소드의 이름을 가져와 빈 목록을 만들어둔다.
	 * 클라이언트가 애플리케이션 컨텍스트의 getBean() 메소드를 호출하면 자신(애플리케이션 컨텍스트)의 빈 목록에서 요청한 이름이 있는지 찾고, 
	 * 있다면 빈을 생성하는 메소드를 호출해서 오브젝트를 생성시킨 후 클라이언트에 돌려준다.
	 * 
	 * DaoFactory를 오브젝트 팩토리로 직접 사용했을 떄와 비교해서 애플리케이션 컨텍스트를 사용했을 때 없을 수 있는 장점(이전 챕터와 지금 챕터의 차이)
	 * 1. 클라이언틑 구체적인 팩토리 클래스를 알 필요가 없다.
	 * 2. 애플리케이션 컨텍스트는 종합 IoC 서비스를 제공해준다.
	 * 3. 애플리케이션 컨텍스트는 빈을 검색하는 다양한 방법을 제공해준다.
	 */
	
	/*
	 * 스프링 IoC의 용어 정리
	 * 빈(Bean)
	 * 빈 또는 빈 오브젝트는 스프링이 IoC 방식으로 관리하는 오브젝트라는 뜻이다.
	 * 스프링이 직접 생성과 제어를 담당하는 오브젝트만을 빈이라고 부른다.
	 * 
	 * 빈 팩토리(bean factory)
	 * 스프링의 IoC를 담당하는 핵심 컨테이너를 가리킨다. 빈의 등록, 생성, 조회, 리턴, 그 외의 부가적인 빈을 관리하는 기능을 담당.
	 * 보통 바로 사용하지 않고 이를 확장한 애플리케이션 컨텍스트를 이용한다.
	 * BeanFactory라고 붙여쓰면 빈 팩토리가 구현하고 있는 가장 기본적인 인터페이스의 이름이 된다.
	 * 이 인터페이스에 getBean()과 같은 메소드가 정의되어 있다.
	 * 
	 * 애플리케이션 컨텍스트(apllication context)
	 * 빈 팩토리를 확장한 IoC 컨테이너다. 기존적 기능은 빈 팩토리와 동일하다. 여기에 스프링이 제공하는 각종 부가 서비스를 추가로 제공한다.
	 * 빈 팩토리라고 부를 때는 주로 빈의 생성과 제어의 관점에서 이야기하는 것이고
	 * 애플리케이션 컨텍스트라고 할 때는 스프링이 제공하는 애플리케이션 지원 기능을 모두 포함해서 이야기하는 것.
	 * ApplicationContext는 BeanFactory를 상속한다.
	 * 
	 * 설정정보/설정 메타정보(configuration metadata)
	 * 애플리케이션 컨텍스트 또는 빈 팩토리가 IoC를 적용하기 위해 사용하는 메타정보를 말한다.
	 * 실제로 스프링의 설정정보는 컨테이너에 어떤 기능을 세팅하거나 조정하는 경우에도 사용하지만, 
	 * 그보다는 IoC 컨테이너에 의해 관리되는 애플리케이션 오브젝트를 생성하고 구성할 때 사용한다.
	 * 
	 * 컨테이너(container) 또는 IoC 컨테이너
	 * IoC 방식으로 빈을 관리한다는 의미에서 애플리케이션 컨텍스트나 빈 팩토리를 컨테이너 또는 IoC 컨테이너라고도 한다.
	 * IoC 컨테이너는 주로 빈 팩토리의 관점, 컨테이너 또는 스프링 컨테이너라고 할 때는 애플리케이션 컨텍스트를 가리킨다.
	 * 
	 * 스프링 프레임워크
	 * IoC 컨테이너, 애플리케이션 컨텍스트를 포함해서 스프링이 제공하는 모든 기능을 통틀어 말할 때 주로 사용한다.
	 */

}
