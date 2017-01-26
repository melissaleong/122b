

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
		
		if(checkUsernamePass.checkUser(email, password)) {
			RequestDispatcher rs = request.getRequestDispatcher("weInBoys");
			rs.forward(request, response);
		}
		else {
			out.println("Username or Password incorrect");
			RequestDispatcher rs = request.getRequestDispatcher("index.html");
			rs.include(request, response);
		}
	}

}
