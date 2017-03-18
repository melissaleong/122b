package HelperClasses;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

public class Database {
//	private String url = "jdbc:mysql://localhost:3306/moviedb?useSSL=false";
//	private String username = "root";
//	private String password = "root";
	
	public static Connection openConnection() throws ClassNotFoundException{
		Connection connection = null;
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			// connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false", "root", "root1234");
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/moviedb");
			
			connection = ds.getConnection();
			return connection;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	

}
