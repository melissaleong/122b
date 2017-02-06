

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import HelperClasses.*;
/**
 * Servlet implementation class Checkout
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Checkout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession mySession = request.getSession();
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false", "root", "root");
			String cc_id= request.getParameter("CCnum");
			String f_name= request.getParameter("firstname");
			String l_name= request.getParameter("lastname");
			String exp_date= request.getParameter("expiration");
			String query = "SELECT COUNT(*) FROM creditcards WHERE id=? AND first_name=? AND last_name=? AND expiration=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			System.out.println(cc_id);
			System.out.println(exp_date);
			preparedStatement.setString(1, cc_id);
			preparedStatement.setString(2, f_name);
			preparedStatement.setString(3, l_name);
			if( exp_date.matches("\\d{4}-[01]\\d-[0-3]\\d")){
				preparedStatement.setDate(4, Date.valueOf(exp_date));
			}
			else{
				//set date to something so the 
				preparedStatement.setDate(4, Date.valueOf("0001-01-01"));
			}
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			int count =rs.getInt(1);
			if(count ==1){
				Customer customer =(Customer) mySession.getAttribute("valid_customer");
				cartSession cart = (cartSession) mySession.getAttribute("cart_session");
				for(Cart item : cart.returnitems()){
					try
					{

						java.util.Date date = new java.util.Date();
						DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
						String today = dateformat.format(date).toString();
						
						String sql= "INSERT into sales(customer_id, movie_id, sale_date) values(?,?,?)";
						PreparedStatement statement = connection.prepareStatement(sql);
						statement.setInt(1, customer.getId());
						statement.setInt(2, item.getMovie().id);
						statement.setDate(3, Date.valueOf(today));
						for (int i = 0; i < item.getQuantity(); i++) 
						{
							int resultSet = statement.executeUpdate();
							
							if (resultSet != 1) {
								return;
							}
						}
						cart.clearall();
						mySession.setAttribute("session_cart",cart);
						request.setAttribute("checkout", true);
						request.getRequestDispatcher("CheckoutConfirmation.jsp").forward(request, response);
					} catch(SQLException e){
						System.out.println(e.getMessage());
					}
						
						
					}
				} else {
					System.out.println("failed");
					request.getRequestDispatcher("customerinfo.html").forward(request, response);
				}
		} catch (SQLException e) {
				System.out.println(e.getMessage());
			} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
