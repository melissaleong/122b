

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import HelperClasses.Movie;
import HelperClasses.cartSession;

/**
 * Servlet implementation class CartPages
 */
@WebServlet("/CartPages")
public class CartPages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartPages() {
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
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false", "isinger", "pi3zza");
			cartSession cart= (cartSession) mySession.getAttribute("cart_session");
			String movieId = request.getParameter("id");
			String requestType = request.getParameter("request").toLowerCase();
			int quantity = 1;
			try{
				if (request.getParameter("quantity") != null || request.getParameter("quantity")!="" )
					{	quantity = Integer.parseInt(request.getParameter("quantity"));
				}
				}catch(NumberFormatException ex){
					ex.printStackTrace();
				}
			
			
			Movie movie = getMovie(movieId, connection);
			if (requestType.equals("add_item"))
			{
				cart.addtoCart(movie, quantity);
			}
			else if (requestType.equals("update_item_quantity"))
			{
				cart.updateCartquantity(movie, quantity);
			}
			else if (requestType.equals("remove_item"))
			{
				cart.removeItem(movie);
			}
			else if (requestType.equals("remove_all_items"))
			{
				cart.clearall();
			}
			
			mySession.setAttribute("cart_session", cart);
			response.sendRedirect("shoppingCart.jsp");
			
			if (connection != null && !connection.isClosed())
			{
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	public Movie getMovie(String input, Connection connection) {
		Movie movie = new Movie();
		
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT DISTINCT * FROM movies where movies.id= '" + input + "'";
			ResultSet result = statement.executeQuery(query);
			
			movie  = returnMovie(result);
			
			result.close();
			statement.close();
			connection.close();
			return movie;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movie;
		
	}
	public static Movie returnMovie(ResultSet result) {
		Movie m = new Movie();
		try {
			while(result.next()) {
			m.id = result.getInt("id");
			m.year = result.getInt("year");
			m.title = result.getString("title");
			m.director = result.getString("director");
			m.banner_url = result.getString("banner_url");
			m.trailer_url = result.getString("trailer_url");
			return m;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			}
		return m;
	}

}
