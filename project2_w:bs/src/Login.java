

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import HelperClasses.*;

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
		Customer check= checkUser(email, password);
		if(check!=null) {
//			response.sendRedirect(request.getContextPath() + "/mainpage.html");
			mySession.setAttribute("cart_session", new cartSession());
			mySession.setAttribute("valid_customer",check );
			response.sendRedirect("mainpage.html");

		}
		else {
			request.getRequestDispatcher("LoginFail.html").include(request, response);
		}
	}
	
	public static Customer checkUser(String email, String password) {

		Customer valid_customer= null;
		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false", "root", "root");
			Connection connection = Database.openConnection();
			Statement select = connection.createStatement();
			String query = "SELECT * FROM customers WHERE email='" + email + "' AND password='" + password + "'";
			ResultSet check = select.executeQuery(query);
			
			if(check.next()){
				valid_customer = new Customer(check.getInt(1),check.getString(2),check.getString(3),check.getString(4), check.getString(5),check.getString(6),check.getString(7));
			}
			connection.close();
			return valid_customer;
//			System.out.println(answer);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return valid_customer;
	}

}
