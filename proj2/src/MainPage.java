

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

/**
 * Servlet implementation class weInBoys
 */
public class MainPage extends HttpServlet {
//	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		RequestDispatcher view = request.getRequestDispatcher("mainpage.html");
		view.forward(request, response);
//		PrintWriter out = response.getWriter();
//		out.println("Welcome!");
	}

}