package chapter01._08.user.dao;

public class DaoFactory {
	/*
	 * 이전 챕터에서는 하나의 메소드였지만 지금은 3개.
	 * 중복은 역시 분리가 진리.
	 * connectionMaker() 메소드를 만들어 중복을 분리했다.
	 */
	
	/*public UserDao userDao(){ // 유저관련 DAO
		return new UserDao(new DConnectionMaker());
	}*/
	
	public UserDao userDao(){ // 유저관련 DAO
		return new UserDao(connectionMaker());
	}
	
	public UserDao accountDao(){ // 계정관련 DAO
		return new UserDao(connectionMaker());
	}
	
	public UserDao messageDao(){ // 메세지관련 DAO
		return new UserDao(connectionMaker());
	}
	
	public ConnectionMaker connectionMaker(){
		return new DConnectionMaker();
	}
	
	/*
	 * 이제 제어의 역전을 알아보자
	 */
}
