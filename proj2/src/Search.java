import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import HelperClasses.*;
import java.util.*;

/**
 * Servlet implementation class weInBoys
 */
public class Search extends HttpServlet {
//	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		HttpSession mySession = request.getSession();
		try{
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false", "isinger", "pi3zza");
			
			String title = request.getParameter("title");
			String year = request.getParameter("yearinput");
			String director = request.getParameter("director"); 
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			
			List<Movie> movieList = getMovieList(title,year, director, firstname, lastname, connection);
			
			for (int i = 0; i < movieList.size(); ++i) {
				for(int j = 0; j<movieList.get(i).starsInMovie.size(); ++j) {
					System.out.println(movieList.get(i).starsInMovie.get(j).fn);
				}
			}
			
		} catch (Exception e){
			e.printStackTrace();
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
	
	public List<Movie> getMovieList(String title, String year, String director, String firstname, String lastname, Connection connection) {
		List<Movie> movieList = new ArrayList<Movie>();
		//need to make advanced query calls here
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT DISTINCT * FROM movies where";
			createStringQuery(query, title, year, director, firstname, lastname);
			ResultSet result = statement.executeQuery(query);
			
			movieList = BrowseByTitle.returnMovieList(result, connection);
			
			result.close();
			statement.close();
			connection.close();
			return movieList;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movieList;
		
	}
	
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
	
	
	private String createStringQuery(String query, String title, String year, String director, String firstname, String lastname){
		String result = query;
		
		//need to figure out how to run query, need to implement firstname and lastname join on for this.
		
		
		
		return result;
	}
	
	
	
	

}