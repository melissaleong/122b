

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import HelperClasses.*;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

public class Login extends HttpServlet {
//	private static final long serialVersionUID = 1L;
       

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("index.html").forward(request, response);
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession mySession = request.getSession();
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
		// Verify CAPTCHA.
		boolean valid = verify.verify(gRecaptchaResponse);
		if (valid) {		
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			Customer check= checkUser(email, password);
			if(check!=null) {
	//			response.sendRedirect(request.getContextPath() + "/mainpage.html");
				mySession.setAttribute("cart_session", new cartSession());
				mySession.setAttribute("valid_customer",check );
				response.sendRedirect("mainpage.jsp");

			}
			else {
				request.getRequestDispatcher("LoginFail.html").include(request, response);

		}}else{
			request.getRequestDispatcher("captchafail.html").include(request, response);

		}

	}
	
	public static Customer checkUser(String email, String password) {

		Customer valid_customer= null;
		try {
			
			
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/moviedb");
			
			Connection connection = ds.getConnection();
//			Connection connection = Database.openConnection();


			String query = "SELECT * FROM customers WHERE email=? AND password=?";
			PreparedStatement select = connection.prepareStatement(query);
			select.setString(1, email);
			select.setString(2, password);


			// String query = "SELECT * FROM customers WHERE email='" + email + "' AND password='" + password + "'";
			ResultSet check = select.executeQuery();
			
			if(check.next()){
				valid_customer = new Customer(check.getInt("id"),check.getString("first_name"),check.getString("last_name"),check.getString("cc_id"), check.getString("address"),check.getString("email"),check.getString("password"));
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
