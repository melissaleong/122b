

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class Login extends HttpServlet {
//	private static final long serialVersionUID = 1L;
       

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String email = request.getParameter("email");
		String password = request.getParameter("pwd");
		
		if(checkUser(email, password)) {
			RequestDispatcher view = request.getRequestDispatcher("mainpage");
			view.forward(request, response);
		}
		else {
			out.println("Username or Password incorrect");
			RequestDispatcher view = request.getRequestDispatcher("index.html");
			view.include(request, response);
		}
	}
	
	public boolean checkUser(String email, String password) {

		boolean answer = false;
		try {
			System.out.printf("%s %s", email, password);
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
			Statement select = connection.createStatement();
			String query = "SELECT * FROM customers WHERE email='" + email + "' AND password='" + password + "'";
			ResultSet check = select.executeQuery(query);
			
			answer = check.next();
			System.out.println(answer);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return answer;
	}

}
