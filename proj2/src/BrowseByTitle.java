

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import HelperClasses.*;
import java.util.*;

/**
 * Servlet implementation class Browse
 */
public class BrowseByTitle extends HttpServlet {
	
	int numOfMovies=0;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		HttpSession mySession = request.getSession();
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false", "root", "root1234");
			int page = 1;
			int moviesPerPage = 10;
			if (request.getParameter("page")!= null){
				page = Integer.parseInt(request.getParameter("page"));
			}

			String title = request.getParameter("title");


			List<Movie> movieList = getMovieList(title, connection);
			

			request.setAttribute("page", page);
			request.setAttribute("movieList", movieList);
			request.setAttribute("movieListSize", movieList.size());
			request.getRequestDispatcher("movielist.jsp").forward(request, response);
			
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	
	public List<Movie> getMovieList(String input, Connection connection) {
		List<Movie> movieList = new ArrayList<Movie>();
		
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT DISTINCT * FROM movies where title like '%" + input + "%'";
			System.out.println(query);
			ResultSet result = statement.executeQuery(query);
			
			movieList = BrowseByTitle.returnMovieList(result, connection);
			MovieList storedMovieList = new MovieList(movieList, query);
//			numOfMovies = movieList.size();
//			
//			query = createPageStringQuery(query); //attempts to implement pagination here
//			result = statement.executeQuery(query);			
//			
//			movieList = BrowseByTitle.returnMovieList(result, connection);
			result.close();
			statement.close();
			connection.close();
			return movieList;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movieList;
		
	}
	
//	private String createPageStringQuery(String query, int offset, int moviesPerPage){ //use later
//		String result = query;
//		result = query + " LIMIT " + moviesPerPage + " OFFSET " + offset;
//		return result;
//	}
	
	public static List<Movie> returnMovieList(ResultSet result, Connection connection) {
		List<Movie> movieList = new ArrayList<Movie>();
		
		try {
			while(result.next()) {
				Movie m = new Movie();
				m.id = result.getInt("id");
				m.year = result.getInt("year");
				m.title = result.getString("title");
				m.director = result.getString("director");
				m.banner_url = result.getString("banner_url");
				m.trailer_url = result.getString("trailer_url");
				
				Statement getStars = connection.createStatement();
				String query = "SELECT DISTINCT * FROM stars s LEFT OUTER JOIN stars_in_movies sm ON sm.star_id=s.id where sm.movie_id = '" + m.id +"'";
				ResultSet starsResult = getStars.executeQuery(query);
				
				while(starsResult.next()) {
					Star s = new Star();
					s.id = starsResult.getInt("id");
					s.fn = starsResult.getString("first_name");
					s.ln = starsResult.getString("last_name");
					s.dob = starsResult.getString("dob");
					s.photo_url = starsResult.getString("photo_url");
					m.starsInMovie.add(s);
				}
				starsResult.close();
				getStars.close();
				
				Statement getGenres = connection.createStatement();
				String query2 = "SELECT DISTINCT * FROM genres g LEFT OUTER JOIN genres_in_movies gm ON gm.genre_id=g.id where gm.movie_id = " + m.id;
				ResultSet genresResult = getGenres.executeQuery(query2);
				
				while(genresResult.next()) {
					String g = genresResult.getString("name");
					m.genresInMovie.add(g);
				}
				genresResult.close();
				getGenres.close();
				
				movieList.add(m);
				
			}
			return movieList;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return movieList;
	}

}
