import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.plaf.synth.SynthSeparatorUI;

import java.sql.*;
import HelperClasses.*;
import java.util.*;

/**
 * Servlet implementation class weInBoys
 */
public class Search extends HttpServlet {
//	private static final long serialVersionUID = 1L;

	private int numOfMovies=0;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		System.out.println("Trying to connect...");
		HttpSession mySession = request.getSession();
		try{
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false", "root", "root");
			
			List<Movie> movieList;
			int page = 1;
			int moviesPerPage = 10;
			if (request.getParameter("page")!= null){
				page = Integer.parseInt(request.getParameter("page"));
			}
			int offset = (page-1)*moviesPerPage;
			
			String title = request.getParameter("title");
			String year = request.getParameter("yearinput");
			String director = request.getParameter("director"); 
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String[] queryInputs = {title, year, director, firstname, lastname};
			
			for (String s : queryInputs){
				System.out.println(s);
			}
			
			movieList = getMovieList(queryInputs, connection, offset, moviesPerPage);
			
			System.out.println(movieList.size());
			
			int numOfPages = (int) Math.ceil((numOfMovies * 1.0)/ moviesPerPage);
			
			request.setAttribute("page", page);
			request.setAttribute("movieList", movieList);
			request.setAttribute("numOfPages", numOfPages);
			request.setAttribute("movieListSize", numOfMovies);
			request.getRequestDispatcher("movielist.jsp").forward(request, response);
			
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
	
	public List<Movie> getMovieList(String[] queryInputs, Connection connection, int offset, int moviesPerPage) {
		List<Movie> movieList = new ArrayList<Movie>();
		
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT DISTINCT movies.id, movies.title, movies.year, movies.director, movies.banner_url, movies.trailer_url "
					+ "FROM (movies CROSS JOIN stars) CROSS JOIN stars_in_movies WHERE "
					+"(stars_in_movies.star_id = stars.id AND stars_in_movies.movie_id =movies.id)" ;
			
			
			query = createStringQuery(query, queryInputs);//for total results
			ResultSet result = statement.executeQuery(query);
			movieList = BrowseByTitle.returnMovieList(result, connection);
			numOfMovies = movieList.size();

			query = createPageStringQuery(query, offset, moviesPerPage); //attempts to implement pagination here
			result = statement.executeQuery(query);			
			
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
	
	//using the QueryField enum, adds to the query string according to what type of value the item is
	private String addFieldToWhere(String query, QueryField fieldToAdd, String fieldValue ) {
		String finalQuery = query;
			if (fieldValue != ""){
				finalQuery = finalQuery + " AND " + fieldToAdd.name 
					+ " " + fieldToAdd.fieldValuePrefix + fieldValue + fieldToAdd.fieldValueSuffix;
			}
			
		return finalQuery;
	}
	
	
	private String createStringQuery(String query, String[] queryInputs){
		String result = query;
		
		Map<QueryField, String> queryItems = new HashMap<QueryField,String>();
		queryItems = populateHashMap(queryItems, queryInputs);
		for (QueryField Key : queryItems.keySet() ){
			result = addFieldToWhere(result, Key, queryItems.get(Key));
		}
				
		return result;
	}
	
	private String createPageStringQuery(String query, int offset, int moviesPerPage){
		String result = query;
		result = query + " LIMIT " + moviesPerPage + " OFFSET " + offset;
		return result;
	}
	
	//populates the hashmap that will be used to create the rest of the query after WHERE
	private Map<QueryField, String> populateHashMap(Map<QueryField, String> queryItems, String[] queryInputs){ 
		Map<QueryField, String> resultMap = queryItems;
		for (int i = 0; i< queryInputs.length;i++){
			if (i ==0){
				resultMap.put(QueryField.Title, queryInputs[0]);
			}
			else if (i==1){
				resultMap.put(QueryField.Year, queryInputs[1]);
			}
			else if (i==2){
				resultMap.put(QueryField.Director, queryInputs[2]);
			}
			else if (i==3){
				resultMap.put(QueryField.FirstName, queryInputs[3]);
			}
			else if (i==4){
				resultMap.put(QueryField.LastName, queryInputs[4]);
			}
		}
		return resultMap;
	}
	
	
	

}