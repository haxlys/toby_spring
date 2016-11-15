package chapter1_10.user.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserDaoTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		DaoFactory factory = new DaoFactory();
		UserDao dao1 = factory.userDao();
		UserDao dao2 = factory.userDao();
		
		// 아래 2개의 주소는 다르다
		System.out.println(dao1);
		System.out.println(dao2);
		
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		
		// 아래 2개의 주소는 같다. 애플리케이션 컨텍스트는 기본적으로 빈을 싱글톤으로 생성하기 때문이다.
		UserDao dao3 = context.getBean("userDao", UserDao.class);
		UserDao dao4 = context.getBean("userDao", UserDao.class);
		
		System.out.println(dao3);
		System.out.println(dao4);
	}

}
