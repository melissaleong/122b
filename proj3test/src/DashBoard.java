

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
/**
 * Servlet implementation class DashBoard
 */
@WebServlet("/DashBoard")
public class DashBoard extends HttpServlet {

    public DashBoard() {super();}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession mySession = request.getSession();
		
		
		if(request.getParameter("Star") != null) {
			response.sendRedirect("insertstar.html");
		}
		
		if (request.getParameter("MetaData") != null){
			response.sendRedirect("metadata.jsp");
		}
		
		if (request.getParameter("Movie")!=null){
			response.sendRedirect("insertstar.html");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
