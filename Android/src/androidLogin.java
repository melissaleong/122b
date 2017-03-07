

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
			
			int check= checkUser(email, password);
			out.write("Number: " + check);
			if(check>=1) {
				out.write("success");
			}
			else{
				//out.write(email + " " + password);
				out.write("fail");
			}
			out.flush();
			out.close();
			//response.sendRedirect("success.html");
	
		}catch(Exception e){
			try{
			e.printStackTrace();
            response.getWriter().print("some error happened here");
            response.getWriter().close();
			}catch(IOException ioe){
			}
		}
	}
	
	public static int checkUser(String email, String password) {
		int num=0;
		try {

			Connection connection = Database.openConnection();
			Statement select = connection.createStatement();
			String query = "SELECT COUNT(*) FROM customers WHERE email='" + email + "' AND password='" + password + "'";
			ResultSet check = select.executeQuery(query);
			num = check.getInt(1);
			connection.close();
			return num;

			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return num;
	}
}
