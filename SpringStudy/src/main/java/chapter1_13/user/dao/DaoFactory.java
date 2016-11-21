package chapter1_13.user.dao;

public class DaoFactory {
	
	public UserDao userDao(){
		UserDao userDao = new UserDao();
		userDao.setConnectionMaker(connectionMaker());
		return userDao;
	}
	
	public ConnectionMaker connectionMaker(){
		return new DConnectionMaker();
	}
}
