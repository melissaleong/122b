

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import HelperClasses.Employee;
import HelperClasses.Database;
import HelperClasses.cartSession;

/**
 * Servlet implementation class EmployeeLogin
 */
@WebServlet("/EmployeeLogin")
public class EmployeeLogin extends HttpServlet {

    public EmployeeLogin() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("employeeLogin.html").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession mySession = request.getSession();
		
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Employee check= checkUser(email, password);
		if(check!=null) {
			mySession.setAttribute("valid_employee",check );
			response.sendRedirect("dashboard.html");

		}
		else {
			request.getRequestDispatcher("EmpLoginFail.html").include(request, response);
		}
	}
	
	public static Employee checkUser(String email, String password) {

		Employee valid_employee= null;
		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false", "root", "root");
			Connection connection = Database.openConnection();
			Statement select = connection.createStatement();
			String query = "SELECT * FROM employees WHERE email='" + email + "' AND password='" + password + "'";
			ResultSet check = select.executeQuery(query);
			
			if(check.next()){
				if (!check.getString(3).equals("")){
					valid_employee= new Employee(check.getString(1),check.getString(2));
				}
				else{
					valid_employee = new Employee(check.getString(1), check.getString(2), check.getString(3));
				}
			}
			connection.close();
			return valid_employee;
//			System.out.println(answer);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return valid_employee;
	}

}
