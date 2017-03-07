

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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession mySession = request.getSession();
		
		try{
			PrintWriter out= response.getWriter();
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			Customer check= checkUser(email, password);
			if(check!=null) {
				out.write("true");

			}
			else{
				out.write("false");
			}
			out.flush();
			out.close();
//			int length = request.getContentLength();
//			byte[] input = new byte[length];
//			
//			ServletInputStream in = request.getInputStream();
//			
//			int c, count =0;
//			while ((c =in.read(input, count, input.length-count))!=-1){
//				count+=c;
//			}
//			in.close();
//			
//			String receivedString = new String(input);
//			response.setStatus(HttpServletResponse.SC_OK);
//			OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
//			
//			String[] values = receivedString.split(" ");
//			
//			Customer check = checkUser(values[0], values[1]);
//			
//			if (check!=null){
//				writer.write("true");
//			}else{
//				writer.write("false");
//			}
//			writer.flush();
//			writer.close();
//			
		}catch(Exception e){
			try{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print(e.getMessage());
            response.getWriter().close();
			}catch(IOException ioe){
			}
		}

	}

	public static Customer checkUser(String email, String password) {

		Customer valid_customer= null;
		try {

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
