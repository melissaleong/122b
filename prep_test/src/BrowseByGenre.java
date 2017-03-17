

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import HelperClasses.*;
import java.util.*;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;


/**
 * Servlet implementation class BrowseByGenre
 */
public class BrowseByGenre extends HttpServlet {
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		HttpSession mySession = request.getSession();
		
		try {
//			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false", "root", "root1234");
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/moviedb");
			
			Connection connection = ds.getConnection();

			String genre = request.getParameter("genre");
			
			int page = 1;
			if (request.getParameter("page")!= null){
				page = Integer.parseInt(request.getParameter("page"));
			}
			
			List<Movie> movieList = getMovieList(genre, connection);
//			connection.close();
			
			request.setAttribute("page", page);
			request.setAttribute("movieList", movieList);
			request.setAttribute("movieListSize", movieList.size());
			request.getRequestDispatcher("movielist.jsp").forward(request, response);



		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public List<Movie> getMovieList(String input, Connection connection) {
		System.out.println(input);
		List<Movie> movieList = new ArrayList<Movie>();
		
		try {
			String query = "SELECT * FROM (movies m CROSS JOIN genres_in_movies gm) CROSS JOIN genres g WHERE m.id = gm.movie_id AND g.id = gm.genre_id AND g.name=?";

			PreparedStatement statement = connection.prepareStatement(query);
			// String query = "SELECT * FROM (movies m CROSS JOIN genres_in_movies gm) CROSS JOIN genres g WHERE m.id = gm.movie_id AND g.id = gm.genre_id AND g.name= '" + input + "'";
			statement = connection.prepareStatement(query);
			statement.setString(1, input);
			ResultSet result = statement.executeQuery();
			movieList = BrowseByTitle.returnMovieList(result, connection);
			MovieList storedMovieList= new MovieList(movieList, query);

			
			result.close();
			statement.close();
			connection.close();
			return movieList;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movieList;
		
	}
	


}
