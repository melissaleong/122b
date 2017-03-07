package HelperClasses;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Database {
//	private String url = "jdbc:mysql://localhost:3306/moviedb?useSSL=false";
//	private String username = "root";
//	private String password = "root";
	
	public static Connection openConnection() throws ClassNotFoundException{
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false", "root", "root123");
			return connection;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	

}
