package chapter01._05.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
	public Connection makeConnection() throws ClassNotFoundException, SQLException;
}
