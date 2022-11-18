package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static String url = "jdbc:oracle:thin:@ananthu-oracle-instance.cazbuiolan96.eu-west-2.rds.amazonaws.com:1521:testdb";
	private static String username = "admin";
	private static String password = "cpiadmin#2022";
	
	public Connection getDBConnection() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}
}
