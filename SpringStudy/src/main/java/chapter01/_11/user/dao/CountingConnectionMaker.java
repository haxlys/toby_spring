package chapter01._11.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker{
	int counter = 0;
	private ConnectionMaker realConnectionMaker;
	
	public CountingConnectionMaker(ConnectionMaker realConnectionMaker){
		this.realConnectionMaker = realConnectionMaker;
	}
	
	@Override
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		this.counter++;
		return realConnectionMaker.makeConnection();
	}
	
	public int getCounter(){
		return this.counter;
	}
	
	/*
	 * DConnectionMaker 클래스가 구현한 ConnectionMaker 와 동일한 인터페이스를 구현하였다.
	 * 
	 * 같은 인터페이스를 구현해 놓았기 때문에 우린 기존 소스를 수정하지 않고 카운팅 할 수 있었다.
	 * 카운팅 한 후에는 return realConnectionMaker.makeConnection(); 해주어서 DConnectionMaker에서 만들어진 Connection 객체를 되돌려 주었다.
	 * 
	 * CountingConnectionMaker 클래스는 DConnectionMaker 클래스가 구현한 ConnectionMaker 인터페이스를 구현하였다.
	 * 그렇기에 조금 헷갈릴 수 있을 것이다.
	 * 클라이언트의 DB 조회 요청과 DB 접속 실행을 하는 흐름 중간에 추가되어 카운팅 해주기 위함이다.
	 */
}
