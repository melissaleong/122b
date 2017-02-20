

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

/**
 * Servlet implementation class weInBoys
 */
public class MainPage extends HttpServlet {
//	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession mySession = request.getSession();
		
		if(request.getParameter("Browse") != null) {
			response.sendRedirect("browse.jsp");
//			request.getRequestDispatcher("browsing.html").forward(request, response);
		}
		
		if (request.getParameter("Search") != null){
			response.sendRedirect("search.html");
		}
//		request.getRequestDispatcher("mainpage.html").forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
