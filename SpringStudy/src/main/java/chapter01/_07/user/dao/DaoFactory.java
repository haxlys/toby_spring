package chapter01._07.user.dao;

public class DaoFactory {
	public UserDao userDao(){
		ConnectionMaker connectionMaker = new DConnectionMaker();
		UserDao userDao = new UserDao(connectionMaker);
		return userDao;
	}
}
