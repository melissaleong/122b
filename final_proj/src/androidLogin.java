

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import HelperClasses.Customer;
import HelperClasses.Database;
import HelperClasses.cartSession;

/**
 * Servlet implementation class androidLogin
 */
@WebServlet("/androidLogin")
public class androidLogin extends HttpServlet {

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession mySession = request.getSession();
		
		try{
			PrintWriter out= response.getWriter();
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			Customer check= checkUser(email, password);
			//out.write("Email: " + email + "Password: " + password);
			if(check!=null) {
				out.write("success");
			}
			else{
				out.write("fail");
			}
			out.flush();
			out.close();
	
		}catch(Exception e){
			try{
			e.printStackTrace();
            response.getWriter().print("some error happened here");
            response.getWriter().close();
			}catch(IOException ioe){
			}
		}
	}
	
	public static Customer checkUser(String email, String password) {

		Customer valid_customer= null;
		try {

			Connection connection = Database.openConnection();
			String query = "SELECT * FROM customers WHERE email=? AND password=?";
			
			PreparedStatement select = connection.prepareStatement(query);
			select.setString(1, email);
			select.setString(2, password);
			ResultSet check = select.executeQuery();
			
			if(check.next()){
				valid_customer = new Customer(check.getInt(1),check.getString(2),check.getString(3),check.getString(4), check.getString(5),check.getString(6),check.getString(7));
			}
			connection.close();
			return valid_customer;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return valid_customer;
	}
}
