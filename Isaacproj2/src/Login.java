

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class Login extends HttpServlet {
//	private static final long serialVersionUID = 1L;
       

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("index.html").forward(request, response);
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession mySession = request.getSession();
		
		PrintWriter out = response.getWriter();
				
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if(checkUser(email, password)) {
//			response.sendRedirect(request.getContextPath() + "/mainpage.html");
			response.sendRedirect("mainpage.html");

		}
		else {
			out.println("Username or Password incorrect");
			request.getRequestDispatcher("index.html").include(request, response);
		}
	}
	
	public boolean checkUser(String email, String password) {

		boolean answer = false;
		try {
//			System.out.printf("%s %s", email, password);
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false", "isinger", "pi3zza");
			Statement select = connection.createStatement();
			String query = "SELECT * FROM customers WHERE email='" + email + "' AND password='" + password + "'";
			ResultSet check = select.executeQuery(query);
			
			answer = check.next();
//			System.out.println(answer);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return answer;
	}

}
